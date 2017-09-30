package com.example.ivan.jantabg.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
        Bundle bundle = new Bundle();
        bundle.putString("userMail", userMail);
        bundle.putString("offerId", adapter.getItem(position));
        Fragment_Offer_Info offerInfo = new Fragment_Offer_Info();
        offerInfo.setArguments(bundle);
        loadFragment(offerInfo);
        //Toast.makeText(this, "You clicked offer id " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(""); <--- add offer details page here
        startActivity(intent);*/
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frMain,fragment);
        ft.commit();
    }

}
