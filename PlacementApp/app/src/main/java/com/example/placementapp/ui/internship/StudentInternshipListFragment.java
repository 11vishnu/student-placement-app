package com.example.placementapp.ui.internship;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placementapp.databinding.FragmentInternshipLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentInternshipListFragment  extends Fragment {

    private IntershipViewModel InternshipViewModel;
    private FragmentInternshipLayoutBinding binding;

    private RecyclerView recyclerView;
    private InternshipListAdapter internshipListAdapter;
    private List<InternshipDataModel> internshipList = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InternshipViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(IntershipViewModel.class);

        binding = FragmentInternshipLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textGallery;
        StudentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        internshipList = new ArrayList<>();
        internshipListAdapter = new InternshipListAdapter(internshipList);
        binding.internshipRecycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.internshipRecycleView.setLayoutManager(layoutManager);
        binding.internshipRecycleView.setItemAnimator(new DefaultItemAnimator());
        binding.internshipRecycleView.setAdapter(internshipListAdapter);


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
        internshipList.add(new InternshipDataModel("offline","cbe","6 months","Student a","companu a name pvt ltd"));
        internshipList.add(new InternshipDataModel("offline","chn","9 months","Student b","companu b name pvt ltd"));
        internshipList.add(new InternshipDataModel("offline","trc","3 months","Student c","companu c name pvt ltd"));
        internshipList.add(new InternshipDataModel("offline","mdu","1 y 3 mts","Student d","companu d name pvt ltd"));
        internshipListAdapter.notifyDataSetChanged();
    }

}
