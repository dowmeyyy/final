package com.example.ayonzon.acapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ayonzon.acapp.CustomAdapters.ClassAttendanceList;
import com.example.ayonzon.acapp.CustomAdapters.ClassCustomList;
import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Models.ApplicationPrefs;
import com.example.ayonzon.acapp.Models.Attendance;
import com.example.ayonzon.acapp.Models.Classes;
import com.example.ayonzon.acapp.Models.Students;
import com.example.ayonzon.acapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayonzon on 2/1/2018.
 */

public class AttendanceClass extends Activity {
    Spinner spin;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        spin=(Spinner)findViewById(R.id.spinner);
        lv=(ListView)findViewById(R.id.list);

        final ArrayList<String> items = new ArrayList<String>();
        String teacher= new ApplicationPrefs(this).getTeacher();





        List<Classes> _classes = new DBHelper(this).getAllClassesViaCode(teacher);
        for(Classes c : _classes)
        {

            String _class = c.getclassCode() + "|" + c.getClassName();
            items.add(_class);


        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item, items);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Students> student= new DBHelper(AttendanceClass.this).getStudentViaCode(items.get(i));
                ArrayList<String> student_name=new ArrayList<String>();
                ArrayList<String> pre=new ArrayList<String>();
                for (Students s : student)
                {
                    Log.d("STUDENT",s.getStudName());
                    student_name.add(s.getStudentID()+"|"+s.getStudName());
                    String stud=s.getStudentID() + "|" + s.getStudName();
                    String isthere=new DBHelper(AttendanceClass.this).getAttendance(stud);


                    pre.add(isthere);


                }
                ClassAttendanceList adapter= new ClassAttendanceList(AttendanceClass.this,student_name,pre,spin.getSelectedItem().toString());
                lv.invalidateViews();
                lv.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

}
    }
