package com.example.placementapp.ui.resume;

import static com.example.placementapp.constants.AppConstants.ARG_COMPANY_ITEM;
import static com.example.placementapp.constants.AppConstants.ARG_PDF_URL;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREFERENCE;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREF_EMAIL_ID;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREF_UID;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREF_UPLOAD_RESUME_NAME;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREF_UPLOAD_RESUME_URL;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREF_USER_TYPE;
import static com.example.placementapp.constants.AppConstants.USER;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.navigation.Navigation;

import com.example.placementapp.R;
import com.example.placementapp.constants.AppConstants;
import com.example.placementapp.databinding.EditCompanyFragmentBinding;
import com.example.placementapp.databinding.ResumeFragmentBinding;
import com.example.placementapp.ui.company.Company;
import com.example.placementapp.ui.company.EditCompanyFragmentArgs;
import com.example.placementapp.ui.dataModels.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResumeFragment extends Fragment {

    private ResumeFragmentBinding binding;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private SharedPreferences sh =null;
    String sharedPrefResumeName = "";
    String sharedPrefResumeUrl = "";
    String resumeUrl = "";
    String resumeName = "";

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

        databaseReference = firebaseDatabase.getReference(USER);
        getUploadedFile();

        binding.txtViewResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                CharSequence options[] = new CharSequence[]{
                        "Download",
                        "View",
                        "Cancel"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Choose One");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // we will be downloading the pdf
                        if (which == 0) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(resumeUrl));
                            startActivity(intent);
                        }
                        // We will view the pdf
                        if (which == 1) {
                           /* Intent intent = new Intent(v.getContext(), ViewPdfActivity.class);
                            intent.putExtra("url", message);
                            startActivity(intent);*/

                            Toast.makeText(requireContext(),"view pdf selected ",Toast.LENGTH_LONG).show();
                            Bundle bundle = new Bundle();
                            bundle.putString(ARG_PDF_URL,resumeUrl);
                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.action_view_upload_resume_to_view_pdf,bundle);

                           /* Intent target = new Intent(Intent.ACTION_VIEW);
                            target.setDataAndType(Uri.parse(sharedPrefResumeUrl),"application/pdf");
                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                            Intent intent = Intent.createChooser(target, "Open File");
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                // Instruct the user to install a PDF reader here, or something
                                Toast.makeText(requireContext(),"install pdf viewer",Toast.LENGTH_LONG).show();
                            }*/
                        }
                    }
                });
                builder.show();
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
                getUploadedFile();
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

    private void getUploadedFile(){
        String sharedPrefResumeName = sh.getString(CONST_SHARED_PREF_UPLOAD_RESUME_NAME, "");
        String sharedPrefResumeUrl = sh.getString(CONST_SHARED_PREF_UPLOAD_RESUME_URL,"");
        String sharedUserId = sh.getString(CONST_SHARED_PREF_UID,"");

        databaseReference.child(sharedUserId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }else {
                    Log.d("firebase", "user type const " +String.valueOf(task.getResult().getValue()));
                    Map<String, String> map = (Map<String, String>) task.getResult().getValue();
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(map);
                    User user = gson.fromJson(jsonElement, User.class);
                    if((user.getUploadPdf()!=null)&&(user.getUploadPdf().getUrl()!=null)&&(user.getUploadPdf().getName()!=null)){
                        resumeUrl = user.getUploadPdf().getUrl();
                        resumeName = user.getUploadPdf().getName();
                    }

                    if((!resumeUrl.isEmpty())&&(!resumeName.isEmpty())){
                        binding.txtViewResume.setVisibility(View.VISIBLE);
                        binding.txtViewResume.setText(resumeName);
                    }else{
                        binding.txtViewResume.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

}
