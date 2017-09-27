package com.example.ivan.jantabg;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    private String userMail;
    DataBaseHelper db;
    TextView testText; ///TODO this is for test, must add more textViews

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        userMail = getIntent().getExtras().getString("userMail");
        db = DataBaseHelper.getHelper(this);
        testText = (TextView) findViewById(R.id.textView);
        Cursor cursor = db.getUserData(userMail);
        cursor.moveToFirst();
        String uresName = cursor.getString(2);
        String email = cursor.getString(0);
        String phone = cursor.getString(5);
        String city = cursor.getString(3);
        String gender = cursor.getString(4);
        String ltd = cursor.getString(6);
        testText.setText("Name: " + uresName +
                "\nMail: " + email +
                "\nPhone: " + phone +
                "\nCity: " + city +
                "\nGender: " + gender +
                "\nLTD: " + ltd);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
