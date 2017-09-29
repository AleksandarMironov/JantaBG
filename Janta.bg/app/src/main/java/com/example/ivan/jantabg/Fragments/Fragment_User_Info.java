package com.example.ivan.jantabg.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.jantabg.DataBaseHelper;
import com.example.ivan.jantabg.R;

public class Fragment_User_Info extends Fragment{

    View view;
    DataBaseHelper db;
    String userMail;
    TextView userName, email, city, gender, phone, ltd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_information, container, false);
        db = DataBaseHelper.getHelper(view.getContext());
        userMail = getArguments().getString("userMail");
        setInformation();
        return view;
    }

    public void setInformation() {
        Cursor cursor = db.getUserData(userMail);
        cursor.moveToFirst();

        userName = (TextView) view.findViewById(R.id.username_info);
        String userNameInfo = cursor.getString(2);
        userName.setText(userName.getText() + userNameInfo);

        email = (TextView) view.findViewById(R.id.email_info);
        String emailInfo = cursor.getString(0);
        email.setText(email.getText() + emailInfo);

        city = (TextView) view.findViewById(R.id.city_info);
        String cityInfo = cursor.getString(3);
        city.setText(city.getText() + cityInfo);

        gender = (TextView) view.findViewById(R.id.gender_info);
        String genderInfo = cursor.getString(4);
        gender.setText(gender.getText() + genderInfo);

        phone = (TextView) view.findViewById(R.id.phone_info);
        String phoneInfo = cursor.getString(5);
        phone.setText(phone.getText() + phoneInfo);

        ltd = (TextView) view.findViewById(R.id.is_ltd_info);
        String ltdInfo = cursor.getString(6);
        ltd.setText(ltd.getText() + ltdInfo);
    }
}
