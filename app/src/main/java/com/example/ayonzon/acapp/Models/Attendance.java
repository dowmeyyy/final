package com.example.ayonzon.acapp.Models;

/**
 * Created by ayonzon on 1/26/2018.
 */

public class Attendance {
    int AttID;
    String studID;
    String date;
    String status;
    String stat;
    String note;
    String classcode;

    public Attendance()
    {

    }

    public Attendance(int attID ,String studID,String date, String status, String stat,String note,String classcode)
    {
        this.AttID=attID;
        this.studID=studID;
        this.date=date;
        this.status=status;
        this.stat=stat;
        this.note=note;
        this.classcode=classcode;
    }

    public int getAttID()
    {
        return this.AttID;
    }
    public void setAttID(int attID)
    {
        this.AttID=attID;
    }

    public String getStudID()
    {
        return this.studID;
    }
    public void setStudID(String studID)
    {
        this.studID=studID;
    }

    public String getDate()
    {
        return this.date;
    }
    public void setDate(String date)
    {
        this.date=date;
    }

    public String getStatus()
    {
        return this.status;
    }
    public void setStatus(String status)
    {
        this.status=status;
    }

    public String getStat()
    {
        return this.stat;
    }
    public void setStat(String stat)
    {
        this.stat=stat;
    }

    public String getNote()
    {
        return this.note;
    }
    public void setNote(String note)
    {
        this.note=note;
    }

    public String getClasscode(){return this.classcode;}
    public void setClasscode(String classcode){this.classcode=classcode;}



}
