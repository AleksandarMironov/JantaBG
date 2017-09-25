package com.example.ivan.jantabg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    DataBaseHelper dataBase;
    EditText email, firstPassword, secondPassword, username, phone, address;
    RadioGroup genderGroup, firmGroup;
    Button btn_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dataBase = DataBaseHelper.getHelper(this);
    }

    public void registerButtonOnClickListener() {
        email = (EditText)findViewById(R.id.text_reg_mail);
        firstPassword = (EditText)findViewById(R.id.text_reg_pass);
        secondPassword = (EditText)findViewById(R.id.text_reg_pass2);
        username = (EditText)findViewById(R.id.text_reg_name);
        phone = (EditText)findViewById(R.id.text_reg_phone);
        address = (EditText)findViewById(R.id.text_reg_address);
        genderGroup = (RadioGroup)findViewById(R.id.radioGroup_gender);
        firmGroup = (RadioGroup)findViewById(R.id.radioGroup_firm);
    }
}
