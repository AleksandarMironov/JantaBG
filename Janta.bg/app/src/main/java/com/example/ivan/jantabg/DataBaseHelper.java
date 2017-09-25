package com.example.ivan.jantabg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static DataBaseHelper instance;

    private static final String DATABASE_NAME = "JantaBase.db";
    private static int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "user_table";
    private static final String TABLE_OFFER = "offer_table";

    private static final String T_USERS_COL_1 = "email";
    private static final String T_USERS_COL_2 = "password";
    private static final String T_USERS_COL_3 = "user";
    private static final String T_USERS_COL_4 = "city";
    private static final String T_USERS_COL_5 = "gender";
    private static final String T_USERS_COL_6 = "phone";
    private static final String T_USERS_COL_7 = "ltd";

    private static final String T_OFFER_COL_1 = "ID";
    private static final String T_OFFER_COL_2 = "title";
    private static final String T_OFFER_COL_3 = "image";
    private static final String T_OFFER_COL_4 = "description";
    private static final String T_OFFER_COL_5 = "price";
    private static final String T_OFFER_COL_6 = "datetime";
    private static final String T_OFFER_COL_7 = "email";

    private static final String USERS_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS +
            " (" + T_USERS_COL_1 + " TEXT PRIMARY KEY, " +
            T_USERS_COL_2 + " TEXT, " +
            T_USERS_COL_3 + " TEXT, " +
            T_USERS_COL_4 + " TEXT, " +
            T_USERS_COL_5 + " TEXT, " +
            T_USERS_COL_6 + " TEXT, " +
            T_USERS_COL_7 + " TEXT)";

    private static final String OFFER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_OFFER +
            " (" + T_OFFER_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            T_OFFER_COL_2 + " TEXT, " +
            T_OFFER_COL_3 + " TEXT, " +
            T_OFFER_COL_4 + " TEXT, " +
            T_OFFER_COL_5 + " REAL, " +
            T_OFFER_COL_6 + " NUMERIC, " +
            T_OFFER_COL_7 + " TEXT ," +
            " FOREIGN KEY (" + T_OFFER_COL_7 + ") REFERENCES " + TABLE_USERS + " (" + T_USERS_COL_1 + "))";

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DataBaseHelper getHelper (Context context){
        if(instance == null){
            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(OFFER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    public boolean checkPassword(String mail, String password){
        //TODO
        return true;
    }

    public boolean addNewUser(String email, String password, String name, String city, String gender, String phone, String ltd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_OFFER_COL_1, email);
        contentValues.put(T_OFFER_COL_2, password);
        contentValues.put(T_OFFER_COL_3, name);
        contentValues.put(T_OFFER_COL_4, city);
        contentValues.put(T_OFFER_COL_5, gender);
        contentValues.put(T_OFFER_COL_6, phone);
        contentValues.put(T_OFFER_COL_7, ltd);

        long b = db.insert(TABLE_USERS, null, contentValues);

        if(b == -1){
            return false;
        }
        return true;
    }

    public Cursor getUserData(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String myRawQuery = ""; //TODO
        Cursor res = db.rawQuery(myRawQuery, null);
        return res;
    }

    public boolean addNewOffer(){
        //TODO
        return true;
    }

    public Cursor getOfferData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String myRawQuery = ""; //TODO
        Cursor res = db.rawQuery(myRawQuery, null);
        return res;
    }
}
