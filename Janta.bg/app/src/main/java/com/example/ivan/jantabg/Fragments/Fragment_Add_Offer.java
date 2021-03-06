package com.example.ivan.jantabg.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ivan.jantabg.DataBaseHelper;
import com.example.ivan.jantabg.R;
import com.example.ivan.jantabg.Utilities;

import static android.app.Activity.RESULT_OK;


public class Fragment_Add_Offer extends Fragment{

    View view;
    DataBaseHelper db;
    Bundle bundle;
    private RelativeLayout layout;
    String userMail;
    private EditText title, description, price;
    private Button chooseImage, addOffer;
    private ImageView picture;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_offer, container, false);
        db = DataBaseHelper.getHelper(view.getContext());
        userMail = getArguments().getString("userMail");
        imgDecodableString = "no_image";
        setOnAddBtnListener();
        setOnChooseImgBtnListener();
        hideKeyboardOnClickListener();
        return view;
    }

    public void hideKeyboardOnClickListener(){
        layout = (RelativeLayout) view.findViewById(R.id.lay_add_offer);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }
    
    public void setOnAddBtnListener() {
        final EditText title = (EditText) view.findViewById(R.id.add_offer_title);
        final EditText description = (EditText) view.findViewById(R.id.add_offer_descr);
        final EditText price = (EditText) view.findViewById(R.id.add_offer_price);
        Button addOffer = (Button) view.findViewById(R.id.btn_add_offer_send);

        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String title, String img, String description, double price, String email
                String titleStr = title.getText().toString();
                String descriptionStr = description.getText().toString();
                String priceStr = price.getText().toString();

                if(Utilities.checkString(titleStr) && Utilities.checkString(descriptionStr) && Utilities.checkString(priceStr)){
                    boolean send = db.addNewOffer(
                            titleStr,
                            imgDecodableString,
                            descriptionStr,
                            Double.parseDouble(priceStr),
                            userMail);
                    if(send){
                        bundle = new Bundle();
                        bundle.putString("userMail", userMail);
                        Fragment_Home_Offers homeFragment = new Fragment_Home_Offers();
                        homeFragment.setArguments(bundle);
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        loadFragment(homeFragment);
                        Toast.makeText(view.getContext(), "Offer added.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Sorry we didn't add your offer, pleace try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(!Utilities.checkString(titleStr)){
                        title.setError("Wrong input");
                    }
                    if(!Utilities.checkString(descriptionStr)){
                        description.setError("Wrong input");
                    }
                    if(!Utilities.checkString(priceStr)){
                        price.setError("Wrong input. Max price 999 999.99");
                    }
                }

            }
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frMain,fragment);
        ft.commit();
    }
    public void setOnChooseImgBtnListener(){
        Button chooseImage = (Button) view.findViewById(R.id.add_offer_choose_image);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery(v);
            }
        });
    }


    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = view.getContext().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) view.findViewById(R.id.add_offer_imageview);
                // Set the Image in ImageView after decoding the String

                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(view.getContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
