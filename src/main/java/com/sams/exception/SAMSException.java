package com.sams.exception;

public class SAMSException extends RuntimeException {
    public SAMSException(String msg) {
        super(msg);
    }

    public SAMSException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public static class StudentNotFoundException extends SAMSException {
        public StudentNotFoundException(String id) {
            super("Student not found: " + id);
        }
    }

    public static class SubjectNotFoundException extends SAMSException {
        public SubjectNotFoundException(String id) {
            super("Subject not found: " + id);
        }
    }

    public static class DuplicateAttendanceException extends SAMSException {
        public DuplicateAttendanceException(String sid, String subid, String date) {
            super(String.format("Attendance already marked — Student[%s] Subject[%s] Date[%s]", sid, subid, date));
        }
    }

    public static class DuplicateStudentException extends SAMSException {
        public DuplicateStudentException(String id) {
            super("Student already exists: " + id);
        }
    }

    public static class InvalidDataException extends SAMSException {
        public InvalidDataException(String msg) {
            super("Invalid data: " + msg);
        }
    }
}

