package com.sams.model;

public class AttendanceSummary {
    private String studentId, studentName, subjectId, subjectName;
    private int totalClasses, presentCount, absentCount, lateCount, excusedCount;

    public AttendanceSummary() {}

    public AttendanceSummary(String studentId, String studentName,
                             String subjectId, String subjectName,
                             int totalClasses, int presentCount,
                             int absentCount, int lateCount, int excusedCount) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.totalClasses = totalClasses;
        this.presentCount = presentCount;
        this.absentCount = absentCount;
        this.lateCount = lateCount;
        this.excusedCount = excusedCount;
    }

    public double getAttendancePercentage() {
        if (totalClasses == 0) return 0.0;
        return ((double) (presentCount + lateCount) / totalClasses) * 100.0;
    }

    public boolean isEligible() {
        return getAttendancePercentage() >= 75.0;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(int presentCount) {
        this.presentCount = presentCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public void setLateCount(int lateCount) {
        this.lateCount = lateCount;
    }

    public int getExcusedCount() {
        return excusedCount;
    }

    public void setExcusedCount(int excusedCount) {
        this.excusedCount = excusedCount;
    }
}

