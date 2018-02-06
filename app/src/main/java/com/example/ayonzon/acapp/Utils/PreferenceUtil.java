package com.example.ayonzon.acapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ayonzon on 1/31/2018.
 */

public class PreferenceUtil {

    public enum PreferenceKeys{
        accountPref
    }

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    public PreferenceUtil(Context c, PreferenceKeys key)
    {
        prefs= c.getSharedPreferences(key.name(), Context.MODE_PRIVATE);
    }

    public void SetStringPreference(String key,String value){
        editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String GetStringPreference(String key,String defvalue){
        return prefs.getString(key, defvalue);
    }

    public void SetBooleanPreference(String key,Boolean value){
        editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean GetBooleanPreference(String key,Boolean defvalue){
        return prefs.getBoolean(key, defvalue);
    }

    public void SetIntegerPreference(String key,Integer value){
        editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public Integer GetIntegerPreference(String key,Integer defvalue){
        return prefs.getInt(key, defvalue);
    }

    public void RemoveReference()
    {
        editor=prefs.edit();
        editor.clear().commit();
    }


}
