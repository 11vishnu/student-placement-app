package com.example.placementapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.placementapp.constants.AppConstants;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.placementapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        Menu menu =navigationView.getMenu();

        MenuItem drawer_menu_staff = menu.findItem(R.id.nav_staff);
        MenuItem drawer_menu_companies = menu.findItem(R.id.nav_companies);
        MenuItem drawer_menu_students = menu.findItem(R.id.nav_student);
        MenuItem drawer_menu_internship = menu.findItem(R.id.nav_internship);
        MenuItem drawer_menu_logout = menu.findItem(R.id.nav_logout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        Bundle extras = getIntent().getExtras();

        if(extras.getString(AppConstants.INTENT_USER_TYPE).contains(AppConstants.CONST_FINAL_ADMIN_TYPE)){
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


        }else if (extras.getString(AppConstants.INTENT_USER_TYPE).contains(AppConstants.CONST_FINAL_STAFF_TYPE)){
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

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}