package com.example.assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FoundFragment extends Fragment {
    private ListView foundListView;
    private ArrayList<Item> foundList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.found_layout, container, false);
        foundListView = rootView.findViewById(R.id.listView);

        foundList = new ArrayList<>();

        DBManager DB = new DBManager(getActivity());
        DB.open();

        //getting lost items, creating item objects, and adding to array
        Cursor result = DB.getFoundItems();
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
            foundList.add(new Item(itemId, item, name, description, studentNo, phone, location, postTime, LF));
        }

        //populating custom listview with lostList
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), R.layout.row, foundList);
        foundListView.setAdapter(itemAdapter);

        //when an item in the list is clicked
        foundListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get data from the object clicked in the listview and send it to a new activity to display the data
                Intent clickedItemIntent = new Intent(getActivity(), ItemClicked.class);
                clickedItemIntent.putExtra("item", String.valueOf(foundList.get(position).getItem()));
                clickedItemIntent.putExtra("name", String.valueOf(foundList.get(position).getName()));
                clickedItemIntent.putExtra("description", String.valueOf(foundList.get(position).getDescription()));
                clickedItemIntent.putExtra("studentNo", String.valueOf(foundList.get(position).getStudentNo()));
                clickedItemIntent.putExtra("phone", String.valueOf(foundList.get(position).getPhone()));
                clickedItemIntent.putExtra("location", String.valueOf(foundList.get(position).getLocation()));
                clickedItemIntent.putExtra("postTime", String.valueOf(foundList.get(position).getPostTime()));
                clickedItemIntent.putExtra("LF", String.valueOf(foundList.get(position).getLF()));
                startActivity(clickedItemIntent);
            }
        });

        return rootView;
    }
}
