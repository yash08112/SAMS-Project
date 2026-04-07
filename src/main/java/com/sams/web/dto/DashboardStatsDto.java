package com.sams.web.dto;

public record DashboardStatsDto(
        int totalStudents,
        int totalSubjects,
        int lowAttendance
) {}

