package com.example.placementapp.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.placementapp.databinding.FragmentSlideshowBinding;
import com.example.placementapp.databinding.FragmentStudentBinding;
import com.example.placementapp.ui.slideshow.SlideshowViewModel;

public class StudentFragment  extends Fragment {

    private StudentVIewModel studentViewModel;
    private FragmentStudentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studentViewModel =
                new ViewModelProvider(this).get(StudentVIewModel.class);

        binding = FragmentStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textStudent;
        studentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
