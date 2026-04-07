package com.sams.service;

import com.sams.dao.AttendanceDAO;
import com.sams.exception.SAMSException;
import com.sams.model.AttendanceRecord;
import com.sams.model.AttendanceStatus;
import com.sams.model.AttendanceSummary;
import com.sams.model.Student;
import com.sams.model.Subject;
import com.sams.util.IDGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    public static final double MIN_ATTENDANCE = 75.0;

    private final AttendanceDAO dao;
    private final StudentService studentService;
    private final SubjectService subjectService;

    public AttendanceService(AttendanceDAO dao, StudentService ss, SubjectService subs) {
        this.dao = dao;
        this.studentService = ss;
        this.subjectService = subs;
    }

    public AttendanceRecord markAttendance(String studentId, String subjectId,
                                          LocalDate date, AttendanceStatus status, String remarks) {
        studentService.getStudentById(studentId);
        subjectService.getSubjectById(subjectId);
        if (dao.existsRecord(studentId, subjectId, date)) {
            throw new SAMSException.DuplicateAttendanceException(studentId, subjectId, date.toString());
        }

        AttendanceRecord r = new AttendanceRecord(IDGenerator.generateAttendanceId(),
                studentId, subjectId, date, status, remarks != null ? remarks : "");
        dao.save(r);
        return r;
    }

    /**
     * For the dashboard UI: create if missing, otherwise update the record for that date.
     */
    public AttendanceRecord upsertAttendanceForDate(String studentId, String subjectId, LocalDate date, AttendanceStatus status) {
        studentService.getStudentById(studentId);
        subjectService.getSubjectById(subjectId);

        return dao.findByStudentSubjectAndDate(studentId, subjectId, date)
                .map(existing -> {
                    existing.setStatus(status);
                    dao.update(existing);
                    return existing;
                })
                .orElseGet(() -> markAttendance(studentId, subjectId, date, status, ""));
    }

    public AttendanceStatus getStatusForDateOrDefault(String studentId, String subjectId, LocalDate date, AttendanceStatus def) {
        return dao.findByStudentSubjectAndDate(studentId, subjectId, date)
                .map(AttendanceRecord::getStatus)
                .orElse(def);
    }

    public boolean updateAttendance(String recordId, AttendanceStatus status, String remarks) {
        AttendanceRecord r = dao.findById(recordId).orElseThrow(() -> new SAMSException("Record not found: " + recordId));
        r.setStatus(status);
        r.setRemarks(remarks);
        return dao.update(r);
    }

    public List<AttendanceRecord> getByStudent(String id) {
        studentService.getStudentById(id);
        return dao.findByStudentId(id);
    }

    public List<AttendanceRecord> getBySubject(String id) {
        subjectService.getSubjectById(id);
        return dao.findBySubjectId(id);
    }

    public List<AttendanceRecord> getByStudentAndSubject(String s, String sub) {
        return dao.findByStudentAndSubject(s, sub);
    }

    public List<AttendanceRecord> getByDate(LocalDate d) {
        return dao.findByDate(d);
    }

    public AttendanceSummary getSummary(String studentId, String subjectId) {
        Student student = studentService.getStudentById(studentId);
        Subject subject = subjectService.getSubjectById(subjectId);
        return new AttendanceSummary(studentId, student.getName(), subjectId, subject.getSubjectName(),
                subject.getTotalClasses(),
                (int) dao.countByStudentSubjectAndStatus(studentId, subjectId, AttendanceStatus.PRESENT),
                (int) dao.countByStudentSubjectAndStatus(studentId, subjectId, AttendanceStatus.ABSENT),
                (int) dao.countByStudentSubjectAndStatus(studentId, subjectId, AttendanceStatus.LATE),
                (int) dao.countByStudentSubjectAndStatus(studentId, subjectId, AttendanceStatus.EXCUSED));
    }

    public List<AttendanceSummary> getSubjectSummary(String subjectId) {
        subjectService.getSubjectById(subjectId);
        return studentService.getAllStudents().stream()
                .map(s -> getSummary(s.getStudentId(), subjectId))
                .collect(Collectors.toList());
    }

    public List<AttendanceSummary> getBelowThreshold(String subjectId) {
        return getSubjectSummary(subjectId).stream()
                .filter(s -> !s.isEligible())
                .sorted(Comparator.comparingDouble(AttendanceSummary::getAttendancePercentage))
                .collect(Collectors.toList());
    }
}

