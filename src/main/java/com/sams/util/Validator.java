package com.sams.util;

import com.sams.exception.SAMSException;
import com.sams.model.Student;
import com.sams.model.Subject;

public class Validator {
    private Validator() {}

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PHONE_REGEX = "^[6-9]\\d{9}$";
    private static final String ID_REGEX = "^[A-Za-z0-9_-]{3,20}$";

    public static void validateStudent(Student s) {
        if (s == null) throw new SAMSException.InvalidDataException("Student is null.");
        if (!s.getStudentId().matches(ID_REGEX)) throw new SAMSException.InvalidDataException("Bad student ID.");
        if (isBlank(s.getName())) throw new SAMSException.InvalidDataException("Name is empty.");
        if (!s.getEmail().matches(EMAIL_REGEX)) throw new SAMSException.InvalidDataException("Bad email: " + s.getEmail());
        if (!s.getPhoneNumber().matches(PHONE_REGEX)) throw new SAMSException.InvalidDataException("Bad phone: " + s.getPhoneNumber());
        if (s.getSemester() < 1 || s.getSemester() > 8) throw new SAMSException.InvalidDataException("Semester must be 1-8.");
        if (isBlank(s.getCourse())) throw new SAMSException.InvalidDataException("Course is empty.");
    }

    public static void validateSubject(Subject s) {
        if (s == null) throw new SAMSException.InvalidDataException("Subject is null.");
        if (isBlank(s.getSubjectName())) throw new SAMSException.InvalidDataException("Subject name is empty.");
        if (isBlank(s.getSubjectCode())) throw new SAMSException.InvalidDataException("Subject code is empty.");
        if (s.getTotalClasses() <= 0) throw new SAMSException.InvalidDataException("Total classes must be > 0.");
    }

    public static boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }
}

