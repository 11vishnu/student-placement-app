package com.example.placementapp.ui.resume;

import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREFERENCE;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREF_UID;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREF_USER_TYPE;
import static com.example.placementapp.constants.AppConstants.USER;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.placementapp.databinding.EditCompanyFragmentBinding;
import com.example.placementapp.databinding.ResumeFragmentBinding;
import com.example.placementapp.ui.company.Company;
import com.example.placementapp.ui.company.EditCompanyFragmentArgs;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

public class ResumeFragment extends Fragment {

    private ResumeFragmentBinding binding;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private SharedPreferences sh =null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = ResumeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setHasOptionsMenu(true);
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        //databaseReference = FirebaseDatabase.getInstance().getReference("uploadPdf");
        sh = requireActivity().getSharedPreferences(CONST_SHARED_PREFERENCE, Context.MODE_PRIVATE);

        binding.txtUploadFile.setEnabled(false);
        binding.txtSelectStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });

    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        openPdfActivityResultLauncher.launch(intent);

    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> openPdfActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if((data!=null)){
                            binding.txtUploadFile.setEnabled(true);
                            binding.txtSelectStorage.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
                            binding.txtUploadFile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    uploadPdfFile(data.getData());
                                }
                            });
                        }
                    }
                }
            });

    private void uploadPdfFile(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("File is loading....");
        progressDialog.show();

        StorageReference ref = storageReference.child("upload"+System.currentTimeMillis()+".pdf");
        ref.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri = uriTask.getResult();

                putPdf putPdf = new putPdf(binding.txtSelectStorage.getText().toString(),uri.toString());
                //databaseReference.child(databaseReference.push().getKey()).setValue(putPdf);

                String sharedPrefUserId = sh.getString(CONST_SHARED_PREF_UID, "");
                FirebaseDatabase.getInstance().getReference(USER).child(sharedPrefUserId).child("uploadPdf").setValue(putPdf);

                Toast.makeText(requireContext(),"File Upload",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Uploaded..."+(int)progress+"%");
            }
        });

    }

}
