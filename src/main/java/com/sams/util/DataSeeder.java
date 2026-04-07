package com.sams.util;

import com.sams.model.AttendanceStatus;
import com.sams.service.AttendanceService;
import com.sams.service.StudentService;
import com.sams.service.SubjectService;

import java.time.LocalDate;

public class DataSeeder {
    private static final AttendanceStatus P = AttendanceStatus.PRESENT;
    private static final AttendanceStatus A = AttendanceStatus.ABSENT;
    private static final AttendanceStatus L = AttendanceStatus.LATE;

    public static void seed(StudentService ss, SubjectService subs, AttendanceService as) {
        // Subjects
        subs.addSubject("Object Oriented Programming", "CS301", "Dr. Ramesh Kumar", 45);
        subs.addSubject("Data Structures & Algorithms", "CS302", "Prof. Anitha Rao", 40);
        subs.addSubject("Database Management Systems", "CS303", "Dr. Suresh Babu", 38);
        subs.addSubject("Operating Systems", "CS304", "Prof. Meena Iyer", 42);

        // Students
        ss.registerStudent("Arjun Sharma", "arjun.sharma@example.com", "B.Tech CSE", 3, "9876543210");
        ss.registerStudent("Priya Nair", "priya.nair@example.com", "B.Tech CSE", 3, "9123456780");
        ss.registerStudent("Ravi Kumar", "ravi.kumar@example.com", "B.Tech CSE", 3, "9988776655");
        ss.registerStudent("Sneha Patel", "sneha.patel@example.com", "B.Tech CSE", 3, "9871234560");
        ss.registerStudent("Mohammed Irfan", "irfan.m@example.com", "B.Tech CSE", 3, "9654321087");

        String[] students = {"STU1001", "STU1002", "STU1003", "STU1004", "STU1005"};
        String sub1 = "SUB101";

        AttendanceStatus[][] patterns = {
                {P, P, P, P, P, P, P, P, P, A},   // STU1001 — 90%
                {P, A, P, P, A, P, P, A, P, P},   // STU1002 — 70%
                {P, A, A, P, A, P, A, A, P, A},   // STU1003 — 30% (below threshold)
                {P, P, P, P, P, P, P, P, P, P},   // STU1004 — 100%
                {P, L, P, A, P, P, A, L, P, P},   // STU1005 — 80%
        };

        LocalDate today = LocalDate.now();
        for (int day = 0; day < 10; day++) {
            LocalDate d = today.minusDays(10 - day);
            for (int i = 0; i < students.length; i++) {
                try {
                    as.markAttendance(students[i], sub1, d, patterns[i][day], "");
                } catch (Exception ignored) {
                }
            }
        }
    }
}

