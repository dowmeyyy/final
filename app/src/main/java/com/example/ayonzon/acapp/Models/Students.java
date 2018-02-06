package com.example.ayonzon.acapp.Models;

/**
 * Created by ayonzon on 1/26/2018.
 */

public class Students {

    int studID;
    String classID;
    String studName;
    String studPass;
    String stat;
    String studentID;

    public Students()
    {

    }

    public Students(int studID,String classID, String studName, String studPass,String studentID,String stat)
    {
        this.studID=studID;
        this.classID=classID;
        this.studName=studName;
        this.studPass=studPass;
        this.studentID=studentID;
        this.stat=stat;

    }

    public int getStudID()
    {
        return this.studID;
    }
    public void setStudID(int studID)
    {
        this.studID=studID;
    }

    public String getClassID()
    {
        return this.classID;
    }
    public void setClassID(String classID)
    {
        this.classID=classID;
    }

    public String getStudName()
    {
        return studName;
    }
    public void setStudName(String studName)
    {
        this.studName=studName;
    }

    public String getStudPass()
    {
        return studPass;
    }
    public void setStudPass(String studPass)
    {
        this.studPass=studPass;
    }

    public String getStat()
    {
        return stat;
    }
    public void setStat(String stat)
    {
        this.stat=stat;
    }

    public String getStudentID()
    {
        return studentID;
    }
    public void setStudentID(String studentID)
    {
        this.studentID=studentID;
    }




}
