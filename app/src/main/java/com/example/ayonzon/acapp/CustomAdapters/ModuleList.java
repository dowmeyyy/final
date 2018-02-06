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
 * Created by ayonzon on 2/2/2018.
 */

public class ModuleList extends ArrayAdapter {
    private final Activity context;
    ArrayList<String> files =new ArrayList<String>();


    public ModuleList(Activity context,ArrayList<String>files) {
        super(context, R.layout.list_single, files);
        this.context = context;
        this.files = files;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtDesc = (TextView) rowView.findViewById(R.id.txtdef);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TableRow tb = (TableRow) rowView.findViewById(R.id.tbrow);

        txtTitle.setText(files.get(position));
        txtDesc.setText("Click to download file.");

        imageView.setImageResource(R.drawable.file);
        return rowView;
    }
}
