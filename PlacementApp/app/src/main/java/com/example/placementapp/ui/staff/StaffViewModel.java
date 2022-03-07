package com.example.placementapp.ui.staff;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StaffViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StaffViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Staff screen need to start");
    }

    public LiveData<String> getText() {
        return mText;
    }
}