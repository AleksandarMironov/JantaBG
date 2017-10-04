package com.example.ivan.jantabg;


import android.text.TextUtils;

public class Utilities {
    public static boolean checkString(String str){
        if (TextUtils.isEmpty(str) ||  str.matches("[&\\;'\"]")){ //bad simbols for database
            return false;
        }
        return true;
    }

    public static boolean isMail(String str){
        return !TextUtils.isEmpty(str) && android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }
}
