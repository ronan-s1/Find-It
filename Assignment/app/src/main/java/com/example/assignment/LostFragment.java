package com.example.assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class LostFragment extends Fragment {
    private ListView lostListView;
    private ArrayList<Item> lostList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.found_layout, container, false);
        lostListView = rootView.findViewById(R.id.listView);

        lostList = new ArrayList<>();

        DBManager DB = new DBManager(getActivity());
        DB.open();

        //getting lost items, creating item objects, and adding to array
        Cursor result = DB.getLostItems();
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
            lostList.add(new Item(itemId, item, name, description, studentNo, phone, location, postTime, LF));
        }

        //populating custom listview with lostList
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), R.layout.row, lostList);
        lostListView.setAdapter(itemAdapter);

        //when an item in the list is clicked
        lostListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get data from the object clicked in the listview and send it to a new activity to display the data
                Intent clickedItemIntent = new Intent(getActivity(), ItemClicked.class);
                clickedItemIntent.putExtra("item", String.valueOf(lostList.get(position).getItem()));
                clickedItemIntent.putExtra("name", String.valueOf(lostList.get(position).getName()));
                clickedItemIntent.putExtra("description", String.valueOf(lostList.get(position).getDescription()));
                clickedItemIntent.putExtra("studentNo", String.valueOf(lostList.get(position).getStudentNo()));
                clickedItemIntent.putExtra("phone", String.valueOf(lostList.get(position).getPhone()));
                clickedItemIntent.putExtra("location", String.valueOf(lostList.get(position).getLocation()));
                clickedItemIntent.putExtra("postTime", String.valueOf(lostList.get(position).getPostTime()));
                clickedItemIntent.putExtra("LF", String.valueOf(lostList.get(position).getLF()));
                startActivity(clickedItemIntent);
            }
        });

        return rootView;
    }
}
