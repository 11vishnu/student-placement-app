package com.example.placementapp.ui.company;

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

import com.example.placementapp.R;
import com.example.placementapp.databinding.FragmentCompanyBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CompanyListFragment extends Fragment {

    private CompanyListViewModel companyListViewModel;
    private FragmentCompanyBinding binding;

    private RecyclerView recyclerView;
    private RecyclerviewItemAdapter recyclerviewItemAdapter;
    private List<Company> companyItemList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        companyListViewModel =
                new ViewModelProvider(this).get(CompanyListViewModel.class);

        binding = FragmentCompanyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textSlideshow;
        companyListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        companyItemList = new ArrayList<>();
        // recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        // recyclerView =

        recyclerviewItemAdapter = new RecyclerviewItemAdapter(companyItemList);
        binding.recycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycleView.setLayoutManager(layoutManager);
        binding.recycleView.setItemAnimator(new DefaultItemAnimator());
        binding.recycleView.setAdapter(recyclerviewItemAdapter);

        recyclerviewItemAdapter.setOnItemClickListener(new ClickListener<Company>(){
            @Override
            public void onClick(View view, Company data, int position) {
                Toast.makeText(requireContext(),"Position = "+position+"\n Item = "+data.getCompanyName(),Toast.LENGTH_SHORT).show();

            }

        });

        binding.fabAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        prepareItems();
    }

    private void prepareItems(){
        companyItemList.add(new Company("Wipro pvt ltd","Chennai"));
        companyItemList.add(new Company("HCL pvt ltd","Bangalore"));
        companyItemList.add(new Company("TCS pvt ltd","Chennai"));
        companyItemList.add(new Company("Infosys pvt ltd","Mysore,Chennai, Coimbatore, Bangalore"));
        companyItemList.add(new Company("Accenture pvt ltd","Chennai"));
        companyItemList.add(new Company("mphasis pvt ltd","Chennai"));
        recyclerviewItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}