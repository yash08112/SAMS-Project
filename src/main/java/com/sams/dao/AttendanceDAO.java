package com.sams.dao;

import com.sams.model.AttendanceRecord;
import com.sams.model.AttendanceStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AttendanceDAO implements GenericDAO<AttendanceRecord, String> {
    private final Map<String, AttendanceRecord> store = new LinkedHashMap<>();

    @Override
    public void save(AttendanceRecord r) {
        if (r == null || r.getRecordId() == null) throw new IllegalArgumentException("Record/ID null.");
        store.put(r.getRecordId(), r);
    }

    @Override
    public Optional<AttendanceRecord> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<AttendanceRecord> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(String id) {
        return store.remove(id) != null;
    }

    @Override
    public boolean update(AttendanceRecord r) {
        if (!store.containsKey(r.getRecordId())) return false;
        store.put(r.getRecordId(), r);
        return true;
    }

    public List<AttendanceRecord> findByStudentId(String studentId) {
        return store.values().stream()
                .filter(r -> r.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<AttendanceRecord> findBySubjectId(String subjectId) {
        return store.values().stream()
                .filter(r -> r.getSubjectId().equals(subjectId))
                .collect(Collectors.toList());
    }

    public List<AttendanceRecord> findByStudentAndSubject(String studentId, String subjectId) {
        return store.values().stream()
                .filter(r -> r.getStudentId().equals(studentId) && r.getSubjectId().equals(subjectId))
                .sorted(Comparator.comparing(AttendanceRecord::getDate))
                .collect(Collectors.toList());
    }

    public Optional<AttendanceRecord> findByStudentSubjectAndDate(String studentId, String subjectId, LocalDate date) {
        return store.values().stream()
                .filter(r -> r.getStudentId().equals(studentId) && r.getSubjectId().equals(subjectId) && r.getDate().equals(date))
                .findFirst();
    }

    public List<AttendanceRecord> findByDate(LocalDate date) {
        return store.values().stream()
                .filter(r -> r.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<AttendanceRecord> findByDateRange(LocalDate from, LocalDate to) {
        return store.values().stream()
                .filter(r -> !r.getDate().isBefore(from) && !r.getDate().isAfter(to))
                .sorted(Comparator.comparing(AttendanceRecord::getDate))
                .collect(Collectors.toList());
    }

    public long countByStudentSubjectAndStatus(String studentId, String subjectId, AttendanceStatus status) {
        return store.values().stream()
                .filter(r -> r.getStudentId().equals(studentId) && r.getSubjectId().equals(subjectId) && r.getStatus() == status)
                .count();
    }

    public boolean existsRecord(String studentId, String subjectId, LocalDate date) {
        return store.values().stream()
                .anyMatch(r -> r.getStudentId().equals(studentId) && r.getSubjectId().equals(subjectId) && r.getDate().equals(date));
    }

    public int getCount() {
        return store.size();
    }
}

