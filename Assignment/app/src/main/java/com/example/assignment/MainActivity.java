package com.example.assignment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.example.assignment.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inserting Admin username and password if not already in the database
        DBManager DB = new DBManager(this);
        DB.open();
        DB.insertAdmin();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        //fill view pager with the fragment and listview
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        //some code taken from https://www.youtube.com/watch?v=IAZaVxz1U40
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.homeMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //when clicked change activity depending on which id has been pressed in the menu
                switch (item.getItemId())
                {
                    case R.id.adminMenu:
                        Intent adminIntent = new Intent(getApplicationContext(), AdminActivity.class);
                        adminIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(adminIntent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.homeMenu:
                        return true;

                    case R.id.postMenu:
                        Intent postIntent = new Intent(getApplicationContext(), PostActivity.class);
                        postIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(postIntent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
}