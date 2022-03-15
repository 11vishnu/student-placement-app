package com.example.placementapp.ui.splash;

import static com.example.placementapp.constants.AppConstants.CONST_FINAL_STAFF_TYPE;
import static com.example.placementapp.constants.AppConstants.CONST_FINAL_STUDENT_TYPE;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREFERENCE;
import static com.example.placementapp.constants.AppConstants.CONST_SHARED_PREF_UID;
import static com.example.placementapp.constants.AppConstants.CONST_VAL_STAFF_TYPE;
import static com.example.placementapp.constants.AppConstants.CONST_VAL_STUDENT_TYPE;
import static com.example.placementapp.constants.AppConstants.USER;
import static com.example.placementapp.constants.AppConstants.USER_TYPE_CONST;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity  extends AppCompatActivity {


    private ActivitySplashBinding binding;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    private SharedPreferences sh =null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        sh = getSharedPreferences(CONST_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String sharedPrefUid = sh.getString(CONST_SHARED_PREF_UID, "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(USER);

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
        }, 2000);

    }

    private void checkUserSignedInFirebase() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser!=null){
            Task<GetTokenResult> getTokenResultTask = mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                FirebaseUser user = mAuth.getCurrentUser();
                                getUserType(user);
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
