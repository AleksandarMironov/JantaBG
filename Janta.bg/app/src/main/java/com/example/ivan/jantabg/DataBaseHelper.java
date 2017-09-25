package com.example.ivan.jantabg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static DataBaseHelper instance;
    public static final String DATABASE_NAME = "JantaBase.db";

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static synchronized DataBaseHelper getHelper (Context context){
        if(instance == null){
            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean checkPassword(String mail, String password){

        return true;
    }
}
