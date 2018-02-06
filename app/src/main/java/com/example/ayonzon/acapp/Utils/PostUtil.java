package com.example.ayonzon.acapp.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.ayonzon.acapp.Activities.Launcher;
import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Models.Classes;
import com.example.ayonzon.acapp.Models.Modules;
import com.example.ayonzon.acapp.Models.Students;
import com.example.ayonzon.acapp.Models.Teachers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ayonzon on 1/26/2018.
 */

public class PostUtil {
    String res= "";
    Context c;

    public PostUtil(Context c)
    {
        this.c=c;
    }
    public void SyncClass(String cl)
    {
        Log.d("PUMASOKZ",res);
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String data = "";

                HttpURLConnection httpURLConnection = null;
                try {

                    httpURLConnection = (HttpURLConnection) new URL(Config.SyncClassURL).openConnection();
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.writeBytes("PostData=" + params[0]);
                    wr.flush();
                    wr.close();

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(in);

                    int inputStreamData = inputStreamReader.read();
                    while (inputStreamData != -1) {
                        char current = (char) inputStreamData;
                        inputStreamData = inputStreamReader.read();
                        data += current;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }

                return data;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("result" , result);
                res=result;



            }
        }.execute(cl);

    }

    public void getClasses()
    {
        Log.d("PUMASOKZ",res);
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String data = "";

                HttpURLConnection httpURLConnection = null;
                try {

                    httpURLConnection = (HttpURLConnection) new URL(Config.getClassURL).openConnection();
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.flush();
                    wr.close();

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(in);

                    int inputStreamData = inputStreamReader.read();
                    while (inputStreamData != -1) {
                        char current = (char) inputStreamData;
                        inputStreamData = inputStreamReader.read();
                        data += current;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }

                return data;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    Log.d("result",result);
                    JSONArray contents =  new JSONArray(result);
                    for(int i=0;i < contents.length();i++)
                    {
                        JSONObject jsonObject = contents.getJSONObject(i);
                        String classCode = jsonObject.getString("classcode");
                        String classdes=jsonObject.getString("classdes");
                        String day = jsonObject.getString("day");
                        String time=jsonObject.getString("time");
                        String time2=jsonObject.getString("time2");
                        String createdby=jsonObject.getString("createdby");
                        String stat="web";

                        DBHelper db = new DBHelper(c);
                        db.addClass(new Classes(0,classCode,classdes,day,time,time2,createdby,stat));


                    }




                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }



            }
        }.execute();
    }

    public void SyncTeachers(String te)
    {
        Log.d("PUMASOKZ",res);
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String data = "";

                HttpURLConnection httpURLConnection = null;
                try {

                    httpURLConnection = (HttpURLConnection) new URL(Config.SyncTeacherURL).openConnection();
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.writeBytes("PostData=" + params[0]);
                    wr.flush();
                    wr.close();

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(in);

                    int inputStreamData = inputStreamReader.read();
                    while (inputStreamData != -1) {
                        char current = (char) inputStreamData;
                        inputStreamData = inputStreamReader.read();
                        data += current;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }

                return data;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("result" , result);
                res=result;



            }
        }.execute(te);

    }

    public void getTeachers()
    {
        Log.d("PUMASOKZ",res);
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String data = "";

                HttpURLConnection httpURLConnection = null;
                try {

                    httpURLConnection = (HttpURLConnection) new URL(Config.getTeachersURL).openConnection();
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.flush();
                    wr.close();

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(in);

                    int inputStreamData = inputStreamReader.read();
                    while (inputStreamData != -1) {
                        char current = (char) inputStreamData;
                        inputStreamData = inputStreamReader.read();
                        data += current;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }

                return data;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    Log.d("result",result);
                    JSONArray contents =  new JSONArray(result);
                    for(int i=0;i < contents.length();i++)
                    {
                        JSONObject jsonObject = contents.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String username=jsonObject.getString("username");
                        String email = jsonObject.getString("email");
                        String password=jsonObject.getString("password");
                        String type=jsonObject.getString("type");
                        String stat="web";

                        DBHelper db = new DBHelper(c);
                        db.addTeachers(new Teachers(0,name,username,email,password,stat));


                    }




                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }



            }
        }.execute();
    }

    public void SyncStudents(String cl)
    {
        Log.d("PUMASOKZ",res);
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String data = "";

                HttpURLConnection httpURLConnection = null;
                try {

                    httpURLConnection = (HttpURLConnection) new URL(Config.SyncStudentsURL).openConnection();
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.writeBytes("PostData=" + params[0]);
                    wr.flush();
                    wr.close();

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(in);

                    int inputStreamData = inputStreamReader.read();
                    while (inputStreamData != -1) {
                        char current = (char) inputStreamData;
                        inputStreamData = inputStreamReader.read();
                        data += current;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }

                return data;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("result" , result);
                res=result;



            }
        }.execute(cl);

    }

    public void getStudents()
    {
        Log.d("PUMASOKZ",res);
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String data = "";

                HttpURLConnection httpURLConnection = null;
                try {

                    httpURLConnection = (HttpURLConnection) new URL(Config.getStudentsURL).openConnection();
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.flush();
                    wr.close();

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(in);

                    int inputStreamData = inputStreamReader.read();
                    while (inputStreamData != -1) {
                        char current = (char) inputStreamData;
                        inputStreamData = inputStreamReader.read();
                        data += current;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }

                return data;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    Log.d("result",result);
                    JSONArray contents =  new JSONArray(result);
                    for(int i=0;i < contents.length();i++)
                    {
                        JSONObject jsonObject = contents.getJSONObject(i);
                        String classid = jsonObject.getString("classid");
                        String sname=jsonObject.getString("sname");
                        String sID=jsonObject.getString("sID");
                        String spass=jsonObject.getString("spass");
                        String stat="web";

                        DBHelper db = new DBHelper(c);
                        db.addStudents(new Students(0,classid,sname,spass,sID,stat));




                    }




                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }



            }
        }.execute();
    }

    public void getModules()
    {
        Log.d("PUMASOKZ",res);
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String data = "";

                HttpURLConnection httpURLConnection = null;
                try {

                    httpURLConnection = (HttpURLConnection) new URL(Config.getModulesURL).openConnection();
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.flush();
                    wr.close();

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(in);

                    int inputStreamData = inputStreamReader.read();
                    while (inputStreamData != -1) {
                        char current = (char) inputStreamData;
                        inputStreamData = inputStreamReader.read();
                        data += current;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }

                return data;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    Log.d("result",result);
                    JSONArray contents =  new JSONArray(result);
                    for(int i=0;i < contents.length();i++)
                    {
                        JSONObject jsonObject = contents.getJSONObject(i);
                        String mID = jsonObject.getString("mID");
                        String filename=jsonObject.getString("filedesc");
                        String filepath=jsonObject.getString("filepath");
                        String classcode=jsonObject.getString("classcode");

                        DBHelper db = new DBHelper(c);
                        db.addModules(new Modules(0,filename,filepath,classcode));




                    }




                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }



            }
        }.execute();
    }

    public void SyncAttendance(String cl)
    {
        Log.d("PUMASOKZ","HELLOOOO");
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String data = "";

                HttpURLConnection httpURLConnection = null;
                try {

                    httpURLConnection = (HttpURLConnection) new URL(Config.SyncAttendanceURL).openConnection();
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.writeBytes("PostData=" + params[0]);
                    wr.flush();
                    wr.close();

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(in);

                    int inputStreamData = inputStreamReader.read();
                    while (inputStreamData != -1) {
                        char current = (char) inputStreamData;
                        inputStreamData = inputStreamReader.read();
                        data += current;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }

                return data;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("result" , result);
                res=result;



            }
        }.execute(cl);

    }
}
