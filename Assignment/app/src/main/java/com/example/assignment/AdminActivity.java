package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EditText username;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        username = (EditText) findViewById(R.id.usernameText);
        password = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.loginButton);

        //some code for login has been taken from https://www.thegadget360.com/post/login-and-register-activity-using-sqlite-database-in-android-studio
        DBManager DB = new DBManager(this);
        DB.open();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                //if fields are entered empty
                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(AdminActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }

                //if the password and username is correct
                else if(DB.checkUsernamePassword(user, pass)){
                    Toast.makeText(AdminActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(getApplicationContext(), AdminEditActivity.class);
                    startActivity(intent);
                }

                //if username or password don't match the ones in the database
                else{
                    Toast.makeText(AdminActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //some code taken from https://www.youtube.com/watch?v=IAZaVxz1U40
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.adminMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //when clicked change activity depending on which id has been pressed in the menu
                switch (item.getItemId())
                {
                    case R.id.adminMenu:
                        return true;

                    case R.id.homeMenu:
                        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homeIntent);
                        overridePendingTransition(0,0);
                        finish();
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