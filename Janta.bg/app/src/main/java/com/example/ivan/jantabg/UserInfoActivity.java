package com.example.ivan.jantabg;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    private String userMail;
    DataBaseHelper db;
    TextView userName, email, city, address, gender, phone, ltd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_info);
        userMail = getIntent().getExtras().getString("userMail");
        db = DataBaseHelper.getHelper(this);
        setInformation();
    }

    public void setInformation() {
        Cursor cursor = db.getUserData(userMail);
        cursor.moveToFirst();

        userName = (TextView) findViewById(R.id.username_info);
        String userNameInfo = cursor.getString(2);
        userName.setText(userName.getText() + userNameInfo);

        email = (TextView) findViewById(R.id.email_info);
        String emailInfo = cursor.getString(0);
        email.setText(email.getText() + emailInfo);

        city = (TextView) findViewById(R.id.city_info);
        String cityInfo = cursor.getString(3);
        city.setText(city.getText() + cityInfo);

        gender = (TextView) findViewById(R.id.gender_info);
        String genderInfo = cursor.getString(4);
        gender.setText(gender.getText() + genderInfo);

        phone = (TextView) findViewById(R.id.phone_info);
        String phoneInfo = cursor.getString(5);
        phone.setText(phone.getText() + phoneInfo);

        ltd = (TextView) findViewById(R.id.is_ltd_info);
        String ltdInfo = cursor.getString(6);
        ltd.setText(ltd.getText() + ltdInfo);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
