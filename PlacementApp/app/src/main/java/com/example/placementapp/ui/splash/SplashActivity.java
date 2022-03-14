package com.example.placementapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.placementapp.MainActivity;
import com.example.placementapp.constants.AppConstants;
import com.example.placementapp.databinding.ActivityLoginBinding;
import com.example.placementapp.databinding.ActivitySplashBinding;
import com.example.placementapp.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class SplashActivity  extends AppCompatActivity {


    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.growingPlantAnim.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        binding.txtAppName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserSignedInFirebase();
            }
        }, 3000);

    }

    private void checkUserSignedInFirebase() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser!=null){
            Task<GetTokenResult> getTokenResultTask = mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                startMainActivity();
                            } else {
                                // Handle error -> task.getException();
                                startLoginActivity();
                            }
                        }
                    });
        }else{
            startLoginActivity();
        }
    }

    private void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class).putExtra(AppConstants.INTENT_USER_TYPE,"Admin");
        startActivity(intent);
    }
}
