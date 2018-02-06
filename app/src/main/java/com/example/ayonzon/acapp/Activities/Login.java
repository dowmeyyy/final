package com.example.ayonzon.acapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ayonzon.acapp.DBase.DBHelper;
import com.example.ayonzon.acapp.Helpers.InputValidation;
import com.example.ayonzon.acapp.Models.ApplicationPrefs;
import com.example.ayonzon.acapp.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ayonzon on 1/26/2018.
 */

public class Login extends Activity {
    Context c;
    private Login _login;
    public Login()
    {
        c=this;
    }

    EditText email,pass;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);

        _login=this;

        String logged_in=new ApplicationPrefs(this).getAccount();
    if(logged_in!=null)
    {
        if(!logged_in.isEmpty())
        {
            Intent intent = new Intent(_login, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!new InputValidation(c).isTextEmpty(email))
                {
                    return;
                }
                if (!new InputValidation(c).isTextEmpty(pass))
                {
                    return;
                }
                else
                {
                    String _email= email.getText().toString();
                    String _pass= pass.getText().toString();

                    String _login1 = null;

                    try {
                        _login1 = new DBHelper(c).login(_email, _pass);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }


                    Log.d("LOGIN", _login1);

                    if (_login1.contains("Successfully logged in"))
                    {
                        Log.d("LOGIN", "boom");
                        Intent intent = new Intent(_login, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }




                }




            }
        });

    }


}
