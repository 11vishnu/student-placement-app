package com.example.placementapp.login;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ActivityLoginBinding binding;

    private String TAG = "LoginActivity";

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

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                                    startMainActivity();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    binding.progressBar.setVisibility(View.GONE);
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        });
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class).putExtra(AppConstants.INTENT_USER_TYPE,"Admin");
        startActivity(intent);
    }
}