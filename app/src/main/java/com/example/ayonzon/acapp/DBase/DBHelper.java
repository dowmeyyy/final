package com.example.ayonzon.acapp.DBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.ayonzon.acapp.Models.ApplicationPrefs;
import com.example.ayonzon.acapp.Models.Attendance;
import com.example.ayonzon.acapp.Models.Classes;
import com.example.ayonzon.acapp.Models.Modules;
import com.example.ayonzon.acapp.Models.Students;
import com.example.ayonzon.acapp.Models.Teachers;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ayonzon on 1/26/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ACAPP.db";

    private static final String STAT= "stat";

    //table config for teachers
    private static final String TEACHER_ID = "teacherID";
    private static final String TEACHER_NAME = "name";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String TEACHER_PASSWORD = "teacherPass";

    //table config for students
    private static final String STUDENT_ID = "studID";
    private static final String CLASS_ID = "classID";
    private static final String STUDENT_NAME = "sName";
    private static final String STUDENT_PASSWORD = "studPass";
    private static final String STUDENT_OTHER_ID="sID";

    //table config for attendance
    private static final String ATTENDANCE_ID = "attID";
    private static final String DATE="date";
    private static final String STATUS="status";
    private static final String NOTE="note";

    //table config for class

    private static final String CLASS_NAME="className";
    private static final String CLASS_CODE="classCode";
    private static final String DAY="day";
    private static final String time="timeIN";
    private static final String time2="timeOUT";

    //table config for modules
    private static final String MODULE_ID="mID";
    private static final String FILENAME="filename";
    private static final String FILEPATH="filepath";

    //table config for flag

    private static final String FLAG="flag";
    Context c;
    ApplicationPrefs ap;


        public DBHelper(Context aContext)
        {

            super (aContext,DATABASE_NAME,null,DATABASE_VERSION);
            this.c=aContext;
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {


            // Create your tables here

            String TeachersSQL = "CREATE TABLE " + "TEACHERS" + "( " + TEACHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    TEACHER_NAME + " TEXT, "+
                    USERNAME + " TEXT, "+
                    EMAIL + " TEXT, "+
                    STAT + " TEXT, "+
                    TEACHER_PASSWORD + " TEXT )";

            String StudentSQL = "CREATE TABLE " + "STUDENTS" + "( " + STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    CLASS_ID + " TEXT, "+
                    STUDENT_NAME + " TEXT, "+
                    STUDENT_OTHER_ID + " TEXT, "+
                    STAT + " TEXT, "+
                    STUDENT_PASSWORD + " TEXT )";

            String AttendanceSQL = "CREATE TABLE " + "ATTENDANCE" + "( " + ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    STUDENT_ID + " TEXT, "+
                    DATE + " TEXT, "+
                    STAT + " TEXT, "+
                    NOTE + " TEXT, "+
                    STATUS + " TEXT, "+
                    CLASS_CODE + " TEXT )";

            String ClassSQL = "CREATE TABLE " + "CLASS" + "( " + CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    CLASS_CODE + " TEXT, "+
                    CLASS_NAME + " TEXT, "+
                    DAY + " TEXT, "+
                    time + " TEXT, "+
                    time2 + " TEXT, "+
                    STAT + " TEXT, "+
                    TEACHER_ID + " TEXT )";

            String ModuleSQL = "CREATE TABLE " + "MODULES" + "( " + MODULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    FILENAME + " TEXT, "+
                    FILEPATH + " TEXT, "+
                    CLASS_CODE + " TEXT )";

            String FlagSQL="CREATE TABLE " + "FLAG" + "( " + FLAG + " INTEGER PRIMARY KEY )";
            String insertFlag="INSERT INTO FLAG (flag) VALUES (0)";
            Log.d("DBASE", FlagSQL);
            Log.d("DBASE", AttendanceSQL);


            sqLiteDatabase.execSQL(TeachersSQL);
            sqLiteDatabase.execSQL(StudentSQL);
            sqLiteDatabase.execSQL(AttendanceSQL);
            sqLiteDatabase.execSQL(ClassSQL);
            sqLiteDatabase.execSQL(FlagSQL);
            sqLiteDatabase.execSQL(ModuleSQL);
            sqLiteDatabase.execSQL(insertFlag);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            // Database schema upgrade code goes here

            String buildSQL = "DROP TABLE IF EXISTS " + "TEACHERS";
            String buildSQL1 = "DROP TABLE IF EXISTS " + "STUDENTS";
            String buildSQL2 = "DROP TABLE IF EXISTS " + "CLASS";
            String buildSQL3 = "DROP TABLE IF EXISTS " + "FLAG";
            String buildSQL4 = "DROP TABLE IF EXISTS " + "ATTENDANCE";
            String buildSQL5 = "DROP TABLE IF EXISTS " + "MODULES";


            Log.d("DBASE", "onUpgrade SQL: " + buildSQL);

            sqLiteDatabase.execSQL(buildSQL);       // drop previous table
            sqLiteDatabase.execSQL(buildSQL1);       // drop previous table
            sqLiteDatabase.execSQL(buildSQL2);       // drop previous table
            sqLiteDatabase.execSQL(buildSQL3);       // drop previous table
            sqLiteDatabase.execSQL(buildSQL4);       // drop previous table
            sqLiteDatabase.execSQL(buildSQL5);       // drop previous table


            onCreate(sqLiteDatabase);               // create the table from the beginning
        }

        public void UpdateFLAG(int _flag)
        {
           int  _current= _flag == 1 ? 0 : 1;
            SQLiteDatabase db= this.getWritableDatabase();
            ContentValues value= new ContentValues();
            value.put(FLAG, _flag );
            db.update("FLAG", value, FLAG + "= ?", new String[] {_current + ""} );
        }

        public void InsertFLAG()
        {
            SQLiteDatabase db=this.getWritableDatabase();

            ContentValues value= new ContentValues();
            value.put(FLAG, 1);
            db.insert("FLAG",null,value);
            db.close();
        }

        public int getFlag()
        {
            int _flag = 0;
            String selectQuery = "SELECT  * FROM " + "FLAG";
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery,null);
            Log.d("FLAGCOUNT",cursor.getCount() + "");
            if(cursor.moveToFirst())
            {
                _flag=Integer.parseInt(cursor.getString(0));
                Log.d("FLAG",_flag + "");
            }
            return _flag;
        }

        public void sampleDisplay(Classes classes)
        {
            Log.d("YELLOW", classes.getclassCode());
        }

        public void addClass(Classes _class)
        {

            SQLiteDatabase db= this.getWritableDatabase();
            ContentValues values= new ContentValues();

            values.put(CLASS_CODE,_class.getclassCode());
            values.put(CLASS_NAME,_class.getClassName());
            values.put(DAY,_class.getDay());
            values.put(time,_class.getTimeIN());
            values.put(time2,_class.getTimeOUT());
            values.put(TEACHER_ID,_class.getTeacherID());
            values.put(STAT,_class.getStat());
            Log.d("HELLO","BOOM");
            db.insert("CLASS",null,values);
            db.close();
        }

        public void editClass(Classes _class)
        {

            SQLiteDatabase db= this.getWritableDatabase();
            ContentValues values= new ContentValues();
            String getStat= getStat(_class.getclassCode());
            values.put(CLASS_CODE,_class.getclassCode());
            values.put(CLASS_NAME,_class.getClassName());
            values.put(DAY,_class.getDay());
            values.put(time,_class.getTimeIN());
            values.put(time2,_class.getTimeOUT());
            values.put(TEACHER_ID,_class.getTeacherID());
            values.put(STAT,getStat.toString());
            db.update("CLASS", values,  "classCode= ?", new String[] {_class.getclassCode() + ""} );
        }
        public void deleteClass(int id)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            String getStat=getClassStat(id);
            ContentValues values=new ContentValues();
            if(getStat.equals("web") || getStat.equals("web-edit"))
            {
                values.put(STAT,"web-delete");
                db.update("CLASS",values,"classID= ?",new String[]{id+""});
            }
            else
            {
                db.delete("CLASS","classID= ?",new String[]{id+""});
            }

        }

        public String getClassStat(int id)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.query("CLASS",null,"classID=?",new String[]{id+""},null,null,null);
            cursor.moveToFirst();
            return cursor.getString(6).toString();
        }

        public String getStat(String classCode)
        {
            String stat="";
            SQLiteDatabase db=this.getWritableDatabase();
           Cursor cursor= db.query("CLASS",null,"classCode=?",new String[] {classCode},null,null,null);

            cursor.moveToFirst();
            if(cursor.getString(6).equals("web"))
            {
                stat="web-edit";
            }
            else
            {
                stat="mob";
            }

            return stat;
        }

        public void addTeachers(Teachers _teacher)
        {
            Log.d("HELLO","PASOKADD");
            SQLiteDatabase db= this.getWritableDatabase();
            ContentValues values= new ContentValues();

            values.put(TEACHER_NAME,_teacher.getTeacherName());
            values.put(USERNAME,_teacher.getUsername());
            values.put(EMAIL,_teacher.getEmail());
            values.put(TEACHER_PASSWORD,_teacher.getPass());
            values.put(STAT,_teacher.getStat());
            db.insert("TEACHERS",null,values);
            db.close();
        }

        public void addStudents(Students _students)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values= new ContentValues();

            values.put(CLASS_ID,_students.getClassID());
            values.put(STUDENT_NAME, _students.getStudName());
            values.put(STUDENT_OTHER_ID,_students.getStudentID());
            values.put(STAT,_students.getStat());
            values.put(STUDENT_PASSWORD, _students.getStudPass());

            db.insert("STUDENTS", null, values);
            db.close();
        }

        public void editStudent(Students s)
        {

            String stat="",pass="";
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values= new ContentValues();
            String getStat=getStudentStat(s.getStudentID() + "");
            String getPassword=getStudentPass(s.getStudentID() + "");
            if(getStat.equals("web"))
            {
                stat="web-edit";
            }
            else if(getStat.equals("mob"))
            {
                stat="mob";
            }

            if(getPassword.equals(s.getStudentID()))
            {
                pass=s.getStudentID();
            }
            else
            {
                pass=getPassword;
            }
            Log.d("CLASSID",s.getClassID());
            if(!(s.getClassID().equals("")))
            {
                values.put(CLASS_ID,s.getClassID());
            }
            values.put(STUDENT_NAME,s.getStudName());
            if(!(s.getStudentID().equals("")))
            {
                values.put(STUDENT_OTHER_ID,s.getStudentID());
            }
            values.put(STAT,stat);
            values.put(STUDENT_PASSWORD,pass);
            db.update("STUDENTS", values,  "sID= ?", new String[] {s.getStudentID() + ""} );

        }

        public void deleteStudent(String id)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            String getStat=getStudentStat(id);
            ContentValues values=new ContentValues();
            if(getStat.equals("web") || getStat.equals("web-edit"))
            {
                values.put(STAT,"web-delete");
                db.update("STUDENTS",values,"studID= ?",new String[]{id+""});
            }
            else
            {
                db.delete("STUDENTS","studID= ?",new String[]{id+""});
            }
        }

        public String getStudentStat(String id)
        {
            Log.d("ID",id);
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.query("STUDENTS",null,"sID=?",new String[]{id+""},null,null,null);
            cursor.moveToFirst();
            return cursor.getString(4).toString();
        }
        public String getStudentPass(String id)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.query("STUDENTS",null,"sID=?",new String[]{id+""},null,null,null);
            cursor.moveToFirst();
            return cursor.getString(5).toString();
        }


        public String getSampleClass()
        {
            String _flag= "";
            String selectQuery = "SELECT  * FROM " + "CLASS";
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery,null);

            if(cursor.moveToFirst())
            {
                _flag=cursor.getString(0);
            }
            return _flag;
        }

        public void deleteAllClass()
        {
            SQLiteDatabase db =this.getWritableDatabase();
            db.execSQL("DELETE FROM " + "CLASS");
        }

        public void deleteALlStudents()
        {
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL("DELETE FROM " + "STUDENTS");
        }

        public void deleteAllTeachers()
        {
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL("DELETE FROM " + "TEACHERS");
        }

        public List<Classes> getAllClasses()
        {
            List<Classes>classes = new ArrayList<Classes>();
            String selectQuery = "SELECT * FROM " + "CLASS";

            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery,  null);

            if (cursor.moveToFirst())
            {
                do {
                    Classes _class = new Classes();
                    _class.setClassID(Integer.parseInt(cursor.getString(0)));
                    _class.setClassCode(cursor.getString(1));
                    _class.setClassName(cursor.getString(2));
                    _class.setDay(cursor.getString(3));
                    _class.setTimeIN(cursor.getString(4));
                    _class.setTimeOUT(cursor.getString(5));
                    _class.setStat(cursor.getString(6));
                    _class.setTeacherID(cursor.getString(7));



                    classes.add(_class);

                }while(cursor.moveToNext());
            }

            return classes;
        }

        public List<Teachers> getAllTeachers()
        {
            List<Teachers>teachers = new ArrayList<Teachers>();
            String selectQuery = "SELECT * FROM " + "TEACHERS";

            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery,  null);

            if (cursor.moveToFirst())
            {
                do {

                    Teachers _teachers = new Teachers();
                    _teachers.setTeacherID(Integer.parseInt(cursor.getString(0)));
                    _teachers.setTeacherName(cursor.getString(1));
                    _teachers.setUsername(cursor.getString(2));
                    _teachers.setEmail(cursor.getString(3));
                    _teachers.setStat(cursor.getString(4));
                    _teachers.setPass(cursor.getString(5));



                    teachers.add(_teachers);

                }while(cursor.moveToNext());
            }

            return teachers;
        }

        public List<Teachers> getTeachersviaID(String id)
        {
            List<Teachers>teachers = new ArrayList<Teachers>();


            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.query("TEACHERS",null,"username=?",new String[] {id},null,null,null);

            if (cursor.moveToFirst())
            {
                do {

                    Teachers _teachers = new Teachers();
                    _teachers.setTeacherID(Integer.parseInt(cursor.getString(0)));
                    _teachers.setTeacherName(cursor.getString(1));
                    _teachers.setUsername(cursor.getString(2));
                    _teachers.setEmail(cursor.getString(3));
                    _teachers.setStat(cursor.getString(4));
                    _teachers.setPass(cursor.getString(5));



                    teachers.add(_teachers);

                }while(cursor.moveToNext());
            }

            return teachers;
        }

        public void editTeacher(Teachers t)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values=new ContentValues();
            String stat="";
            String getStat=getTeacherStat(t.getUsername());
            if(getStat.equals("web"))
            {
                stat="web-edit";
            }
            else
            {
                stat="mob";
            }
            values.put(TEACHER_NAME,t.getTeacherName());
            values.put(USERNAME,t.getUsername());
            values.put(EMAIL,t.getEmail());
            values.put(STAT,stat);
            if(!(t.getPass().equals("")))
            {
                values.put(TEACHER_PASSWORD,t.getPass());
            }



            db.update("TEACHERS",values,"username=?",new String[]{t.getUsername()});



        }
        public String getTeacherStat(String username)
        {
            String stat="";
            SQLiteDatabase db=getWritableDatabase();
            Cursor cursor= db.query("TEACHERS",null,"username=?",new String[]{username},null,null,null);
            cursor.moveToFirst();
            return cursor.getString(4);

        }

        public List<Students> getAllStudents()
        {
            List<Students>students = new ArrayList<Students>();
            String selectQuery = "SELECT * FROM " + "STUDENTS";

            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery,  null);

            if (cursor.moveToFirst())
            {
                do {

                    Students _students = new Students();
                    _students.setStudID(Integer.parseInt(cursor.getString(0)));
                    _students.setClassID(cursor.getString(1));
                    _students.setStudName(cursor.getString(2));
                    _students.setStudentID(cursor.getString(3));
                    _students.setStat(cursor.getString(4));
                    _students.setStudPass(cursor.getString(5));



                    students.add(_students);

                }while(cursor.moveToNext());
            }

            return students;

        }

        public String getStudentCount(String _class)
        {
            String count="";
            SQLiteDatabase db=getWritableDatabase();
            Cursor cursor = db.query("STUDENTS",null,"classID=?",new String[] {_class},null,null,null);
            String query="SELECT * FROM STUDENTS";
            Cursor cursor2=db.rawQuery(query,null);



            if(cursor!=null)
            {
                count=cursor.getCount() + "";
            }
            return count;
        }
        public List<Students> getStudentViaCode(String _class)
        {
            List<Students>students = new ArrayList<Students>();

            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.query("STUDENTS",null,"classID=? AND (stat=? OR stat=? OR stat=?)",new String[] {_class,"web-edit","mob","web"},null,null,null);

            if (cursor.moveToFirst())
            {
                do {

                    Students _students = new Students();
                    _students.setStudID(Integer.parseInt(cursor.getString(0)));
                    _students.setClassID(cursor.getString(1));
                    _students.setStudName(cursor.getString(2));
                    _students.setStudentID(cursor.getString(3));
                    _students.setStat(cursor.getString(4));
                    _students.setStudPass(cursor.getString(5));



                    students.add(_students);

                }while(cursor.moveToNext());
            }

            return students;
        }

    public List<Students> getStudentViaID(String id)
    {
        List<Students>students = new ArrayList<Students>();

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.query("STUDENTS",null,"studID=? AND (stat=? OR stat=? OR stat=?)",new String[] {id,"web-edit","mob","web"},null,null,null);

        if (cursor.moveToFirst())
        {
            do {

                Students _students = new Students();
                _students.setStudID(Integer.parseInt(cursor.getString(0)));
                _students.setClassID(cursor.getString(1));
                _students.setStudName(cursor.getString(2));
                _students.setStudentID(cursor.getString(3));
                _students.setStat(cursor.getString(4));
                _students.setStudPass(cursor.getString(5));



                students.add(_students);

            }while(cursor.moveToNext());
        }

        return students;
    }

    public List<Students> getStudentViaStudID(String id)
    {
        List<Students>students = new ArrayList<Students>();

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.query("STUDENTS",null,"sID=? AND (stat=? OR stat=? OR stat=?)",new String[] {id,"web-edit","mob","web"},null,null,null);

        if (cursor.moveToFirst())
        {
            do {

                Students _students = new Students();
                _students.setStudID(Integer.parseInt(cursor.getString(0)));
                _students.setClassID(cursor.getString(1));
                _students.setStudName(cursor.getString(2));
                _students.setStudentID(cursor.getString(3));
                _students.setStat(cursor.getString(4));
                _students.setStudPass(cursor.getString(5));



                students.add(_students);

            }while(cursor.moveToNext());
        }

        return students;
    }


        public int getClassCount(String teacher)
        {
            int count=0;
            SQLiteDatabase db=getWritableDatabase();
            Cursor cursor = db.query("CLASS",null,"teacherID=?",new String[] {teacher},null,null,null);
            if(cursor!=null)
            {
                count=cursor.getCount();
            }
            return count;

        }
    public List<Classes> getAllClassesViaCode(String teacher)
    {
        List<Classes>classes = new ArrayList<Classes>();

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.query("CLASS",null,"teacherID=? AND (stat=? OR stat=? OR stat=?)",new String[]{teacher,"mob","web-edit","web"},null,null,null);

        if (cursor.moveToFirst())
        {
            do {
                Classes _class = new Classes();
                _class.setClassID(Integer.parseInt(cursor.getString(0)));
                _class.setClassCode(cursor.getString(1));
                _class.setClassName(cursor.getString(2));
                _class.setDay(cursor.getString(3));
                _class.setTimeIN(cursor.getString(4));
                _class.setTimeOUT(cursor.getString(5));
                _class.setStat(cursor.getString(6));
                _class.setTeacherID(cursor.getString(7));



                classes.add(_class);

            }while(cursor.moveToNext());
        }

        return classes;
    }

        public String login(String email, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            ap=new ApplicationPrefs(c.getApplicationContext());
            String statement="";
            SQLiteDatabase db=getWritableDatabase();

            Log.d("USERNAME",email);
            Cursor cursor = db.query("TEACHERS", null, "email=?", new String[] { email }, null, null, null);
            Cursor cursor2 = db.query("STUDENTS", null , "sid=?" , new String[] {email}, null, null,null);

            Log.d("COUNT", cursor.getCount() + "");
            Log.d("COUNT2",cursor2.getCount()+"");
            if (cursor.getCount() <= 0) {
                cursor.close();


                if(cursor2.getCount()<=0)
                {
                    cursor2.close();
                    statement= "Incorrect Email/Password";
                }
                else
                {
                    cursor2.moveToFirst();

                    String pass= cursor2.getString(5);
                    if(password.equals(pass) )
                    {
                        new ApplicationPrefs(c).setAccount(cursor2.getString(2), "STUDENT",cursor2.getString(3));
                        new ApplicationPrefs(c).setClass(cursor2.getString(1));
                        statement= "Successfully logged in";
                    }
                    else
                    {
                        statement= "Incorrect Password";
                    }

                }
            }
            else
            {


                    cursor.moveToFirst();
                    String pass = cursor.getString(5);
                    String md5=md5(password);
                Log.d("PASS",md5);
                    if (md5.equals(pass) ) {
                        Log.d("CONTEXT", cursor.getString(1) + cursor.getString(2));
                         ap.setTeacher(cursor.getString(2), cursor.getString(1));
                         ap.setAccount(cursor.getString(1), "TEACHER", cursor.getString(2));

                        statement= "Sucessfully logged in";
                    } else {
                        statement= "Incorrect Email/Password";
                    }





                }



                cursor.close();
                db.close();
            return statement;

            }

            public List<Classes> getClassViaCode(String code)
            {
                Log.d("CODE",code);
                List<Classes>classes = new ArrayList<Classes>();
                SQLiteDatabase db=getWritableDatabase();
                Cursor cursor= db.query("CLASS",null,"classCode=?",new String[] {code},null,null,null);
                Log.d("CLASS COUNT EDIT",cursor.getCount() + "");
                cursor.moveToFirst();
                Classes _class = new Classes();
                _class.setClassID(Integer.parseInt(cursor.getString(0)));
                _class.setClassCode(cursor.getString(1));
                _class.setClassName(cursor.getString(2));
                _class.setDay(cursor.getString(3));
                _class.setTimeIN(cursor.getString(4));
                _class.setTimeOUT(cursor.getString(5));
                _class.setStat(cursor.getString(6));
                _class.setTeacherID(cursor.getString(7));



                classes.add(_class);

                return classes;

            }

            public String getAttendance(String id)
            {

                String status="";
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                SQLiteDatabase db=getWritableDatabase();
                Cursor cursor= db.query("ATTENDANCE",null,"studID=? AND date=? ",new String[] {id,date},null,null,null);
                if(cursor!=null)
                {
                    if(cursor.getCount()>0)
                    {

                        cursor.moveToFirst();
                        status=cursor.getString(5);

                    }

                }
                return  status;
            }

            public List<Attendance> getAttendanceviaID(String id)
            {
                List<Attendance>attendance = new ArrayList<Attendance>();
                SQLiteDatabase db=getWritableDatabase();
                Cursor cursor= db.query("ATTENDANCE",null,"studID=?",new String[] {id},null,null,null);
                if(cursor.getCount()>0)
                {
                    cursor.moveToFirst();
                    Attendance a = new Attendance();
                    a.setAttID(Integer.parseInt(cursor.getString(0)));
                    a.setStudID(cursor.getString(1));
                    a.setDate(cursor.getString(2));
                    a.setStat(cursor.getString(3));
                    a.setNote(cursor.getString(4));
                    a.setStatus(cursor.getString(5));
                    a.setClasscode(cursor.getString(6));




                    attendance.add(a);
                }



                return attendance;
            }

    public List<Attendance> getAttendanceviaClass(String id)
    {
        Log.d("ATT",id);
        List<Attendance>attendance = new ArrayList<Attendance>();
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor= db.query("ATTENDANCE",null,"classCode=?",new String[] {id},null,null,null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            Attendance a = new Attendance();
            a.setAttID(Integer.parseInt(cursor.getString(0)));
            a.setStudID(cursor.getString(1));
            a.setDate(cursor.getString(2));
            a.setStat(cursor.getString(3));
            a.setNote(cursor.getString(4));
            a.setStatus(cursor.getString(5));
            a.setClasscode(cursor.getString(6));




            attendance.add(a);
        }


        return attendance;
    }
    public List<Attendance> getAttendanceviaDate(String date,String classCode)
    {
        List<Attendance>attendance = new ArrayList<Attendance>();
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor= db.query("ATTENDANCE",null,"classCode=? AND date=?",new String[] {classCode,date},null,null,null);
        cursor.moveToFirst();
        Attendance a = new Attendance();
        a.setAttID(Integer.parseInt(cursor.getString(0)));
        a.setStudID(cursor.getString(1));
        a.setDate(cursor.getString(2));
        a.setStat(cursor.getString(3));
        a.setNote(cursor.getString(4));
        a.setStatus(cursor.getString(5));
        a.setClasscode(cursor.getString(6));




        attendance.add(a);

        return attendance;
    }

            public List<Attendance> getAllAttendance()
            {
                ArrayList<Attendance> att=new ArrayList<Attendance>();
                String query = "SELECT * FROM ATTENDANCE";
                SQLiteDatabase db=getWritableDatabase();
                Cursor cursor = db.rawQuery(query,null);

                if(cursor.moveToFirst())
                {
                    do {
                        Attendance _att = new Attendance();
                        _att.setAttID(Integer.parseInt(cursor.getString(0)));
                        _att.setStudID(cursor.getString(1));
                        _att.setDate(cursor.getString(2));
                        _att.setStat(cursor.getString(3));
                        _att.setStatus(cursor.getString(5));
                        _att.setNote(cursor.getString(4));
                        _att.setClasscode(cursor.getString(6));

                        att.add(_att);

                    }while (cursor.moveToNext());
                }

                return att;
            }

            public String getAttendanceSample()
            {
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                SQLiteDatabase db=getWritableDatabase();
                String query="SELECT * FROM ATTENDANCE";
                Cursor cursor= db.rawQuery(query,null);
                String status="";

                if(cursor.moveToFirst())
                {
                    status=cursor.getString(5);
                }

                return status;
            }

    public void addAttendance(Attendance att)
    {

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        values.put(STUDENT_ID,att.getStudID());
        values.put(DATE,date);
        values.put(STAT,att.getStat());
        values.put(NOTE,att.getNote());
        Log.d("STATUS",att.getStatus());
        values.put(STATUS,att.getStatus());
        values.put(CLASS_CODE,att.getClasscode());




        db.insert("ATTENDANCE",null,values);
        db.close();
    }

    public void deleteAllModules()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "MODULES");
    }
    public void addModules(Modules mod)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(FILENAME,mod.getFilename());
        values.put(FILEPATH,mod.getFilepath());
        values.put(CLASS_CODE,mod.getClasscode());

        db.insert("MODULES",null,values);
        db.close();
    }

    public List<Modules> getAllModules(String id)
    {
        List<Modules>module = new ArrayList<Modules>();


        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.query("MODULES",null,"classCode=?",new String[] {id},null,null,null);



            if (cursor.moveToFirst())
            {
                do {

                    Modules m = new Modules();
                    m.setmID(Integer.parseInt(cursor.getString(0)));
                    m.setFilename(cursor.getString(1));
                    m.setFilepath(cursor.getString(2));
                    m.setClasscode(cursor.getString(3));




                    module.add(m);

                }while(cursor.moveToNext());

        }


        return module;
    }

    public static String md5(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;





        }
    }






