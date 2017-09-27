package com.example.ivan.jantabg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private DataBaseHelper dataBase;
    private EditText email, password;
    private Button btn_Login, btn_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        dataBase = DataBaseHelper.getHelper(this);
        dataBase.create();
        loginButtonOnClickListener();
        registerButtonOnClickListener();
    }

    public void loginButtonOnClickListener() {
        email = (EditText)findViewById(R.id.editText_loginEmail);
        password = (EditText)findViewById(R.id.editText_loginPassword);
        btn_Login = (Button)findViewById(R.id.button_login);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if ((!mail.matches("[;\"]")) && !pass.matches("[;\"]") && !mail.isEmpty() && !pass.isEmpty() && dataBase.checkPassword(mail, pass)){

                    Intent intent = new Intent("com.example.ivan.jantabg.OffersActivity");//goes to offersActivity
                    intent.putExtra("userMail", mail);
                    startActivity(intent);
                    finish();

                } else {

                    AlertDialog.Builder alert_builder = new AlertDialog.Builder(LoginActivity.this);
                    alert_builder.setMessage("Invalid username or password. If you haven't registered yet, please click on Register button to create your new account.")
                            .setCancelable(false)
                            .setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("REGISTER", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent("com.example.ivan.jantabg.RegisterActivity");
                                    startActivity(intent); //goes directly to register screen
                                }
                            });
                    AlertDialog ad = alert_builder.create();
                    ad.setTitle("ERROR");
                    ad.show();
                }
            }
        });
    }

    public void registerButtonOnClickListener() {
        btn_Register = (Button)findViewById(R.id.button_register);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.ivan.jantabg.RegisterActivity");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(LoginActivity.this);
        a_builder.setMessage("Do you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { //activity is in background
                        finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.putExtra("userMail", "");
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                                /*int pid = android.os.Process.myPid();=====> use this if you want to kill your activity. But its not a good one to do.
                                android.os.Process.killProcess(pid);*/  //(black magic :) )
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("Quit");
        alert.show();
    }
}
