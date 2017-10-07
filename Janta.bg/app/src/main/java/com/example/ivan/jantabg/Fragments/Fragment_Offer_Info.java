package com.example.ivan.jantabg.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.jantabg.DataBaseHelper;
import com.example.ivan.jantabg.R;

import java.io.File;

public class Fragment_Offer_Info extends Fragment {

     View view;
     DataBaseHelper db;
     String userMail;
     String offerId;
    private TextView offerBy, title, descr, price;
    private ImageView image;

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
        offerBy = (TextView)view.findViewById(R.id.fragment_offerby_offerifno);
        title = (TextView)view.findViewById(R.id.fragment_titleoffer_offerifno);
        descr = (TextView)view.findViewById(R.id.fragment_descroffer_offerifno);
        price = (TextView)view.findViewById(R.id.fragment_priceoffer_offerifno);
        image = (ImageView)view.findViewById(R.id.image_view_offer_info);

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

//        Cursor loggedUser = db.getUserData(userMail);
//        loggedUser.moveToFirst();
//
//        String loggedUserMail = loggedUser.getString(0);
//
//        if (loggedUserMail.equals(userEmail)) {
//            offerBy.setText("You");
//        } else {
//            offerBy.setText(userName);
//        }
        title.setText(title.getText() + offerTitle);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < offerDescription.length(); i++) {
            if (i == 23) {
                sb.append("\n");
            }
            sb.append(offerDescription.charAt(i));
        }
            descr.setText(descr.getText() + sb.toString());
            price.setText(price.getText() + "$" + offerPrice);

            File imgFile = new File(offerImgPath);

            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                image.setImageBitmap(myBitmap);
            } else {
                image.setImageResource(R.drawable.empty);
            }
            //offer date time = col 5....
//        test.setText(offerTitle + "\n" + offerDescription + "\n" + offerPrice + "\n" + offerImgPath + "\n" + userEmail + "\n" + userName  + "\n" + userAddres  + "\n" +  userPhone);
    }
}