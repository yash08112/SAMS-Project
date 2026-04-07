package com.sams.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AttendanceUpdateRequest {
    @NotBlank
    private String studentId;

    private String subjectId;

    @NotBlank
    @Pattern(regexp = "(?i)^[PALE]$", message = "Status must be one of P/A/L/E")
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

