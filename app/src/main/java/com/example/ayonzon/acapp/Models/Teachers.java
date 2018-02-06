package com.example.ayonzon.acapp.Models;

/**
 * Created by ayonzon on 1/26/2018.
 */

public class Teachers {

    int teacherID;
    String teacherName;
    String username;
    String email;
    String pass;
    String stat;
    public Teachers()
    {}
    public Teachers(int teacherID, String teacherName, String username, String email, String pass, String stat)
    {
        this.teacherID=teacherID;
        this.teacherName=teacherName;
        this.username=username;
        this.email=email;
        this.pass=pass;
        this.stat=stat;
    }

    public int getTeacherID()
    {
        return this.teacherID;
    }
    public void setTeacherID(int teacherID)
    {
        this.teacherID=teacherID;
    }

    public String getTeacherName()
    {
        return this.teacherName;
    }
    public void setTeacherName(String teacherName)
    {
        this.teacherName=teacherName;
    }

    public String getUsername()
    {
        return this.username;
    }
    public void setUsername(String username)
    {
        this.username=username;
    }

    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }

    public String getPass()
    {
        return this.pass;
    }
    public void setPass(String pass)
    {
        this.pass=pass;
    }

    public String getStat(){return this.stat;}
    public void setStat(String stat){this.stat=stat;}




}
