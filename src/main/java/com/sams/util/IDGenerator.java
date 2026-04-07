package com.sams.util;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {
    private IDGenerator() {}

    private static final AtomicInteger SC = new AtomicInteger(1000);
    private static final AtomicInteger SUC = new AtomicInteger(100);
    private static final AtomicInteger AC = new AtomicInteger(1);

    public static String generateStudentId() {
        return "STU" + SC.incrementAndGet();
    }

    public static String generateSubjectId() {
        return "SUB" + SUC.incrementAndGet();
    }

    public static String generateAttendanceId() {
        LocalDate t = LocalDate.now();
        return String.format("ATT-%d%02d%02d-%04d", t.getYear(), t.getMonthValue(), t.getDayOfMonth(), AC.getAndIncrement());
    }
}

