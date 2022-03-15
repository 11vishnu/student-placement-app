package com.example.placementapp.ui.staff;

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
import com.example.placementapp.ui.company.ClickListener;
import com.example.placementapp.ui.company.Company;

import java.util.ArrayList;
import java.util.List;

public class StaffFragment extends Fragment {

    private StaffViewModel StaffViewModel;
    private FragmentGalleryBinding binding;

    private RecyclerView recyclerView;
    private StaffListAdapter staffListAdapter;
    private List<Staff> staffList = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StaffViewModel =
                new ViewModelProvider(this).get(StaffViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        StaffViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        staffList = new ArrayList<>();
        staffListAdapter = new StaffListAdapter(staffList);
        binding.recycleViewStaff.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycleViewStaff.setLayoutManager(layoutManager);
        binding.recycleViewStaff.setItemAnimator(new DefaultItemAnimator());
        binding.recycleViewStaff.setAdapter(staffListAdapter);


      /* staffListAdapter.setOnItemClickListener(new ClickListener<String Staff >(){
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
        staffList.add(new Staff("Staff a","CSE","staffa@gmail.com"));
        staffList.add(new Staff("Staff b","EEE","staffB@gmail.com"));
        staffList.add(new Staff("Staff C","ECE","staffC@gmail.com"));
        staffList.add(new Staff("Staff D","MECH","staffd@gmail.com"));
        staffList.add(new Staff("Staff E","Nanotech","staffe@gmail.com"));
        staffListAdapter.notifyDataSetChanged();
    }
}