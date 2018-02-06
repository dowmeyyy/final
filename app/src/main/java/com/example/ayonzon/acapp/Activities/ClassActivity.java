package com.example.ayonzon.acapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ayonzon.acapp.CustomAdapters.ClassCustomList;
import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Models.ApplicationPrefs;
import com.example.ayonzon.acapp.Models.Attendance;
import com.example.ayonzon.acapp.Models.Classes;
import com.example.ayonzon.acapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayonzon on 1/31/2018.
 */

public class ClassActivity extends Activity {
    ListView list;
    Button add;
    ArrayList<String> cname=new ArrayList<String>();
    ArrayList<String> desc=new ArrayList<String>();
    Integer[] imageID={R.drawable.book};
    ArrayList<String> id=new ArrayList<String>();
    String _class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);



        String teacher= new ApplicationPrefs(this).getTeacher();
        int classCount = new DBHelper(this).getClassCount(teacher);

        List<Classes> classes = new DBHelper(this).getAllClassesViaCode(teacher);
        int a=0;
        for(Classes c : classes)
        {

             _class = c.getclassCode() + "|" + c.getClassName();

            cname.add(_class);
            id.add(c.getClassID() + "");
            String count=new DBHelper(this).getStudentCount(_class);
            desc.add(count);
            a++;

        }
        ClassCustomList adapter= new ClassCustomList(ClassActivity.this,cname,desc,imageID);
        list=(ListView) findViewById(R.id.list);
        list.invalidateViews();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pre_class=cname.get(i);
                String [] class_code=pre_class.split("\\|");
                showAlert(class_code[0],id.get(i),pre_class);


            }
        });

        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassActivity.this, ClassMaintenance.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                onDestroy();
            }
        });



    }

    public void showAlert(final String classcode, final String id, final String whole)
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.class_modal, null);
        Button edit=(Button)dialogView.findViewById(R.id.edit);
        Button attendance=(Button)dialogView.findViewById(R.id.attendance);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("SELECT ACTION");

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassActivity.this, ClassMaintenance.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("classcode",classcode.toString());
                intent.putExtra("id", id);
                startActivity(intent);
                onDestroy();
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassActivity.this, SeeClassAttendance.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("classCode",whole);
                intent.putExtra("id", id);
                startActivity(intent);
                onDestroy();
            }
        });

        AlertDialog dialog = dialogBuilder.show();
    }

}
