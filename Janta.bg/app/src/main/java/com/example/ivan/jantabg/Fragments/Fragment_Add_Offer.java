package com.example.ivan.jantabg.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ivan.jantabg.AddOfferActivity;
import com.example.ivan.jantabg.DataBaseHelper;
import com.example.ivan.jantabg.HomeActivity;
import com.example.ivan.jantabg.MyRecyclerViewAdapter;
import com.example.ivan.jantabg.R;

import static android.app.Activity.RESULT_OK;


public class Fragment_Add_Offer extends Fragment{

    View view;
    DataBaseHelper db;
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
        //verifyStoragePermissions(view);
        return view;
    }

//    /////////////////////// this is needed for API 23+ to permit permission !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    // Storage Permissions
//    private static final int REQUEST_EXTERNAL_STORAGE = 1;
//    private static String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.READ_EXTERNAL_STORAGE, //api 16
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//    };
//
//    /**
//     * Checks if the app has permission to write to device storage
//     *
//     * If the app does not has permission then the user will be prompted to grant permissions
//     *
//     * @param activity
//     */
//    public static void verifyStoragePermissions(View view) {
//        // Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // We don't have permission so prompt the user
//            ActivityCompat.requestPermissions(
//                    ,
//                    PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE
//            );
//        }
//    }
//    /////////////////////////

    public void setOnAddBtnListener() {
        final EditText title = (EditText) view.findViewById(R.id.add_offer_title);
        final EditText description = (EditText) view.findViewById(R.id.add_offer_descr);
        final EditText price = (EditText) view.findViewById(R.id.add_offer_price);
        Button chooseImage = (Button) view.findViewById(R.id.add_offer_choose_image);
        Button addOffer = (Button) view.findViewById(R.id.btn_add_offer_send);
        ImageView image = (ImageView) view.findViewById(R.id.add_offer_imageview);

        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String title, String img, String description, double price, String email
                boolean send = db.addNewOffer(
                        title.getText().toString(),
                        imgDecodableString,
                        description.getText().toString(),
                        Double.parseDouble(price.getText().toString()),
                        userMail);
                if(send){
                    Intent in = new Intent("com.example.ivan.jantabg.HomeActivity");
                    in.putExtra("userMail", userMail);
                    startActivity(in);
                } else {
                    Toast.makeText(view.getContext(), "Sorry we didn't add your offer, pleace try again", Toast.LENGTH_SHORT).show();
                }
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