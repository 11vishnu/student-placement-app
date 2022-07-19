package com.example.placementapp.ui.internship;

public class InternshipDataModel {

    private String internshipMode;
    private String duration;
    private String studentName;
    private String companyName;
    private String stipend;

    public String getInternshipMode() {
        return internshipMode;
    }

    public void setInternshipMode(String internshipMode) {
        this.internshipMode = internshipMode;
    }

    public String getStipend() {
        return stipend;
    }

    public void setStipend(String stipend) {
        this.stipend = stipend;
    }


    public InternshipDataModel(String intrnshpMde, String duration, String studentName,String companyName,String stipend) {
        this.internshipMode = intrnshpMde;
        this.duration = duration;
        this.studentName = studentName;
        this.companyName = companyName;
        this.stipend = stipend;
    }

    public String getRegNo() {
        return internshipMode;
    }

    public void setRegNo(String regNo) {
        this.internshipMode = regNo;
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
