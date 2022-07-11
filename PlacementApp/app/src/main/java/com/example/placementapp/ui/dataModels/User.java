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
    UploadPdf uploadPdf;

    public String getEmailId() {
        return emailId;
    }

    public User(String userTypeConst, String userName, String emailId, UploadPdf uploadPdf) {
        this.userTypeConst = userTypeConst;
        this.userName = userName;
        this.emailId = emailId;
        this.uploadPdf = uploadPdf;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public UploadPdf getUploadPdf() {
        return uploadPdf;
    }

    public void setUploadPdf(UploadPdf uploadPdf) {
        this.uploadPdf = uploadPdf;
    }

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
        result.put(AppConstants.USER_UPLOAD_PDF,uploadPdf);
        return result;
    }


    public static class UploadPdf implements Serializable{
        String name;
        String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public UploadPdf(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }
}


