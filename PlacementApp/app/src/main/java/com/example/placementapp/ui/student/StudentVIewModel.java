package com.example.placementapp.ui.student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudentVIewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StudentVIewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is student fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}