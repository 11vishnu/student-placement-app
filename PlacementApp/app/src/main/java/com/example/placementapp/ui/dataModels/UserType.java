package com.example.placementapp.ui.dataModels;

import com.example.placementapp.constants.AppConstants;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserType{

    String User;

    public UserType(String User) {
        this.User = User;
    }


    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(AppConstants.USER_TYPE_CONST, User);
        return result;
    }
}
