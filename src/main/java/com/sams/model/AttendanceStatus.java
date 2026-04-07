package com.sams.model;

public enum AttendanceStatus {
    PRESENT("P"), ABSENT("A"), LATE("L"), EXCUSED("E");

    private final String code;

    AttendanceStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static AttendanceStatus fromCode(String code) {
        for (AttendanceStatus s : values()) {
            if (s.code.equalsIgnoreCase(code)) return s;
        }
        throw new IllegalArgumentException("Unknown attendance code: " + code);
    }
}

