package com.example.placementapp;

import static com.example.placementapp.constants.AppConstants.CONST_FINAL_STAFF_TYPE;
import static com.example.placementapp.constants.AppConstants.CONST_VAL_ADMIN_TYPE;
import static com.example.placementapp.constants.AppConstants.CONST_VAL_STAFF_TYPE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.placementapp.constants.AppConstants;
import com.example.placementapp.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.placementapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    DrawerLayout drawer = null;
    NavigationView navigationView = null;

    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        /*if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            user.getIdToken(false);

        }*/

        Log.d(TAG,"getIdToken "+user.getIdToken(false)+" token result " );

        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        Menu menu =navigationView.getMenu();

        MenuItem drawer_menu_staff = menu.findItem(R.id.nav_staff);
        MenuItem drawer_menu_companies = menu.findItem(R.id.nav_companies);
        MenuItem drawer_menu_students = menu.findItem(R.id.nav_student);
        MenuItem drawer_menu_internship = menu.findItem(R.id.nav_internship);
        MenuItem drawer_menu_logout = menu.findItem(R.id.nav_logout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        Bundle extras = getIntent().getExtras();

        if(extras.getString(AppConstants.INTENT_USER_TYPE).contains(CONST_VAL_ADMIN_TYPE)){
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_staff, R.id.nav_companies,R.id.nav_student,R.id.nav_internship,R.id.nav_logout)
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

            drawer_menu_staff.setVisible(true);
            drawer_menu_companies.setVisible(true);
            drawer_menu_students.setVisible(true);
            drawer_menu_logout.setVisible(true);
            drawer_menu_internship.setVisible(true);


        }else if (extras.getString(AppConstants.INTENT_USER_TYPE).contains(CONST_VAL_STAFF_TYPE)){
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_companies,R.id.nav_student,R.id.nav_internship,R.id.nav_logout)
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
            drawer_menu_staff.setVisible(false);
            drawer_menu_students.setVisible(true);
            drawer_menu_companies.setVisible(true);
            drawer_menu_logout.setVisible(true);
            drawer_menu_internship.setVisible(true);
        }else{
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_companies,R.id.nav_logout)
                    .setOpenableLayout(drawer)
                    .build();
            drawer_menu_staff.setVisible(false);
            drawer_menu_companies.setVisible(true);
            drawer_menu_students.setVisible(false);
            drawer_menu_logout.setVisible(true);
            drawer_menu_internship.setVisible(true);
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }

        setNavigationViewListener();

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_logout: {
                Toast.makeText(this,"logout clicked",Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                startLoginActivity();

                break;
            }
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
