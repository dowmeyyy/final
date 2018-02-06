package com.example.ayonzon.acapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Helpers.TransferHelper;
import com.example.ayonzon.acapp.R;

/**
 * Created by ayonzon on 1/26/2018.
 */

public class Launcher extends Activity {

    private Launcher launcher;
    ProgressBar prog;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        launcher=this;

        runUpProcess();
    }

    public void runUpProcess() {
        DBHelper db = new DBHelper(this);
        int _flag = db.getFlag();


//        db.addClass(new Classes(0,"SS3","Programming","Monday","13:00","16:00","1","mob"));
//            db.UpdateFLAG(1);
        if(isConnectedToInternet())
        {
            Log.d("PUMASOK",_flag + "");
            if(_flag == 1)
            {
                new TransferHelper(this).TransferClasses();
                new TransferHelper(this).TransferTeachers();
                new TransferHelper(this).TransferStudents();
                new TransferHelper(this).TransferAttendance();
                db.UpdateFLAG(0);
                runUpProcess();
            }
            else
            {
                new TransferHelper(this).getClasses();
                new TransferHelper(this).getTeachers();
                new TransferHelper(this).getStudents();
                new TransferHelper(this).getModules();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        StartIntent();
                    }
                }, 5000);





            }
        }



    }

    public void StartIntent()
    {
        Intent intent = new Intent(launcher, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }


}
