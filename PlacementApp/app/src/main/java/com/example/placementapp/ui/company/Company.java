package com.example.placementapp.ui.company;

public class Company {

    private String companyName;
    private String companyLocation;

    Company(String mName,String cLoc){
        this.companyName = mName;
        this.companyLocation = cLoc;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }
}
