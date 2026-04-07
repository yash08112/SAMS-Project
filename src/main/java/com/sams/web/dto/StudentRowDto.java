package com.sams.web.dto;

public record StudentRowDto(
        String id,
        String name,
        String subjectId,
        String subject,
        String status
) {}

