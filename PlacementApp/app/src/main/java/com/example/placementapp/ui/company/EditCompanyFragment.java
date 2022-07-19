package com.example.placementapp.ui.company;

import static com.example.placementapp.constants.AppConstants.COMPANY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.placementapp.databinding.EditCompanyFragmentBinding;
import com.example.placementapp.databinding.ViewCompanyFragmentBinding;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Map;

public class EditCompanyFragment extends Fragment {

    private EditCompanyFragmentBinding binding;
    FirebaseDatabase firebaseDatabase;
    String cmpnyItem = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = EditCompanyFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textSlideshow;
        companyListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();


        setHasOptionsMenu(true);

        cmpnyItem = EditCompanyFragmentArgs.fromBundle(getArguments()).getCompanyItem();

        Gson gson = new Gson();

        Company cmp = gson.fromJson(cmpnyItem,Company.class);

        if((cmp.getCompanyName()!=null)&&(!cmp.getCompanyName().isEmpty())){
            binding.companyNameEdittext.setText(cmp.getCompanyName());
        }
        if((cmp.getcompanyAlumniEmail()!=null)&&(!cmp.getcompanyAlumniEmail().isEmpty())){
            binding.alumniemailidedittext.setText(cmp.getcompanyAlumniEmail());
        }
        if((cmp.getcompanyAlumniName()!=null)&&(!cmp.getcompanyAlumniName().isEmpty())){
            binding.alumniedittext.setText(cmp.getcompanyAlumniName());
        }
        if((cmp.getCompanyBond()!=null)&&(cmp.getCompanyBond()!=null)){
            binding.bondedittext.setText(cmp.getCompanyBond());
        }
        if((cmp.getCompanySelectionProcess()!=null)&&(!cmp.getCompanySelectionProcess().isEmpty())){
            binding.selectionProcessEdittext.setText(cmp.getCompanySelectionProcess());
        }
        if((cmp.getcompanyEligibility()!=null)&&(!cmp.getcompanyEligibility().isEmpty())){
            binding.eligibilityedittext.setText(cmp.getcompanyEligibility());
        }
        if((cmp.getcompanyRequirement()!=null)&&(!cmp.getcompanyRequirement().isEmpty())){
            binding.requirementsEdittext.setText(cmp.getcompanyRequirement());
        }
        if((cmp.getCompanyLocation()!=null)&&(!cmp.getCompanyLocation().isEmpty())){
            binding.companyLocationEdittext.setText(cmp.getCompanyLocation());
        }

        binding.btnEditCompany.setOnClickListener(new View.OnClickListener() {
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


                Company company = new Company(companyName,cmpnyLocation,selectionProcess,bond,eligibility,requirements,alumniName,alumniEmail,cmp.getCompanyId());
                String key = firebaseDatabase.getReference().push().getKey();
                Map<String, Object> postValues = company.toMap();
                updateCompanyData(cmp.getCompanyId(),postValues);
            }
        });


    }

    private void updateCompanyData(String ky, Map<String, Object> postValues) {
        binding.progressBarAddCompany.setVisibility(View.VISIBLE);
        Log.d("Edit","key "+ky );
        firebaseDatabase.getReference().child(COMPANY).child(ky).setValue(postValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                binding.progressBarAddCompany.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "user info successfully updated", Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            }
        });
    }

}
