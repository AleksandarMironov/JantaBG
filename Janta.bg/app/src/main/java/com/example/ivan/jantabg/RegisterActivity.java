package com.example.ivan.jantabg;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DataBaseHelper dataBase;
    EditText email, firstPassword, secondPassword, username, phone, address;
    RadioGroup genderGroup, firmGroup;
    Button btn_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        dataBase = DataBaseHelper.getHelper(this);
        registerButtonOnClickListener();
    }

    public void registerButtonOnClickListener() {
        email = (EditText) findViewById(R.id.text_reg_mail);
        firstPassword = (EditText) findViewById(R.id.text_reg_pass);
        secondPassword = (EditText) findViewById(R.id.text_reg_pass2);
        username = (EditText) findViewById(R.id.text_reg_name);
        phone = (EditText) findViewById(R.id.text_reg_phone);
        address = (EditText) findViewById(R.id.text_reg_address);
        genderGroup = (RadioGroup) findViewById(R.id.radioGroup_gender);
        firmGroup = (RadioGroup) findViewById(R.id.radioGroup_firm);

        // add checks for ^^^^ here

        btn_Register = (Button) findViewById(R.id.btn_reg_register);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utilities.isMail(email.getText().toString())){
                    boolean temp = dataBase.addNewUser(
                            email.getText().toString(),
                            firstPassword.getText().toString(),
                            username.getText().toString(),
                            address.getText().toString(),
                            ((RadioButton)findViewById(genderGroup.getCheckedRadioButtonId())).getText().toString(),
                            phone.getText().toString(),
                            ((RadioButton)findViewById(firmGroup.getCheckedRadioButtonId())).getText().toString());

                    if(temp){
                        Toast.makeText(RegisterActivity.this, "Now you are registered!", Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("userMail",email.getText().toString());
                        returnIntent.putExtra("pass", firstPassword.getText().toString());
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "ERR!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    email.setError("Wrong email!");
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(RegisterActivity.this);
        a_builder.setMessage("Do you want to cancel registration?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { //activity is in background
                        Intent returnIntent = new Intent();  //getIntent() ?!
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("Cancel registration");
        alert.show();
    }
}