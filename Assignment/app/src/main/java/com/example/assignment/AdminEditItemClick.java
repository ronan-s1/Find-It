package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class AdminEditItemClick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_item_click);

        DBManager DB = new DBManager(this);
        DB.open();

        Button leaveClickedItem = (Button) findViewById(R.id.leaveClickedItem);

        leaveClickedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button updateButton = (Button) findViewById(R.id.updateAdminEdit);
        Button deleteButton = (Button) findViewById(R.id.deleteAdminEdit);

        //unpacking data
        Bundle data = getIntent().getExtras();
        String itemId = data.getString("itemId");
        String item = data.getString("item");
        String name = data.getString("name");
        String description = data.getString("description");
        String studentNo = data.getString("studentNo");
        String phone = data.getString("phone");
        String location = data.getString("location");
        String postTime = data.getString("postTime");
        String LF;

        EditText nameText = (EditText) findViewById(R.id.nameClicked);
        TextView itemIdText = (TextView) findViewById(R.id.itemIDClicked);
        EditText itemText = (EditText) findViewById(R.id.itemClicked);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionClicked);
        EditText studentNoText = (EditText) findViewById(R.id.studentClicked);
        EditText phoneText = (EditText) findViewById(R.id.phoneClicked);
        EditText locationText = (EditText) findViewById(R.id.locationClicked);
        TextView postTimeText = (TextView) findViewById(R.id.postTimeClicked);
        TextView LFText = (TextView) findViewById(R.id.statusClicked);

        //displaying lost or found opposed to just L or F
        if (Objects.equals(data.getString("LF"), "F")){
            LF = "Found";
            LFText.setTextColor(Color.parseColor("#18b52a"));
        }
        else{
            LF = "Lost";
            LFText.setTextColor(Color.parseColor("#cf0433"));
        }

        nameText.setText(name);
        itemIdText.setText(itemId);
        itemText.setText(item);
        descriptionText.setText(description);
        studentNoText.setText(studentNo);
        phoneText.setText(phone);
        locationText.setText(location);
        postTimeText.setText(postTime);
        LFText.setText(LF);

        //when update button is clicked
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean updated = false;
                String nameEdited = nameText.getText().toString();
                String studentNoEdited = studentNoText.getText().toString();
                String itemEdited = itemText.getText().toString();
                String descriptionEdited = descriptionText.getText().toString();
                String phoneEdited = phoneText.getText().toString();
                String locationEdit = locationText.getText().toString();

                //if fields are empty don't update and display toast
                if (nameEdited.equals("") || studentNoEdited.equals("") || itemEdited.equals("") || descriptionEdited.equals("") || phoneEdited.equals("") || locationEdit.equals("")) {
                    Toast.makeText(AdminEditItemClick.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }

                //if all fields are filled,
                else{
                    updated = DB.updateItem(itemId, nameEdited, studentNoEdited, itemEdited, descriptionEdited, phoneEdited, locationEdit);
                }

                //if update was successful
                if (updated) {
                    Toast.makeText(AdminEditItemClick.this, "Updated Item!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

                //if update was unsuccessful
                else {
                    Toast.makeText(AdminEditItemClick.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //when delete button is clicked
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DB.deleteItem(itemId)) {
                    Toast.makeText(AdminEditItemClick.this, "Item " + itemId + " has been deleted!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                else {
                    Toast.makeText(AdminEditItemClick.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}