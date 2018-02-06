package com.example.ayonzon.acapp.CustomAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ayonzon.acapp.R;

import java.util.ArrayList;

/**
 * Created by ayonzon on 1/31/2018.
 */

public class ClassCustomList extends ArrayAdapter {
    private final Activity context;
    private  ArrayList<String> web = new ArrayList<String>();
    private  ArrayList<String> desc = new ArrayList<String>();
    private final Integer[]imageId;

    public ClassCustomList(Activity context,
                      ArrayList<String> web, ArrayList<String> desc, Integer[] imageId) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.desc = desc;
        this.imageId=imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtDesc = (TextView) rowView.findViewById(R.id.txtdef);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TableRow tb = (TableRow) rowView.findViewById(R.id.tbrow);

        txtTitle.setText(web.get(position));
        txtDesc.setText(desc.get(position) + " students");


        imageView.setImageResource(imageId[0]);
        return rowView;
    }
}
