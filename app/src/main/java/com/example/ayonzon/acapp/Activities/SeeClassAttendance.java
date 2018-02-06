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
import com.example.ayonzon.acapp.CustomAdapters.SeeClassList;
import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Models.Attendance;
import com.example.ayonzon.acapp.Models.Students;
import com.example.ayonzon.acapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayonzon on 2/2/2018.
 */

public class SeeClassAttendance extends Activity {
    String id;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> status = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    Spinner spin;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_class);
        spin = (Spinner) findViewById(R.id.spinner);
        lv = (ListView) findViewById(R.id.list);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("classCode");
        }


        List<Attendance> attendance = new DBHelper(this).getAttendanceviaClass(id);

        for (Attendance a : attendance) {
            date.add(a.getDate());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, date);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Attendance> att = new DBHelper(SeeClassAttendance.this).getAttendanceviaDate(date.get(i), id);

                for (Attendance at : att) {

                    name.add(at.getStudID());
                    status.add(at.getStatus());


                }
                SeeClassList adapter = new SeeClassList(SeeClassAttendance.this, name, status);
                lv.invalidateViews();
                lv.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });
    }
}
