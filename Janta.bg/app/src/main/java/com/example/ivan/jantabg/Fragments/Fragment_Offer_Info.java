package com.example.ivan.jantabg.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.jantabg.DataBaseHelper;
import com.example.ivan.jantabg.R;

public class Fragment_Offer_Info extends android.app.Fragment {

    View view;
    DataBaseHelper db;
    String userMail;
    String offerId;
    TextView test;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__offer__info, container, false);
        db = DataBaseHelper.getHelper(view.getContext());
        userMail = getArguments().getString("userMail");
        offerId = getArguments().getString("offerId");
        setInformation();
        return view;
    }

    public void setInformation() {
        test = (TextView) view.findViewById(R.id.testText);
        Cursor cursorOffer = db.getOfferData(offerId);
        Cursor cursorUser = db.getUserData(userMail);
        cursorOffer.moveToFirst();
        for(int i = 0; i < cursorOffer.getColumnCount(); i++){
           test.setText(test.getText() + cursorOffer.getString(i));
        }
    }

}

