package com.example.placementapp.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.placementapp.databinding.FragmentGalleryBinding;
import com.example.placementapp.databinding.FragmentStudentBinding;
import com.example.placementapp.ui.company.ClickListener;
import com.example.placementapp.ui.company.Company;
import com.example.placementapp.ui.staff.Staff;
import com.example.placementapp.ui.staff.StaffListAdapter;
import com.example.placementapp.ui.staff.StaffViewModel;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment {

    private StudentViewModel StudentViewModel;
    private FragmentStudentBinding binding;

    private RecyclerView recyclerView;
    private StudentListAdapter studentListAdapter;
    private List<Student> studentList = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StudentViewModel =
                new ViewModelProvider(this).get(StudentViewModel.class);

        binding = FragmentStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        StudentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        studentList = new ArrayList<>();
        studentListAdapter = new StudentListAdapter(studentList);
        binding.recycleViewStudent.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycleViewStudent.setLayoutManager(layoutManager);
        binding.recycleViewStudent.setItemAnimator(new DefaultItemAnimator());
        binding.recycleViewStudent.setAdapter(studentListAdapter);


      /* studentListAdapter.setOnItemClickListener(new ClickListener<String Staff >(){
            @Override
            public void onClick(View view, Staff data, int position) {
                Toast.makeText(requireContext(),"Position = "+position+"\n Item = "+data.getCompanyName(),Toast.LENGTH_SHORT).show();



        });*/


        prepareItems();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareItems(){
        studentList.add(new Student("Staff a","CSE","staffa@gmail.com"));
        studentList.add(new Student("Staff b","EEE","staffB@gmail.com"));
        studentList.add(new Student("Staff C","ECE","staffC@gmail.com"));
        studentList.add(new Student("Staff D","MECH","staffd@gmail.com"));
        studentList.add(new Student("Staff E","Nanotech","staffe@gmail.com"));
        studentListAdapter.notifyDataSetChanged();
    }
}