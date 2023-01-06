package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class ItemClicked extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_clicked);
        Button leaveClickedItem = (Button) findViewById(R.id.leaveClickedItem);

        leaveClickedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //unpacking bundle of data passed from the array
        Bundle data = getIntent().getExtras();
        String item = data.getString("item");
        String name = data.getString("name");
        String description = data.getString("description");
        String studentNo = data.getString("studentNo");
        String phone = data.getString("phone");
        String location = data.getString("location");
        String postTime = data.getString("postTime");
        String LF;

        TextView nameText = (TextView) findViewById(R.id.nameClicked);
        TextView itemText = (TextView) findViewById(R.id.itemClicked);
        TextView descriptionText = (TextView) findViewById(R.id.descriptionClicked);
        TextView studentNoText = (TextView) findViewById(R.id.studentClicked);
        TextView phoneText = (TextView) findViewById(R.id.phoneClicked);
        TextView locationText = (TextView) findViewById(R.id.locationClicked);
        TextView postTimeText = (TextView) findViewById(R.id.postTimeClicked);
        TextView LFText = (TextView) findViewById(R.id.statusClicked);
        Button shareButton = (Button) findViewById(R.id.buttonShareItem);

        //displaying lost or found opposed to just L or F
        if (Objects.equals(data.getString("LF"), "F")){
            LF = "Found";
            LFText.setTextColor(Color.parseColor("#18b52a"));
        }
        else{
            LF = "Lost";
            LFText.setTextColor(Color.parseColor("#cf0433"));
        }

        //setting text and name into the activity
        nameText.setText(name);
        itemText.setText(item);
        descriptionText.setText(description);
        studentNoText.setText(studentNo);
        phoneText.setText(phone);
        locationText.setText(location);
        postTimeText.setText(postTime);
        LFText.setText(LF);

        //string to be shared
        String shareString = "A " + LF + " item on FindIt!" +
                "\n\nName:\n" + name +
                "\n\nStudent number:\n" + studentNo +
                "\n\nItem:\n" + item +
                "\n\nDescription:\n" + description +
                "\n\nPhone number:\n" + phone +
                "\n\nLocation:\n" + location +
                "\n\nPosted at:\n" + postTime;

        //code from https://medium.com/androiddevelopers/sharing-content-between-android-apps-2e6db9d1368b
        shareButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {
                //when share button is clicked, prepare the shareString to shared
                Intent shareIntent = ShareCompat.IntentBuilder.from(ItemClicked.this)
                        .setType("text/plain")
                        .setText(shareString)
                        .getIntent();

                //if Activity available to handle the mime type selected, share the content
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                }
            }
        });
    }
}