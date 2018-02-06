package com.example.ayonzon.acapp.Activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Helpers.InputValidation;
import com.example.ayonzon.acapp.Models.ApplicationPrefs;
import com.example.ayonzon.acapp.Models.Classes;
import com.example.ayonzon.acapp.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ayonzon on 1/31/2018.
 */

public class ClassMaintenance extends Activity {
    Button save,delete;
    EditText code, desc;
    TextView days ,from,to;
    Spinner spin;
    String _id="";
   ArrayList<String> _days =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_maintenance);

        save=(Button)findViewById(R.id.save);
        code=(EditText)findViewById(R.id.code);
        desc=(EditText)findViewById(R.id.desc);
        from=(TextView) findViewById(R.id.from);
        to=(TextView) findViewById(R.id.to);
        days=(TextView) findViewById(R.id.days);
        spin=(Spinner)findViewById(R.id.spinner);
        delete=(Button)findViewById(R.id.delete);

        Bundle extras =getIntent().getExtras();
        if(extras !=null)
        {
            String _code=extras.getString("classcode");
             _id=extras.getString("id");
            List<Classes> classEdit= new DBHelper(ClassMaintenance.this).getClassViaCode(_code);
            for(Classes c :classEdit)
            {
                code.setText(c.getclassCode());
                desc.setText(c.getClassName());
                from.setText(c.getTimeIN());
                to.setText(c.getTimeOUT());
                days.setText(c.getDay());
                save.setText("UPDATE CLASS");
                delete.setVisibility(View.VISIBLE);
                save.setBackground(this.getDrawable(R.drawable.gradient_edit));

            }
        }

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String [] selection = getResources().getStringArray(R.array.days);
                if (_days.contains(selection[i]))
                {
                    int pos=0;
                    for(int j=0;j< _days.size();j++)
                    {
                        if(_days.get(j).equalsIgnoreCase(selection[i]))
                        {
                            pos=j;
                        }
                    }
                    _days.remove(pos);
                }
                else
                {

                    _days.add(selection[i]);
                }
                String _day = "";
                for (int a=0;a<_days.size();a++)
                {
                   if(a==_days.size()-1)
                   {
                       _day+=_days.get(a);
                   }
                   else
                   {
                       _day+=_days.get(a) + "|";
                   }

                }

                days.setText(_day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ClassMaintenance.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        from.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ClassMaintenance.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        to.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teacher= new ApplicationPrefs(ClassMaintenance.this).getTeacher();
                if(!new InputValidation(ClassMaintenance.this).isTextEmpty(code))
                    return;
                else if(!new InputValidation(ClassMaintenance.this).isTextEmpty(desc))
                    return;
                else if(days.getText().toString().equals(""))
                    return;
                else if(from.getText().toString().equals(""))
                    return;
                else if(to.getText().toString().equals(""))
                    return;
                else
                {
                    if(save.getText().toString().equals("ADD CLASS"))
                    {
                        new DBHelper(ClassMaintenance.this).addClass(new Classes(0,code.getText().toString(), desc.getText().toString(),days.getText().toString(),from.getText().toString(),to.getText().toString(),teacher,"mob"));
                        new DBHelper(ClassMaintenance.this).UpdateFLAG(1);
                        Toast.makeText(ClassMaintenance.this, "Class Added", Toast.LENGTH_SHORT).show();
                        StartIntent();
                    }
                    else
                    {
                        new DBHelper(ClassMaintenance.this).editClass(new Classes(Integer.parseInt(_id),code.getText().toString(), desc.getText().toString(),days.getText().toString(),from.getText().toString(),to.getText().toString(),teacher,"mob"));
                        new DBHelper(ClassMaintenance.this).UpdateFLAG(1);
                        Toast.makeText(ClassMaintenance.this, "Class Updated", Toast.LENGTH_SHORT).show();
                        StartIntent();
                    }

                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DBHelper(ClassMaintenance.this).deleteClass(Integer.parseInt(_id));
                new DBHelper(ClassMaintenance.this).UpdateFLAG(1);
                Toast.makeText(ClassMaintenance.this,"Class Deleted",Toast.LENGTH_SHORT).show();
                StartIntent();
            }
        });


    }

    public void StartIntent()
    {
        Intent intent = new Intent(ClassMaintenance.this,ClassActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
