package com.example.ayonzon.acapp.CustomAdapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.ayonzon.acapp.Activities.ClassActivity;
import com.example.ayonzon.acapp.Activities.ClassMaintenance;
import com.example.ayonzon.acapp.Activities.SeeStudentAttendance;
import com.example.ayonzon.acapp.Activities.StudentMaintenance;
import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Models.Attendance;
import com.example.ayonzon.acapp.R;

import java.util.ArrayList;

/**
 * Created by ayonzon on 2/2/2018.
 */

public class StudentList extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<String> student;
    private final ArrayList<String>pre;
    private final ArrayList<String>id;
    private String []imageID;

    public StudentList(Activity context, ArrayList<String> student, ArrayList<String> pre, ArrayList<String> id)
    {
        super(context, R.layout.student_list_single,student);
        this.context=context;
        this.student=student;
        this.pre=pre;
        this.id=id;


    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.student_list_single, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.txt);
        Button edit=(Button)rowView.findViewById(R.id.edit);
        Button attendance =(Button)rowView.findViewById(R.id.attendance);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TableRow tb = (TableRow) rowView.findViewById(R.id.tbrow);
        TableLayout tl= (TableLayout)rowView.findViewById(R.id.tl);
        LinearLayout buttons=(LinearLayout)rowView.findViewById(R.id.buttons);



        final String [] student_set=student.get(position).split("\\|");
        txtName.setText(student_set[1]);


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, StudentMaintenance.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("id", id.get(position));
                    intent.putExtra("action","edit");
                    context.startActivity(intent);
                }
            });

            attendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SeeStudentAttendance.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("studID", student.get(position));
                    context.startActivity(intent);
                }
            });







        imageView.setImageResource(R.drawable.user);
        return rowView;
    }


}
