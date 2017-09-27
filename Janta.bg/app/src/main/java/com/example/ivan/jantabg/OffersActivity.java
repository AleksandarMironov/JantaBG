package com.example.ivan.jantabg;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ivan.jantabg.MyRecyclerViewAdapter.ItemClickListener;

import java.util.ArrayList;

public class OffersActivity extends AppCompatActivity implements ItemClickListener {

    MyRecyclerViewAdapter adapter;
    private Button btnAction;
    private Button btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        drawerDropMenucreator(); //metod menu
        onQuitBtnClickListener();

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Janta");
        animalNames.add("guma");
        animalNames.add("druga janta");
        animalNames.add("Janta");
        animalNames.add("guma");
        animalNames.add("druga janta");
        animalNames.add("Janta");
        animalNames.add("guma");
        animalNames.add("druga janta");
        animalNames.add("Janta");
        animalNames.add("guma");
        animalNames.add("druga janta");
        animalNames.add("Janta");
        animalNames.add("guma");
        animalNames.add("druga janta");
        animalNames.add("Janta");
        animalNames.add("guma");
        animalNames.add("druga janta");
        animalNames.add("Janta");
        animalNames.add("guma");
        animalNames.add("druga janta");
        animalNames.add("Janta");
        animalNames.add("guma");
        animalNames.add("druga janta");

         //set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void drawerDropMenucreator() {
        btnAction = (Button)findViewById(R.id.btnAction);
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
    }

    public void onQuitBtnClickListener(){
        btnQuit = (Button) findViewById(R.id.btn_quit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(OffersActivity.this);
                a_builder.setMessage("Do you want to Exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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
        });
    }
}
