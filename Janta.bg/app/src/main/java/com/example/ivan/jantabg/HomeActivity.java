package com.example.ivan.jantabg;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.jantabg.Fragments.Fragment_Add_Offer;
import com.example.ivan.jantabg.Fragments.Fragment_Home_Offers;
import com.example.ivan.jantabg.Fragments.Fragment_Update_Information;
import com.example.ivan.jantabg.Fragments.Fragment_User_Info;

public class HomeActivity extends AppCompatActivity {


    DataBaseHelper db;
    private String userMail;
    private ImageButton btnAction;
    private Button btnQuit;
    private Button btnAddOffer;
    private Button btnUserInfo;
    private Button btnLogOut;
    private TextView janta;
    private Button btnHome;
    private DrawerLayout drawerLayout;
    public static TextView usernameWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_offers);
        drawerLayout = (DrawerLayout)findViewById(R.id.dlContent);
        userMail = getIntent().getExtras().getString("userMail");
        Bundle bundle = new Bundle();
        bundle.putString("userMail", userMail);
        Fragment_Home_Offers homeFragment = new Fragment_Home_Offers();
        homeFragment.setArguments(bundle);
        loadFragment(homeFragment);
        getSupportActionBar().hide();
        db = DataBaseHelper.getHelper(this);
        verifyStoragePermissions(this);
        verifyCallPermissions(this);
        drawerDropMenucreator(); //metod menu
        setOnQuitBtnClickListener();
        setOnAddOfferBtnClickListener();
        setOnUserInfoBtnClickListener();
        setOnLogOutBtnClickListener();
        setOnHomeBtnClickListener();
        setOnJantaClickListener();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frMain,fragment);
        ft.commit();
    }

        /////////////////////// this is needed for API 23+ to permit permission !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, //api 16
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    /////////////////////////
    // Call Permissions
    private static final int REQUEST_CALL = 2;
    private static String[] PERMISSIONS_CALL = {
            Manifest.permission.CALL_PHONE
    };
    public static void verifyCallPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_CALL,
                    REQUEST_CALL
            );
        }
    }
    /////////////////////////

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

    public void setOnHomeBtnClickListener(){
        btnHome = (Button) findViewById(R.id.btn_home_button);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("userMail", userMail);
                Fragment_Home_Offers homeFragment = new Fragment_Home_Offers();
                homeFragment.setArguments(bundle2);
                loadFragment(homeFragment);
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
            }
        });
    }

    public void setOnJantaClickListener(){
        janta = (TextView) findViewById(R.id.text_janta_main);
        janta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("userMail", userMail);
                Fragment_Home_Offers homeFragment = new Fragment_Home_Offers();
                homeFragment.setArguments(bundle2);
                loadFragment(homeFragment);
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
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
                Bundle bundle1 = new Bundle();
                bundle1.putString("userMail", userMail);
                Fragment_User_Info userInfo = new Fragment_User_Info();
                userInfo.setArguments(bundle1);
                loadFragment(userInfo);
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
            }
        });
    }

    public void setOnAddOfferBtnClickListener(){
        btnAddOffer = (Button) findViewById(R.id.btn_add_item);
        btnAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("userMail", userMail);
                Fragment_Add_Offer addOffer = new Fragment_Add_Offer();
                addOffer.setArguments(bundle1);
                loadFragment(addOffer);
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
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
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
//        else if(getFragmentManager().findFragmentById(R.id.frUpdate) instanceof Fragment_Update_Information) {
//            Bundle bundle3 = new Bundle();
//            bundle3.putString("userMail", userMail);
//            Fragment_User_Info userInfo = new Fragment_User_Info();
//            userInfo.setArguments(bundle3);
//            loadFragment(userInfo);
//        }
        else if (getFragmentManager().findFragmentById(R.id.frMain) instanceof Fragment_Home_Offers){
            exit();
        } else {
            Bundle bundle2 = new Bundle();
            bundle2.putString("userMail", userMail);
            Fragment_Home_Offers homeFragment = new Fragment_Home_Offers();
            homeFragment.setArguments(bundle2);
            loadFragment(homeFragment);
        }
    }
}
