package com.example.ayonzon.acapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Helpers.InputValidation;
import com.example.ayonzon.acapp.Models.ApplicationPrefs;
import com.example.ayonzon.acapp.Models.Classes;
import com.example.ayonzon.acapp.Models.Students;
import com.example.ayonzon.acapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayonzon on 2/2/2018.
 */

public class StudentMaintenance extends Activity {
    Button add,delete;
    EditText id,name;
    Spinner spinner;
    String _id="",_class="",action="'";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_maintenance);

        add=(Button)findViewById(R.id.add);
        delete=(Button)findViewById(R.id.delete);
        id=(EditText)findViewById(R.id.sID);
        name=(EditText)findViewById(R.id.sName);
        spinner=(Spinner)findViewById(R.id.spinner);

        Bundle extras =getIntent().getExtras();
        if(extras !=null) {
             action = extras.getString("action");

        }

        if(action.equals("add"))
        {
            _class=extras.getString("classcode");
            add.setText("SAVE STUDENT");
            add.setBackground(StudentMaintenance.this.getDrawable(R.drawable.gradient_present));

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!new InputValidation(StudentMaintenance.this).isTextEmpty(id))
                        return;
                    else if(!new InputValidation(StudentMaintenance.this).isTextEmpty(name))
                        return;
                    else
                    {
                        new DBHelper(StudentMaintenance.this).addStudents(new Students(0,_class,name.getText().toString(),id.getText().toString(),id.getText().toString(),"mob"));
                        new DBHelper(StudentMaintenance.this).UpdateFLAG(1);
                        Toast.makeText(StudentMaintenance.this, "Student Added", Toast.LENGTH_SHORT).show();
                        StartIntent();
                    }
                }
            });

        }
        else
        {
            final ArrayList<String> items = new ArrayList<String>();
            String teacher= new ApplicationPrefs(this).getTeacher();





            List<Classes> _classes = new DBHelper(this).getAllClassesViaCode(teacher);
            for(Classes c : _classes)
            {

                String _class = c.getclassCode() + "|" + c.getClassName();
                items.add(_class);


            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item, items);
            spinner.setAdapter(adapter);
            spinner.setVisibility(View.VISIBLE);
            _id=extras.getString("id");
            List<Students>stud=new DBHelper(StudentMaintenance.this).getStudentViaID(_id);
            for (Students s: stud)
            {
                name.setText(s.getStudName());
                id.setText(s.getStudentID());
            }
            delete.setVisibility(View.VISIBLE);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!new InputValidation(StudentMaintenance.this).isTextEmpty(id))
                        return;
                    else if(!new InputValidation(StudentMaintenance.this).isTextEmpty(name))
                        return;
                    else
                    {
                        new DBHelper(StudentMaintenance.this).editStudent(new Students(Integer.parseInt(_id),spinner.getSelectedItem().toString(),name.getText().toString(),id.getText().toString(),id.getText().toString(),"mob"));
                        new DBHelper(StudentMaintenance.this).UpdateFLAG(1);
                        Toast.makeText(StudentMaintenance.this, "Student Updated", Toast.LENGTH_SHORT).show();
                        StartIntent();
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DBHelper(StudentMaintenance.this).deleteStudent(_id);
                    new DBHelper(StudentMaintenance.this).UpdateFLAG(1);
                    Toast.makeText(StudentMaintenance.this, "Student Deleted", Toast.LENGTH_SHORT).show();
                    StartIntent();
                }
            });


        }









    }
    public void StartIntent()
    {
        Intent intent = new Intent(StudentMaintenance.this,StudentClass.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
