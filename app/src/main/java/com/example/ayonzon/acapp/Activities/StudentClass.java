package com.example.ayonzon.acapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ayonzon.acapp.CustomAdapters.ClassAttendanceList;
import com.example.ayonzon.acapp.CustomAdapters.StudentList;
import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Models.ApplicationPrefs;
import com.example.ayonzon.acapp.Models.Classes;
import com.example.ayonzon.acapp.Models.Students;
import com.example.ayonzon.acapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayonzon on 2/2/2018.
 */

public class StudentClass extends Activity {
    Spinner spin;
    ListView lv;
    Button add;
    private ArrayList<String>id=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        spin=(Spinner)findViewById(R.id.spinner);
        lv=(ListView)findViewById(R.id.list);
        add=(Button)findViewById(R.id.add);

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
                List<Students> student= new DBHelper(StudentClass.this).getStudentViaCode(items.get(i));
                ArrayList<String> student_name=new ArrayList<String>();
                ArrayList<String> pre=new ArrayList<String>();
                for (Students s : student)
                {
                    Log.d("STUDENT",s.getStudName());
                    student_name.add(s.getStudentID()+"|"+s.getStudName());
                    String isthere=new DBHelper(StudentClass.this).getAttendance(s.getStudentID());
                    id.add(s.getStudID()+ "");


                    pre.add(isthere);


                }
                StudentList adapter= new StudentList(StudentClass.this,student_name,pre,id);
                lv.invalidateViews();
                lv.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StudentClass.this, StudentMaintenance.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("classcode",spin.getSelectedItem().toString());
                intent.putExtra("action","add");
                startActivity(intent);
                onDestroy();

            }
        });



    }
}
