package com.example.ayonzon.acapp.Models;

import android.content.Context;

import com.example.ayonzon.acapp.Utils.PreferenceUtil;

/**
 * Created by ayonzon on 1/31/2018.
 */

public class ApplicationPrefs {

    private PreferenceUtil accountPref;
    private Context c;



    public ApplicationPrefs ()
    {

    }
    public ApplicationPrefs(Context c)
    {
        accountPref = new PreferenceUtil(c,PreferenceUtil.PreferenceKeys.accountPref);
        this.c=c;
    }

    public void setTeacher(String username, String name)
    {
        accountPref.SetStringPreference("Teacher", username + "|" + name);
    }
    public String getTeacher()
    {
        return accountPref.GetStringPreference("Teacher",null);
    }

    public void setAccount(String username,String type, String id)
    {
        accountPref.SetStringPreference("Account", id + "|" + username + "|" + type);
    }
    public String getAccount()
    {
        return accountPref.GetStringPreference("Account",null);
    }

    public void setClass(String classcode)
    {
        accountPref.SetStringPreference("Class",classcode);

    }
    public String getClassz()
    {
        return accountPref.GetStringPreference("Class",null);
    }

    public void removeReferences()
    {
        accountPref.RemoveReference();
    }


}
