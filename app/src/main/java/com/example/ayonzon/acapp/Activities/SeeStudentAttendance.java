package com.example.ayonzon.acapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ayonzon.acapp.CustomAdapters.ClassCustomList;
import com.example.ayonzon.acapp.CustomAdapters.StudentAttendanceList;
import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Models.Attendance;
import com.example.ayonzon.acapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayonzon on 2/2/2018.
 */

public class SeeStudentAttendance extends Activity {
    ListView lv;
    ArrayList<String> dt =new ArrayList<String>();
    ArrayList<String> att=new ArrayList<String>();
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_student);

        Bundle extras =getIntent().getExtras();
        if(extras !=null) {
             id = extras.getString("studID");
        }

        List<Attendance> attendance = new DBHelper(this).getAttendanceviaID(id);
        for(Attendance a: attendance)
        {
            dt.add(a.getDate());
            att.add(a.getStatus());
        }

        StudentAttendanceList adapter= new StudentAttendanceList(SeeStudentAttendance.this,dt,att);
        lv=(ListView) findViewById(R.id.list);
        lv.invalidateViews();
        lv.setAdapter(adapter);
    }

}
