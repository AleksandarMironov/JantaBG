package com.example.ivan.jantabg.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ivan.jantabg.DataBaseHelper;
import com.example.ivan.jantabg.MyRecyclerViewAdapter;
import com.example.ivan.jantabg.R;

/**
 * Created by Ivan on 09/29/2017.
 */

public class Fragment_Home_Offers extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    View view;
    DataBaseHelper db;
    MyRecyclerViewAdapter adapter;
    String userMail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_offers, container, false);
        db = DataBaseHelper.getHelper(view.getContext());
        userMail = getArguments().getString("userMail");

        // data to populate the RecyclerView with
        Cursor offersData = db.getOffers(); //id title price
        //set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MyRecyclerViewAdapter(view.getContext(), offersData);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        if(position == 0){
            Intent intent = new Intent("com.example.ivan.jantabg.AddOfferActivity");
            intent.putExtra("userMail", userMail);
            startActivity(intent);
        }
        //Toast.makeText(this, "You clicked offer id " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(""); <--- add offer details page here
        startActivity(intent);*/
    }

}
