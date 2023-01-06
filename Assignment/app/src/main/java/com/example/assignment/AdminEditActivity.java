package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminEditActivity extends AppCompatActivity {
    private ListView itemListView;
    private ArrayList<Item> itemList;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit);

        itemListView = findViewById(R.id.allItemsListView);
        itemList = new ArrayList<>();

        DBManager DB = new DBManager(this);
        DB.open();

        //adding all item objects to array
        Cursor result = DB.getAllItems();
        while (result.moveToNext()){
            int itemId = result.getInt(0);
            String item = result.getString(1);
            String name = result.getString(2);
            String description = result.getString(3);
            String studentNo = result.getString(4);
            String phone = result.getString(5);
            String location = result.getString(6);
            String postTime = result.getString(7);
            String LF = result.getString(8);
            itemList.add(new Item(itemId, item, name, description, studentNo, phone, location, postTime, LF));
        }

        //adding the objects to the listview
        ItemAdapter itemAdapter = new ItemAdapter(this, R.layout.row, itemList);
        itemListView.setAdapter(itemAdapter);

        //start new activity with all the details when item in the listview has been clicked
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickedItemIntent = new Intent(AdminEditActivity.this, AdminEditItemClick.class);
                clickedItemIntent.putExtra("itemId", String.valueOf(itemList.get(position).getItem_id()));
                clickedItemIntent.putExtra("item", String.valueOf(itemList.get(position).getItem()));
                clickedItemIntent.putExtra("name", String.valueOf(itemList.get(position).getName()));
                clickedItemIntent.putExtra("description", String.valueOf(itemList.get(position).getDescription()));
                clickedItemIntent.putExtra("studentNo", String.valueOf(itemList.get(position).getStudentNo()));
                clickedItemIntent.putExtra("phone", String.valueOf(itemList.get(position).getPhone()));
                clickedItemIntent.putExtra("location", String.valueOf(itemList.get(position).getLocation()));
                clickedItemIntent.putExtra("postTime", String.valueOf(itemList.get(position).getPostTime()));
                clickedItemIntent.putExtra("LF", String.valueOf(itemList.get(position).getLF()));
                startActivity(clickedItemIntent);
            }
        });

        //going back to previous activity
        Button leaveClickedItem = (Button) findViewById(R.id.leaveAdmin);
        leaveClickedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //bits of this code was taken from https://www.geeksforgeeks.org/pull-to-refresh-with-listview-in-android-with-example/
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //when user swipes up, send user to same activity and end previous same activity
                Intent refresh = new Intent(AdminEditActivity.this, AdminEditActivity.class);
                finish();
                overridePendingTransition(0, 0);
                startActivity(refresh);
                overridePendingTransition(0, 0);
            }
        });
    }
}