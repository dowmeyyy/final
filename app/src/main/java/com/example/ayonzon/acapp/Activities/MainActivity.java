package com.example.ayonzon.acapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ayonzon.acapp.CustomAdapters.CustomList;
import com.example.ayonzon.acapp.CustomAdapters.ModuleList;
import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Helpers.InputValidation;
import com.example.ayonzon.acapp.Models.ApplicationPrefs;
import com.example.ayonzon.acapp.Models.Attendance;
import com.example.ayonzon.acapp.Models.Modules;
import com.example.ayonzon.acapp.Models.Students;
import com.example.ayonzon.acapp.Models.Teachers;
import com.example.ayonzon.acapp.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    ListView list;
    TextView tv;
    ImageView logout;
    String[] web = {
            "Attendance",
            "Classes",
            "Students",
    } ;

    String[] desc = {
            "Check who's here",
            "See what classes you have",
            "See who are your students",
    };

    Integer[] imageId = {
            R.drawable.attendance,
            R.drawable.class1,
            R.drawable.students,

    };

    Integer[] colors={
            R.drawable.three,
            R.drawable.two,
            R.drawable.one,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView)findViewById(R.id.name);
        String account=new ApplicationPrefs(this).getAccount();
        String [] _account= account.split("\\|");
        String name= _account[1];
        Log.d("HELLO" , account);
        tv.setText( name);
        String acc=new ApplicationPrefs(MainActivity.this).getAccount();
        String [] _acc=acc.split("\\|");
        final String type=_acc[2];
        String code=new ApplicationPrefs(MainActivity.this).getClassz();
        list=(ListView)findViewById(R.id.list);
        logout=(ImageView)findViewById(R.id.logout);
        if(type.equals("STUDENT"))
        {
            Log.d("CODEE",code);
            List<Modules> b=new DBHelper(MainActivity.this).getAllModules(code);
            ArrayList<String> n=new ArrayList<String>();
            final ArrayList<String> path=new ArrayList<String>();
            for(Modules m:b)
            {
                Log.d("FNAME",m.getFilename());
                n.add(m.getFilename());
                path.add(m.getFilepath());

            }
            ModuleList adapter2=new ModuleList(MainActivity.this,n);
            list.setAdapter(adapter2);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent in = new Intent(Intent.ACTION_VIEW);
                    in.setData(Uri.parse(path.get(position)));
                    startActivity(in);


                }
            });
        }
        else
        {
            CustomList adapter = new
                    CustomList(MainActivity.this, web, desc, imageId, colors);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    if (position == 0) {
                        Intent intent = new Intent(MainActivity.this, AttendanceClass.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);

                    }else if (position == 1) {
                        Intent intent = new Intent(MainActivity.this, ClassActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);

                    }else if (position == 2) {
                        Intent intent = new Intent(MainActivity.this, StudentClass.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }

                }
            });
        }








        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAlert();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ApplicationPrefs(MainActivity.this).removeReferences();
                startIntent();
            }
        });
    }

    public void launchAlert()
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.account_modal, null);
        final EditText name=(EditText)dialogView.findViewById(R.id.name);
        final EditText username=(EditText)dialogView.findViewById(R.id.username);
        final EditText email=(EditText)dialogView.findViewById(R.id.email);
        final EditText password=(EditText)dialogView.findViewById(R.id.pass);
        final EditText confirm=(EditText)dialogView.findViewById(R.id.confirm);
        final Button save=(Button) dialogView.findViewById(R.id.save);

        String acc=new ApplicationPrefs(MainActivity.this).getAccount();
        String [] account=acc.split("\\|");
        final String type=account[2];
        final String uname=account[0];
        String nme=account[1];

        if(type.equals("STUDENT"))
        {
            List<Students> stud=new DBHelper(MainActivity.this).getStudentViaStudID(uname);
            email.setVisibility(View.GONE);
            for(Students s:stud)
            {
                name.setText(s.getStudName());
                username.setText(s.getStudentID());

            }

        }
        else
        {
            List<Teachers> teachers=new DBHelper(MainActivity.this).getTeachersviaID(uname);
            email.setVisibility(View.VISIBLE);
            for(Teachers t:teachers)
            {
                name.setText(t.getTeacherName());
                username.setText(t.getUsername());
                email.setText(t.getEmail());

            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (save.getText().equals("EDIT"))
                {
                    name.setEnabled(true);
                    username.setEnabled(true);
                    email.setEnabled(true);
                    password.setVisibility(View.VISIBLE);
                    confirm.setVisibility(View.VISIBLE);
                    save.setText("SAVE");
                    save.setBackground(MainActivity.this.getDrawable(R.drawable.gradient_present));
                }
                else
                {
                    if(type.equals("STUDENT")) {
                        if (!new InputValidation(MainActivity.this).isTextEmpty(name))
                            return;
                        else if (!new InputValidation(MainActivity.this).isTextEmpty(username))
                            return;
                        else if (new InputValidation(MainActivity.this).isTextEmpty(password)) {
                            if (password.getText().length() < 6) {
                                password.setError("Please enter a password of atleast 6 characters");
                            } else if (!password.getText().toString().equals(confirm.getText().toString())) {
                                password.setError("Passwords do not match");
                                confirm.setError("Passwords do not match");
                            } else {
                                password.setError(null);
                                confirm.setError(null);
                                new DBHelper(MainActivity.this).editStudent(new Students(0,"",name.getText().toString(),password.getText().toString(),uname,"mob"));
                                new DBHelper(MainActivity.this).UpdateFLAG(1);
                                new ApplicationPrefs(MainActivity.this).removeReferences();
                                startIntent();

                            }
                        }
                        else
                        {
                            new DBHelper(MainActivity.this).editStudent(new Students(0,"",name.getText().toString(),password.getText().toString(),uname,"mob"));
                            new DBHelper(MainActivity.this).UpdateFLAG(1);
                            new ApplicationPrefs(MainActivity.this).removeReferences();
                            startIntent();
                        }
                    }
                        else
                        {
                            if(!new InputValidation(MainActivity.this).isTextEmpty(name))
                                return;
                            else if(!new InputValidation(MainActivity.this).isTextEmpty(username))
                                return;
                            else if(!new InputValidation(MainActivity.this).isTextEmpty(email))
                                    return;
                            else if(new InputValidation(MainActivity.this).isTextEmpty(password))
                            {
                                if(password.getText().length()<6)
                                {
                                    password.setError("Please enter a password of atleast 6 characters");
                                }
                                else if(!password.getText().toString().equals(confirm.getText().toString()))
                                {
                                    Log.d("PASSWORD", password.getText().toString() + confirm.getText().toString());
                                    password.setError("Passwords do not match");
                                    confirm.setError("Passwords do not match");
                                }
                                else
                                {
                                    password.setError(null);
                                    confirm.setError(null);
                                    new DBHelper(MainActivity.this).editTeacher(new Teachers(0,name.getText().toString(),username.getText().toString(),email.getText().toString(),password.getText().toString(),"mob"));
                                    new DBHelper(MainActivity.this).UpdateFLAG(1);
                                    new ApplicationPrefs(MainActivity.this).removeReferences();
                                    startIntent();

                                }
                        }
                                else
                                    {
                                        new DBHelper(MainActivity.this).editTeacher(new Teachers(0,name.getText().toString(),username.getText().toString(),email.getText().toString(),password.getText().toString(),"mob"));
                                        new DBHelper(MainActivity.this).UpdateFLAG(1);
                                        new ApplicationPrefs(MainActivity.this).removeReferences();
                                        startIntent();
                                    }


                            }
                    }


                    }


        });



        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("CHECK YOUR ACCOUNT");



        AlertDialog dialog = dialogBuilder.show();
    }
    public void startIntent()
    {
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
