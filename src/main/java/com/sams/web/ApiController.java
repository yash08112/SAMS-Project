package com.sams.web;

import com.sams.model.AttendanceStatus;
import com.sams.model.Subject;
import com.sams.service.AttendanceService;
import com.sams.service.StudentService;
import com.sams.service.SubjectService;
import com.sams.web.dto.AttendanceUpdateRequest;
import com.sams.web.dto.DashboardStatsDto;
import com.sams.web.dto.StudentRowDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final AttendanceService attendanceService;

    public ApiController(StudentService studentService, SubjectService subjectService, AttendanceService attendanceService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.attendanceService = attendanceService;
    }

    private Subject defaultSubject() {
        List<Subject> subjects = subjectService.getAllSubjects();
        if (subjects.isEmpty()) {
            throw new IllegalStateException("No subjects configured.");
        }
        return subjects.get(0);
    }

    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentRowDto> students() {
        Subject subject = defaultSubject();
        LocalDate today = LocalDate.now();

        return studentService.getAllStudents().stream()
                .map(s -> new StudentRowDto(
                        s.getStudentId(),
                        s.getName(),
                        subject.getSubjectId(),
                        subject.getSubjectName(),
                        attendanceService.getStatusForDateOrDefault(s.getStudentId(), subject.getSubjectId(), today, AttendanceStatus.PRESENT).getCode()
                ))
                .toList();
    }

    @PostMapping(value = "/attendance", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentRowDto updateAttendance(@Valid @RequestBody AttendanceUpdateRequest req) {
        Subject subject = req.getSubjectId() != null && !req.getSubjectId().isBlank()
                ? subjectService.getSubjectById(req.getSubjectId())
                : defaultSubject();

        AttendanceStatus status = AttendanceStatus.fromCode(req.getStatus());
        LocalDate today = LocalDate.now();
        attendanceService.upsertAttendanceForDate(req.getStudentId(), subject.getSubjectId(), today, status);

        var student = studentService.getStudentById(req.getStudentId());
        return new StudentRowDto(
                student.getStudentId(),
                student.getName(),
                subject.getSubjectId(),
                subject.getSubjectName(),
                status.getCode()
        );
    }

    @GetMapping(value = "/dashboard-stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public DashboardStatsDto dashboardStats() {
        Subject subject = defaultSubject();
        int low = attendanceService.getBelowThreshold(subject.getSubjectId()).size();
        return new DashboardStatsDto(
                studentService.getTotalStudentCount(),
                subjectService.getTotalSubjectCount(),
                low
        );
    }
}

