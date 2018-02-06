package com.example.ayonzon.acapp.Helpers;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by ayonzon on 1/26/2018.
 */

public class InputValidation {
    private Context context;

    public InputValidation (Context context)
    {
        this.context=context;
    }

    public boolean isEmailValid(EditText email, String message)
    {
        String value = email.getText().toString().trim();
        if(value.isEmpty())
        {
            email.setError("Please enter an Email ");
            return false;
        }
        else
        {
            if (!Patterns.EMAIL_ADDRESS.matcher(value).matches())
            {
                email.setError(message);
                hideKeyboardFrom(email);

            }
            else
            {
                email.setError(null);
                return true;
            }
        }
        return true;

    }

    public boolean isTextEmpty(EditText edit)
    {
        String value=edit.getText().toString().trim();

        if (value.isEmpty())
        {
            edit.setError("Please enter a value here");
            return false;
        }
        else
        {
            edit.setError(null);
            hideKeyboardFrom(edit);
            return true;
        }
    }

    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
