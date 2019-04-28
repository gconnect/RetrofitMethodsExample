package com.glory.retrofitmethodsexamples.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.glory.retrofitmethodsexamples.R;
import com.glory.retrofitmethodsexamples.fragments.HomeFragment;
import com.glory.retrofitmethodsexamples.fragments.SettingsFragment;
import com.glory.retrofitmethodsexamples.fragments.UsersFragment;
import com.glory.retrofitmethodsexamples.model.User;
import com.glory.retrofitmethodsexamples.storage.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new HomeFragment());

    }

    private void displayFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.home:
                fragment = new HomeFragment();
                break;
            case R.id.users:
                fragment = new UsersFragment();
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                break;
        }

        if(fragment != null){
            displayFragment(fragment);
        }
        return false;
    }
}
