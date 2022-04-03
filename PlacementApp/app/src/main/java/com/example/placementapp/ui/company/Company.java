package com.example.placementapp.ui.company;

import com.example.placementapp.constants.AppConstants;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Company {

    private String companyName;
    private String companyLocation;
    private String companySelectionProcess;
    private String companyBond;
    private String companyEligibility;
    private String companyRequirement;
    private String companyAlumniName;
    private String companyAlumniEmail;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Company(String companyName, String companyLocation, String companySelectionProcess, String companyBond, String companyEligibility, String companyRequirements, String companyAlumniName, String companyAlumniEmail) {
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companySelectionProcess = companySelectionProcess;
        this.companyBond = companyBond;
        this.companyEligibility = companyEligibility;
        this.companyRequirement = companyRequirement;
        this.companyAlumniName = companyAlumniName;
        this.companyAlumniEmail = companyAlumniEmail;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }


    public String getCompanySelectionProcess() {
        return companySelectionProcess;
    }

    public void setCompanySelectionProcess(String companySelectionProcess) {
        this.companySelectionProcess = companySelectionProcess;
    }

    public String getCompanyBond() {
        return companyBond;
    }

    public void setCompanyBond(String bond) {
        this.companyBond = bond;
    }

    public String getcompanyEligibility() {
        return companyEligibility;
    }

    public void setcompanyEligibility(String companyEligibility) {
        this.companyEligibility = companyEligibility;
    }

    public String getcompanyRequirement() {
        return companyRequirement;
    }

    public void setcompanyRequirement(String companyRequirement) {
        this.companyRequirement = companyRequirement;
    }

    public String getcompanyAlumniName() {
        return companyAlumniName;
    }

    public void setcompanyAlumniName(String companyAlumniName) {
        this.companyAlumniName = companyAlumniName;
    }

    public String getcompanyAlumniEmail() {
        return companyAlumniEmail;
    }

    public void setcompanyAlumniEmail(String companyAlumniEmail) {
        this.companyAlumniEmail = companyAlumniEmail;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(AppConstants.COMPANY_NAME, companyName);
        result.put(AppConstants.COMPANY_SELECTION_PROCESS, companySelectionProcess);
        result.put(AppConstants.COMPANY_BOND, companyBond);
        result.put(AppConstants.COMPANY_ELIGIBILITY, companyEligibility);
        result.put(AppConstants.COMPANY_REQUIREMENT, companyRequirement);
        result.put(AppConstants.COMPANY_ALUMNI_NAME, companyAlumniName);
        result.put(AppConstants.COMPANY_ALUMNI_EMAIL, companyAlumniEmail);
        return result;
    }

}
