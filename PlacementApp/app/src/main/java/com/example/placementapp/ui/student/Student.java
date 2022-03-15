package com.example.placementapp.ui.student;

public class Student {
    private String studentName;
    private String studentDepartment;
    private String studentMail;

    public Student(String studentName, String studentDepartment, String studentMail) {
        this.studentName = studentName;
        this.studentDepartment = studentDepartment;
        this.studentMail = studentMail;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentDepartment() {
        return studentDepartment;
    }

    public void setStudentDepartment(String studentDepartment) {
        this.studentDepartment = studentDepartment;
    }

    public String getStudentMail() {
        return studentMail;
    }

    public void setStudentMail(String studentMail) {
        this.studentMail = studentMail;
    }
}

