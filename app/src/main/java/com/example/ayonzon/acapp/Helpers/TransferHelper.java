package com.example.ayonzon.acapp.Helpers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.ayonzon.acapp.Activities.Launcher;
import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Models.Attendance;
import com.example.ayonzon.acapp.Models.Classes;
import com.example.ayonzon.acapp.Models.Students;
import com.example.ayonzon.acapp.Models.Teachers;
import com.example.ayonzon.acapp.Utils.PostUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ayonzon on 1/26/2018.
 */

public class TransferHelper {
    Context c;
    public TransferHelper(Context c)
    {
        this.c=c;
    }

    public void TransferClasses()
    {
        List<Classes> classes = new DBHelper(c).getAllClasses();

        for (Classes cl : classes)
        {
            JSONObject postData = new JSONObject();
            try {
                Log.d("HELLO",cl.getTeacherID());
                postData.put("classCode",cl.getclassCode());
                postData.put("className",cl.getClassName());
                postData.put("day",cl.getDay());
                postData.put("timeIN",cl.getTimeIN());
                postData.put("timeOUT",cl.getTimeOUT());
                postData.put("teacherID",cl.getTeacherID());
                postData.put("stat",cl.getStat());

                new PostUtil(c).SyncClass(postData.toString());

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void getClasses()
    {

        new DBHelper(c).deleteAllClass();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new PostUtil(c).getClasses();
            }
        }, 2000);

    }

    public void TransferTeachers()
    {
        List<Teachers> teachers = new DBHelper(c).getAllTeachers();

        for (Teachers t : teachers)
        {
            JSONObject postData = new JSONObject();
            try {
                Log.d("HELLO",t.getTeacherID() + "");
                postData.put("name",t.getTeacherName());
                postData.put("username",t.getUsername());
                postData.put("email",t.getEmail());
                postData.put("password",t.getPass());
                postData.put("stat",t.getStat());


                new PostUtil(c).SyncTeachers(postData.toString());

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void getTeachers()
    {
        new DBHelper(c).deleteAllTeachers();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new PostUtil(c).getTeachers();
            }
        }, 2000);


    }

    public void TransferStudents()
    {
        List<Students> students = new DBHelper(c).getAllStudents();
        for (Students s : students)
        {
            JSONObject postData = new JSONObject();
            try {
                postData.put("classid",s.getClassID());
                postData.put("sname",s.getStudName());
                postData.put("stat",s.getStat());
                postData.put("sID",s.getStudentID());
                postData.put("spass",s.getStudPass());



                new PostUtil(c).SyncStudents(postData.toString());

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void getStudents()
    {
        new DBHelper(c).deleteALlStudents();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new PostUtil(c).getStudents();
            }
        }, 2000);

    }

    public void getModules()
    {
        new DBHelper(c).deleteAllModules();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new PostUtil(c).getModules();
            }
        }, 2000);

    }

    public void TransferAttendance()
    {
        List<Attendance> attendance = new DBHelper(c).getAllAttendance();
        for (Attendance att : attendance)
        {
            JSONObject postData = new JSONObject();
            try {
                Log.d("STATUS",att.getStatus());
                postData.put("id",att.getAttID());
                postData.put("sID",att.getStudID());
                postData.put("date",att.getDate());
                postData.put("stat",att.getStat());
                postData.put("status",att.getStatus());
                postData.put("note",att.getNote());
                postData.put("classCode",att.getClasscode());




                new PostUtil(c).SyncAttendance(postData.toString());

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

}
