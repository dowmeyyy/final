package com.example.ayonzon.acapp.CustomAdapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ayonzon.acapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ayonzon on 2/2/2018.
 */

public class StudentAttendanceList extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<String> date;
    private final ArrayList<String>pre;
    private String []imageID;

    public StudentAttendanceList(Activity context, ArrayList<String> date, ArrayList<String> pre)
    {
        super(context, R.layout.studentattendance_list_single,date);
        this.context=context;
        this.date=date;
        this.pre=pre;


    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.studentattendance_list_single, null, true);
        TextView dt = (TextView) rowView.findViewById(R.id.dt);
        TextView stat=(TextView)rowView.findViewById(R.id.stat);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TableRow tb = (TableRow) rowView.findViewById(R.id.tbrow);
        TableLayout tl= (TableLayout)rowView.findViewById(R.id.tl);




        dt.setText(date.get(position));

        String pre_isthere = pre.get(position);
        stat.setText(pre_isthere);
        if(!pre_isthere.equals(""))
        {
            if(pre_isthere.equals("PRESENT"))
            {
                tb.setBackgroundResource(R.drawable.gradient_present);

            }
            else if(pre_isthere.equals("ABSENT"))
            {
                tb.setBackgroundResource(R.drawable.gradient_absent);

            }
            else if(pre_isthere.equals("LATE"))
            {
                tb.setBackgroundResource(R.drawable.gradient_late);

            }

        }



        imageView.setImageResource(R.drawable.user);
        return rowView;
    }

}
