package com.example.placementapp.ui.company;

import static com.example.placementapp.constants.AppConstants.ARG_COMPANY_ITEM;
import static com.example.placementapp.constants.AppConstants.COMPANY;
import static com.example.placementapp.constants.AppConstants.USER;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavArgs;
import androidx.navigation.Navigation;

import com.example.placementapp.R;
import com.example.placementapp.databinding.AddCompanyFragmentBinding;
import com.example.placementapp.databinding.FragmentCompanyBinding;
import com.example.placementapp.databinding.ViewCompanyFragmentBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.persistence.CachePolicy;
import com.google.gson.Gson;

import java.util.Map;

public class ViewCompanyFragment extends Fragment {

    private ViewCompanyFragmentBinding binding;
    FirebaseDatabase firebaseDatabase;
    String cmpnyItem = "";
    Company cmp = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = ViewCompanyFragmentBinding.inflate(inflater, container, false);
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

      /*  binding.btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyName = "",selectionProcess="",bond ="",eligibility ="",requirements="",alumniName="",alumniEmail="";
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

                Company company = new Company(companyName,"inprogress",selectionProcess,bond,eligibility,requirements,alumniName,alumniEmail);
                String key = firebaseDatabase.getReference().push().getKey();
                Map<String, Object> postValues = company.toMap();
                updateCompanyData(key,postValues);
            }
        });*/


        setHasOptionsMenu(true);

        cmpnyItem = ViewCompanyFragmentArgs.fromBundle(getArguments()).getCompanyItem();

        Gson gson = new Gson();

        cmp = gson.fromJson(cmpnyItem,Company.class);

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


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.id_menu_edit:{
                Toast.makeText(requireContext(), "edit icon clicked", Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                String str = gson.toJson(cmp);
                Bundle bundle = new Bundle();
                bundle.putString(ARG_COMPANY_ITEM,str);
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.action_view_cmpny_to_edit_company,bundle);

                return true;
            }
            case R.id.id_menu_remove:{

                new AlertDialog.Builder(requireContext())
                        .setTitle("Delete Company")
                        .setMessage("Are you sure you want to delete this company?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                deleteCompanyData(cmp.getCompanyId());
                                Toast.makeText(requireContext(),"Company deleted successfully",Toast.LENGTH_LONG).show();
                                requireActivity().onBackPressed();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void deleteCompanyData(String ky) {
        binding.progressBarAddCompany.setVisibility(View.VISIBLE);
        Log.d("View","key "+ky );
        firebaseDatabase.getReference().child(COMPANY).child(ky).removeValue();
    }


}
