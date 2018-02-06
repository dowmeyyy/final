package com.example.ayonzon.acapp.CustomAdapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Models.Attendance;
import com.example.ayonzon.acapp.R;

import java.util.ArrayList;

/**
 * Created by ayonzon on 2/2/2018.
 */

public class SeeClassList extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<String> name;
    private final ArrayList<String>status;
    private String []imageID;
    private String classCode;

    public SeeClassList(Activity context, ArrayList<String> name, ArrayList<String> status)
    {
        super(context, R.layout.attendance_list_single,name);
        this.context=context;
        this.name=name;
        this.status=status;



    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.studentattendance_list_single, null, true);
        TextView nm = (TextView) rowView.findViewById(R.id.dt);
        TextView stat=(TextView)rowView.findViewById(R.id.stat);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TableRow tb = (TableRow) rowView.findViewById(R.id.tbrow);
        TableLayout tl= (TableLayout)rowView.findViewById(R.id.tl);

        String[]_name=name.get(position).split("\\|");
        nm.setText(_name[1]);

        String pre_isthere = status.get(position);
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
        stat.setText(status.get(position));






        imageView.setImageResource(R.drawable.user);
        return rowView;
    }

    public void createAlertDialog(final String studID, final String status)
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.note_modal, null);
        final EditText note=(EditText)dialogView.findViewById(R.id.note);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Remarks");
        dialogBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                new DBHelper(context).addAttendance(new Attendance(0,studID,"",status,"mob",note.getText().toString(),classCode));
                new DBHelper(context).UpdateFLAG(1);
                context.recreate();
            }
        });
        dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = dialogBuilder.show();
    }
}
