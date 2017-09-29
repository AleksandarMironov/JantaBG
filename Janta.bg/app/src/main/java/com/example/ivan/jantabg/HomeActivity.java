package com.example.ivan.jantabg;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ivan.jantabg.Fragments.Fragment_Add_Offer;
import com.example.ivan.jantabg.Fragments.Fragment_Home_Offers;

public class HomeActivity extends AppCompatActivity {


    DataBaseHelper db;
    Bundle bundle;
    private String userMail;
    private ImageButton btnAction;
    private Button btnQuit;
    private Button btnAddOffer;
    private Button btnUserInfo;
    private Button btnLogOut;
    private TextView usernameWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_offers);
        bundle = new Bundle();
        bundle.putString("userMail", userMail);
        Fragment_Home_Offers homeFragment = new Fragment_Home_Offers();
        homeFragment.setArguments(bundle);
        loadFragment(homeFragment);
        getSupportActionBar().hide();
        db = DataBaseHelper.getHelper(this);
        userMail = getIntent().getExtras().getString("userMail");
        drawerDropMenucreator(); //metod menu
        setOnQuitBtnClickListener();
        setOnAddOfferBtnClickListener();
        setOnUserInfoBtnClickListener();
        setOnLogOutBtnClickListener();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frMain,fragment);
        ft.commit();
    }


    public void drawerDropMenucreator() {
        btnAction = (ImageButton)findViewById(R.id.btnAction);
        usernameWelcome = (TextView)findViewById(R.id.textView_toolbar_welcome_user);
        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.dlContent);

        //sl 2 reda ne murdat main screena pri izlizane na menuto
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setStatusBarBackground(Color.TRANSPARENT);

        //slagame fukcionalnost na butona
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        String name = db.getName(userMail);
        usernameWelcome.setText(usernameWelcome.getText() + "\n" + name);
    }

    public void setOnQuitBtnClickListener(){
        btnQuit = (Button) findViewById(R.id.btn_quit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }

    public void setOnLogOutBtnClickListener(){
        btnLogOut = (Button) findViewById(R.id.btn_log_out);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.putExtra("userMail", "");
                startActivity(intent);
                finish();
            }
        });
    }

    public void setOnUserInfoBtnClickListener(){
        btnUserInfo = (Button) findViewById(R.id.btn_user_info);
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserInfoActivity.class);
                intent.putExtra("userMail", userMail);
                startActivity(intent);
            }
        });
    }

    public void setOnAddOfferBtnClickListener(){
        btnAddOffer = (Button) findViewById(R.id.btn_add_item);
        btnAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Add_Offer addOffer = new Fragment_Add_Offer();
                addOffer.setArguments(bundle);
                loadFragment(addOffer);
            }
        });
    }

    public void exit(){ ///exit dialog
        AlertDialog.Builder a_builder = new AlertDialog.Builder(HomeActivity.this);
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

    @Override
    public void onBackPressed() {
        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.dlContent);
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            exit();
        }
    }
}
