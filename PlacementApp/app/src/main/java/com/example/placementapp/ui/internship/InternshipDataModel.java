package com.example.placementapp.ui.internship;

public class InternshipDataModel {

    private String regNo;
    private String duration;
    private String studentName;
    private String companyName;

    public InternshipDataModel(String regNo, String duration, String studentName,String companyName) {
        this.regNo = regNo;
        this.duration = duration;
        this.studentName = studentName;
        this.companyName = companyName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
