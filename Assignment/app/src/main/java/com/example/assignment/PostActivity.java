package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostActivity extends AppCompatActivity implements LocationListener {
    BottomNavigationView bottomNavigationView;
    Button postButton;
    Button locationButton;
    RadioButton lostRadio;
    RadioButton foundRadio;
    EditText name;
    EditText studentNo;
    EditText item;
    EditText description;
    EditText phone;
    EditText location;

    LocationManager locationManager;
    long minTime = 500;
    float minDistance = 1;
    static final int MY_PERMISSION_GPS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postButton = (Button) findViewById(R.id.buttonPost);
        locationButton = (Button) findViewById(R.id.locationButtonPost);
        name = (EditText) findViewById(R.id.namePost);
        studentNo = (EditText) findViewById(R.id.studentNumberPost);
        item = (EditText) findViewById(R.id.itemPost);
        description = (EditText) findViewById(R.id.descriptionPost);
        phone = (EditText) findViewById(R.id.phonePost);
        location = (EditText) findViewById(R.id.locationPost);
        foundRadio = (RadioButton) findViewById(R.id.foundPost);
        lostRadio = (RadioButton) findViewById(R.id.lostPost);

        DBManager DB = new DBManager(this);
        DB.open();

        locationButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            //checking permissions for location
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PostActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                }
                //display loading until location has been tracked
                location.setText("Loading...");
                getLocation();
            }
        });

        //when post button is pressed
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = false;
                String nameString = name.getText().toString();
                String studentNoString = studentNo.getText().toString();
                String itemString = item.getText().toString();
                String descriptionString = description.getText().toString();
                String phoneString = phone.getText().toString();
                String locationString = location.getText().toString();

                //some code has been used from https://www.digitalocean.com/community/tutorials/java-simpledateformat-java-date-format
                //getting time and date and formatting it
                String postTime = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(Calendar.getInstance().getTime());

                //if fields arent filled properly
                if (nameString.equals("") || studentNoString.equals("") || itemString.equals("") || descriptionString.equals("") || phoneString.equals("") || locationString.equals("") || !foundRadio.isChecked() && !lostRadio.isChecked()) {
                    Toast.makeText(PostActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //checking which radio button has been pressed
                else if (foundRadio.isChecked()) {
                    isInserted = DB.insertPost(nameString, studentNoString, itemString, descriptionString, phoneString, locationString, postTime, "F");
                }

                else if (lostRadio.isChecked()) {
                    isInserted = DB.insertPost(nameString, studentNoString, itemString, descriptionString, phoneString, locationString, postTime, "L");
                }

                //if insertion is successful clear all fields and display a toast
                if (isInserted) {
                    Toast.makeText(PostActivity.this, "Post Created!", Toast.LENGTH_SHORT).show();
                    name.getText().clear();
                    studentNo.getText().clear();
                    item.getText().clear();
                    description.getText().clear();
                    phone.getText().clear();
                    location.getText().clear();
                }

                //else display error toast
                else {
                    Toast.makeText(PostActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //some code taken from https://www.youtube.com/watch?v=IAZaVxz1U40
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.postMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //when clicked change activity depending on which id has been pressed in the menu
                switch (item.getItemId()) {
                    case R.id.adminMenu:
                        Intent adminIntent = new Intent(getApplicationContext(), AdminActivity.class);
                        adminIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(adminIntent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.homeMenu:
                        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homeIntent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.postMenu:
                        return true;
                }
                return false;
            }
        });
    }

    //some code taken from lab 9
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, PostActivity.this);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void onLocationChanged(Location setLocation) {
        try {
            //getting current location address using coordinates
            Geocoder geocoder = new Geocoder(PostActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(setLocation.getLatitude(), setLocation.getLongitude(), MY_PERMISSION_GPS);
            String address = addresses.get(0).getAddressLine(0);
            location.setText(address);
            locationManager.removeUpdates(this);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}