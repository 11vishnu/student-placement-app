package com.example.placementapp.ui.company;

import com.example.placementapp.constants.AppConstants;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Company {

    private String companyName;
    private String companyLocation;
    private String companySelectionProcess;
    private String bond;
    private String eligibility;
    private String requirements;
    private String alumniName;
    private String alumniEmailId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Company(String companyName, String companyLocation, String companySelectionProcess, String bond, String eligibility, String requirements, String alumniName, String alumniEmailId) {
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companySelectionProcess = companySelectionProcess;
        this.bond = bond;
        this.eligibility = eligibility;
        this.requirements = requirements;
        this.alumniName = alumniName;
        this.alumniEmailId = alumniEmailId;
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

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getAlumniName() {
        return alumniName;
    }

    public void setAlumniName(String alumniName) {
        this.alumniName = alumniName;
    }

    public String getAlumniEmailId() {
        return alumniEmailId;
    }

    public void setAlumniEmailId(String alumniEmailId) {
        this.alumniEmailId = alumniEmailId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(AppConstants.COMPANY_NAME, companyName);
        result.put(AppConstants.COMPANY_SELECTION_PROCESS, companySelectionProcess);
        result.put(AppConstants.COMPANY_BOND, bond);
        result.put(AppConstants.COMPANY_ELIGIBILITY, eligibility);
        result.put(AppConstants.COMPANY_REQUIREMENT, requirements);
        result.put(AppConstants.COMPANY_ALUMNI_NAME, alumniName);
        result.put(AppConstants.COMPANY_ALUMNI_EMAIL, alumniEmailId);
        return result;
    }

}
