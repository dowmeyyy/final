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
 * Created by ayonzon on 2/1/2018.
 */

public class ClassAttendanceList extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<String> student;
    private final ArrayList<String>pre;
    private String []imageID;
    private String classCode;

    public ClassAttendanceList(Activity context, ArrayList<String> student, ArrayList<String> pre,String classCode)
    {
        super(context, R.layout.attendance_list_single,student);
        this.context=context;
        this.student=student;
        this.pre=pre;
        this.classCode=classCode;


    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.attendance_list_single, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.txt);
        Button present=(Button)rowView.findViewById(R.id.present);
        Button absent =(Button)rowView.findViewById(R.id.absent);
        Button late=(Button)rowView.findViewById(R.id.tardy);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TableRow tb = (TableRow) rowView.findViewById(R.id.tbrow);
        TableLayout tl= (TableLayout)rowView.findViewById(R.id.tl);
        LinearLayout buttons=(LinearLayout)rowView.findViewById(R.id.buttons);



        final String [] student_set=student.get(position).split("\\|");
        txtName.setText(student_set[1]);

        String pre_isthere = pre.get(position);
        if(!pre_isthere.equals(""))
        {
            Log.d("ISTHERE",pre_isthere);
            if(pre_isthere.equals("PRESENT"))
            {
                tb.setBackgroundResource(R.drawable.gradient_present);
                buttons.setVisibility(View.GONE);
            }
            else if(pre_isthere.equals("ABSENT"))
            {
                tb.setBackgroundResource(R.drawable.gradient_absent);
                buttons.setVisibility(View.GONE);
            }
            else if(pre_isthere.equals("LATE"))
            {
                tb.setBackgroundResource(R.drawable.gradient_late);
                buttons.setVisibility(View.GONE);
            }

        }
        else
        {
            present.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createAlertDialog(student.get(position),"PRESENT");
                }
            });

            absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createAlertDialog(student.get(position),"ABSENT");
                }
            });

            late.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createAlertDialog(student.get(position),"LATE");
                }
            });
        }




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
