package com.example.placementapp.ui.company;

import static com.example.placementapp.constants.AppConstants.COMPANY;
import static com.example.placementapp.constants.AppConstants.USER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.placementapp.R;
import com.example.placementapp.databinding.AddCompanyFragmentBinding;
import com.example.placementapp.databinding.FragmentCompanyBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.persistence.CachePolicy;

import java.util.Map;

public class AddCompanyFragment extends Fragment {

    private AddCompanyFragmentBinding binding;
    FirebaseDatabase firebaseDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = AddCompanyFragmentBinding.inflate(inflater, container, false);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyName = "",selectionProcess="",bond ="",eligibility ="",requirements="",alumniName="",alumniEmail="",cmpnyLocation="";
                if((binding.companyNameEdittext.getText().toString()!=null)&&(!binding.companyNameEdittext.getText().toString().isEmpty())){
                    companyName = binding.companyNameEdittext.getText().toString();
                }

                if((binding.selectionProcessEdittext.getText().toString()!=null)&&(!binding.selectionProcessEdittext.getText().toString().isEmpty())){
                    selectionProcess =  binding.selectionProcessEdittext.getText().toString();
                }

                if((binding.bondedittext.getText().toString()!=null)&&(!binding.bondedittext.getText().toString().isEmpty())){
                    bond = binding.bondedittext.getText().toString();
                }

                if((binding.eligibilityedittext.getText().toString()!=null)&&(!binding.eligibilityedittext.getText().toString().isEmpty())){
                    eligibility = binding.eligibilityedittext.getText().toString();
                }

                if((binding.requirementsEdittext.getText().toString()!=null)&&(!binding.requirementsEdittext.getText().toString().isEmpty())){
                    requirements = binding.requirementsEdittext.getText().toString();
                }

                if((binding.alumniedittext.getText().toString()!=null)&&(!binding.alumniedittext.getText().toString().isEmpty())){
                    alumniName = binding.alumniedittext.getText().toString();
                }

                if((binding.alumniemailidedittext.getText().toString()!=null)&&(!binding.alumniemailidedittext.getText().toString().isEmpty())){
                    alumniEmail = binding.alumniemailidedittext.getText().toString();
                }

                if((binding.companyLocationEdittext.getText().toString()!=null)&&(!binding.companyLocationEdittext.getText().toString().isEmpty())){
                    cmpnyLocation = binding.companyLocationEdittext.getText().toString();
                }

               String key = firebaseDatabase.getReference().push().getKey();
               // Company company = new Company(key,companyName,"inprogress",selectionProcess,bond,eligibility,requirements,alumniName,alumniEmail);
                Company company = new Company(companyName,cmpnyLocation,selectionProcess,bond,eligibility,requirements,alumniName,alumniEmail,key);

                Map<String, Object> postValues = company.toMap();
                updateCompanyData(key,postValues);
            }
        });


    }

    private void updateCompanyData(String ky,Map<String, Object> postValues) {
        binding.progressBarAddCompany.setVisibility(View.VISIBLE);
        firebaseDatabase.getReference().child(COMPANY).child(ky).updateChildren(postValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                binding.progressBarAddCompany.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "user info successfully inserted", Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            }
        });
    }




}
