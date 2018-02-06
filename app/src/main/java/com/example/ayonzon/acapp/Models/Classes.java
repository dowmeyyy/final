package com.example.ayonzon.acapp.Models;

/**
 * Created by ayonzon on 1/26/2018.
 */

public class Classes {

    int classID;
    String classCode;
    String className;
    String day;
    String timeIN;
    String timeOUT;
    String teacherID;
    String stat;
    public Classes()
    {}

    public Classes(int classID, String classCode, String className, String day, String timeIN, String timeOUT, String teacherID,String stat )
    {
        this.classID=classID;
        this.classCode=classCode;
        this.className=className;
        this.day=day;
        this.timeIN=timeIN;
        this.timeOUT=timeOUT;
        this.teacherID=teacherID;
        this.stat=stat;
    }

    public int getClassID()
    {
        return this.classID;
    }
    public void setClassID(int id)
    {
        this.classID=id;
    }

    public String getclassCode()
    {
        return this.classCode;
    }
    public void setClassCode(String classCode )
    {
        this.classCode=classCode;
    }

    public String getClassName()
    {
        return this.className;
    }
    public void setClassName(String className )
    {
        this.className=className;
    }

    public String getDay()
    {
        return this.day;
    }
    public void setDay(String day )
    {
        this.day=day;
    }

    public String getTimeIN()
    {
        return this.timeIN;
    }
    public void setTimeIN(String timeIN )
    {
        this.timeIN=timeIN;
    }

    public String getTimeOUT()
    {
        return this.timeOUT;
    }
    public void setTimeOUT(String timeOUT )
    {
        this.timeOUT=timeOUT;
    }

    public String getTeacherID()
    {
        return this.teacherID;
    }
    public void setTeacherID(String teacherID )
    {
        this.teacherID=teacherID;
    }

    public String getStat()
    {
        return this.stat;
    }
    public void setStat(String stat )
    {
        this.stat=stat;
    }





}
