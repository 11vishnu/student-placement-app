package com.example.placementapp.login;

import static com.example.placementapp.constants.AppConstants.CONST_FINAL_STAFF_TYPE;
import static com.example.placementapp.constants.AppConstants.CONST_FINAL_STUDENT_TYPE;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREFERENCE;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREF_UID;
import static com.example.placementapp.constants.AppConstants.CONST_VAL_STAFF_TYPE;
import static com.example.placementapp.constants.AppConstants.CONST_VAL_STUDENT_TYPE;
import static com.example.placementapp.constants.AppConstants.USER;
import static com.example.placementapp.constants.AppConstants.USER_TYPE_CONST;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.placementapp.MainActivity;
import com.example.placementapp.constants.AppConstants;
import com.example.placementapp.databinding.ActivityLoginBinding;
import com.example.placementapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ActivityLoginBinding binding;

    private String TAG = "LoginActivity";


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(USER);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Storing data into SharedPreferences
        sharedPreferences = getSharedPreferences(CONST_SHARED_PREFERENCE,MODE_PRIVATE);
        // Creating an Editor object to edit(write to the file)
        myEdit = sharedPreferences.edit();

        binding.btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Functionality for the button...

                binding.progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(binding.edtUsername.getText().toString().trim(), binding.edtPassword.getText().toString().trim())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    binding.progressBar.setVisibility(View.GONE);
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.d(TAG, "signInWithEmail:success"+" "+user.getUid());
                                    setUidSharedPref(user);
                                    getUserType(user);
                                } else {
                                    // If sign in fails, display a message to the user.

                                    binding.progressBar.setVisibility(View.GONE);
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void setUidSharedPref(FirebaseUser user) {
        myEdit.putString(CONST_SHARED_PREF_UID, user.getUid());
        myEdit.commit();
    }

    private void getUserType(FirebaseUser user){
        databaseReference.child(user.getUid()).child(USER_TYPE_CONST).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", "user type const " +String.valueOf(task.getResult().getValue()));
                    startMainActivity(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    private void startMainActivity(String userTypeConst){
        if(userTypeConst.contains(AppConstants.CONST_VAL_ADMIN_TYPE)){
            Intent intent = new Intent(this, MainActivity.class).putExtra(AppConstants.INTENT_USER_TYPE,AppConstants.CONST_VAL_ADMIN_TYPE);
            startActivity(intent);
        }else if (userTypeConst.contains(CONST_VAL_STAFF_TYPE)){
            Intent intent = new Intent(this, MainActivity.class).putExtra(AppConstants.INTENT_USER_TYPE,CONST_VAL_STAFF_TYPE);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, MainActivity.class).putExtra(AppConstants.INTENT_USER_TYPE,CONST_VAL_STUDENT_TYPE);
            startActivity(intent);
        }
    }

}