package com.example.placementapp.ui.dataModels;

import com.example.placementapp.constants.AppConstants;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    String userTypeConst;
    String userName;
    String emailId;

    public String getUserTypeConst() {
        return userTypeConst;
    }

    public void setUserTypeConst(String userTypeConst) {
        this.userTypeConst = userTypeConst;
    }

    public String getUserEmailId() {
        return emailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.emailId = userEmailId;
    }

    public User(String userType, String userName,String userEmailId) {
        this.userTypeConst = userType;
        this.userName = userName;
        this.emailId = userEmailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userTypeConst;
    }

    public void setUserType(String userType) {
        this.userTypeConst = userType;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(AppConstants.USER_TYPE_CONST, userTypeConst);
        result.put(AppConstants.USER_NAME_CONST, userName);
        result.put(AppConstants.USER_EMAIL_CONST, emailId);
        return result;
    }
}


