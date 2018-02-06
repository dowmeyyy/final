package com.example.ayonzon.acapp.Models;

import android.widget.Spinner;

/**
 * Created by ayonzon on 2/2/2018.
 */

public class Modules {
    String filename;
    String filepath;
    String classcode;
    int mID;
    public Modules()
    {}

    public Modules(int mID,String filename,String filepath,String classcode)
    {
        this.filename=filename;
        this.mID=mID;
        this.filepath=filepath;
        this.classcode=classcode;
    }

    public void setmID(int mID)
    {
        this.mID=mID;
    }
    public int getmID()
    {
        return this.mID;
    }

    public void setFilename(String filename)
    {
        this.filename=filename;
    }
    public String getFilename()
    {
        return this.filename;
    }

    public void setFilepath(String filepath)
    {
        this.filepath=filepath;
    }
    public String getFilepath()
    {
        return this.filepath;
    }

    public void setClasscode(String classcode)
    {
        this.classcode=classcode;
    }
    public String getClasscode()
    {
        return this.classcode;
    }


}
