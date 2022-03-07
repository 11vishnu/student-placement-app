package com.example.placementapp.ui.company;

import android.view.View;

public interface ClickListener<T> {

    void onClick(View view, T data, int position);
}