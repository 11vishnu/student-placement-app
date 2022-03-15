package com.example.placementapp.ui.staff;

public class Staff {

    private String staffName;
    private String staffDepartment;
    private String staffMail;


    public Staff(String staffName, String staffDepartment, String staffMail) {
        this.staffName = staffName;
        this.staffDepartment = staffDepartment;
        this.staffMail = staffMail;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffDepartment() {
        return staffDepartment;
    }

    public void setStaffDepartment(String staffDepartment) {
        this.staffDepartment = staffDepartment;
    }

    public String getStaffMail() {
        return staffMail;
    }

    public void setStaffMail(String staffMail) {
        this.staffMail = staffMail;
    }
}


