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

/**
 * Created by ayonzon on 1/31/2018.
 */

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final String[] desc;
    private final Integer[] imageId;
    private final Integer[] colors;

    public CustomList(Activity context,
                      String[] web, String[] desc, Integer[] imageId, Integer[] colors) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.desc = desc;
        this.imageId = imageId;

        this.colors = colors;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtDesc = (TextView) rowView.findViewById(R.id.txtdef);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TableRow tb = (TableRow) rowView.findViewById(R.id.tbrow);

        txtTitle.setText(web[position]);
        txtDesc.setText(desc[position]);

        tb.setBackgroundResource(colors[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
