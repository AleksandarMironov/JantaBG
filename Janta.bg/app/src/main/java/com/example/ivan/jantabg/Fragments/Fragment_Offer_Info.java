package com.example.ivan.jantabg.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.jantabg.DataBaseHelper;
import com.example.ivan.jantabg.R;

public class Fragment_Offer_Info extends Fragment {

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
        cursorOffer.moveToFirst();

        Cursor cursorUser = db.getUserData(cursorOffer.getString(6));
        cursorUser.moveToFirst();

        String userEmail = cursorUser.getString(0);
        String userName = cursorUser.getString(2);
        String userAddres = cursorUser.getString(3);
        String userPhone = cursorUser.getString(5);

        String offerTitle = cursorOffer.getString(1);
        String offerDescription = cursorOffer.getString(3);
        String offerPrice = cursorOffer.getString(4);
        String offerImgPath = cursorOffer.getString(2);
        //offer date time = col 5....
        test.setText(offerTitle + "\n" + offerDescription + "\n" + offerPrice + "\n" + offerImgPath + "\n" + userEmail + "\n" + userName  + "\n" + userAddres  + "\n" +  userPhone);

    }
}