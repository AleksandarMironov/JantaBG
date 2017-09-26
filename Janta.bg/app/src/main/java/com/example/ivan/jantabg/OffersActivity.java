package com.example.ivan.jantabg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.ivan.jantabg.MyRecyclerViewAdapter.ItemClickListener;

import java.util.ArrayList;

public class OffersActivity extends AppCompatActivity implements ItemClickListener {

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

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

        // set up the RecyclerView
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
}
