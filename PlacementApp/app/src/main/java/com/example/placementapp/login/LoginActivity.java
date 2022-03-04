package com.example.placementapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.placementapp.MainActivity;
import com.example.placementapp.databinding.ActivityLoginBinding;
import com.example.placementapp.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {


    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Functionality for the button...
                startMainActivity();
            }
        });
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}