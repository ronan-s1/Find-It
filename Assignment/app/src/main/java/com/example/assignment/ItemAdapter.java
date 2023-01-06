package com.example.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    private Context context;
    private int resource;

    public ItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //inflating listview
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        //getting data from the object
        TextView description = convertView.findViewById(R.id.textDescription);
        TextView item = convertView.findViewById(R.id.textItem);
        TextView name = convertView.findViewById(R.id.textName);
        TextView postTime = convertView.findViewById(R.id.textPostTime);

        //setting the text to the data from the object
        description.setText(getItem(position).getDescription());
        item.setText(getItem(position).getItem());
        name.setText(getItem(position).getName());
        postTime.setText(getItem(position).getPostTime());

        return convertView;
    }
}
