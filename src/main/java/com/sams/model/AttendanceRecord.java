package com.sams.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AttendanceRecord {
    private String recordId, studentId, subjectId, remarks;
    private LocalDate date;
    private AttendanceStatus status;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public AttendanceRecord() {}

    public AttendanceRecord(String recordId, String studentId, String subjectId, LocalDate date, AttendanceStatus status, String remarks) {
        this.recordId = recordId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.date = date;
        this.status = status;
        this.remarks = remarks;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFormattedDate() {
        return date != null ? date.format(FMT) : "N/A";
    }
}

