package com.example.placementapp.ui.company;

import static com.example.placementapp.constants.AppConstants.ARG_COMPANY_ITEM;

import android.os.Bundle;
import android.util.Log;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placementapp.R;
import com.example.placementapp.constants.AppConstants;
import com.example.placementapp.databinding.FragmentCompanyBinding;
import com.example.placementapp.ui.dataModels.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CompanyListFragment extends Fragment implements RecyclerviewItemAdapter.CompanyItemInterface {

    private CompanyListViewModel companyListViewModel;
    private FragmentCompanyBinding binding;

    private RecyclerView recyclerView;
    private RecyclerviewItemAdapter recyclerviewItemAdapter;
    private List<Company> companyItemList;


    List<Company> companyList = new ArrayList<Company>();

    FirebaseDatabase firebaseDatabase;

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

        recyclerviewItemAdapter.setOnItemClickListener(new ClickListener<Company>() {
            @Override
            public void onClick(View view, Company data, int position) {
               // Toast.makeText(requireContext(), "Position = " + position + "\n Item = " + data.getCompanyName(), Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                String str = gson.toJson(data);
                Bundle bundle = new Bundle();
                bundle.putString(ARG_COMPANY_ITEM,str);
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.action_cmpnylist_to_view_company,bundle);

            }

        });

        binding.fabAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.action_companies_to_add_company);

            }
        });

       // prepareItems();

        firebaseDatabase = FirebaseDatabase.getInstance();
        binding.progressBarCompanyList.setVisibility(View.VISIBLE);
        firebaseDatabase.getReference(AppConstants.COMPANY).orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                binding.progressBarCompanyList.setVisibility(View.GONE);
                final Map<String, Company> messageMap = new LinkedHashMap<String, Company>();
                companyItemList.clear();
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        HashMap<String, Company> msgMap = (HashMap<String, Company>) postSnapshot.getValue();

                        Gson gson = new Gson();
                        JsonElement jsonElement = gson.toJsonTree(msgMap);
                        Company company = gson.fromJson(jsonElement, Company.class);
                        companyItemList.add(company);
                        recyclerviewItemAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBarCompanyList.setVisibility(View.GONE);
            }
        });

    }

    private void prepareItems(){
        companyItemList.add(new Company("Wipro pvt ltd","Chennai","","","","","",""));
        companyItemList.add(new Company("HCL pvt ltd","Bangalore","","","","","",""));
        companyItemList.add(new Company("TCS pvt ltd","Chennai","","","","","",""));
        companyItemList.add(new Company("Infosys pvt ltd","Mysore,Chennai, Coimbatore, Bangalore","","","","","",""));
        companyItemList.add(new Company("Accenture pvt ltd","Chennai","","","","","",""));
        companyItemList.add(new Company("mphasis pvt ltd","Chennai","","","","","",""));
        recyclerviewItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCompanyClick(Company cmpny) {

    }
}