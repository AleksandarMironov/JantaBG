package com.example.ivan.jantabg;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddOfferActivity extends AppCompatActivity {

    DataBaseHelper db;
    private String userMail;
    private EditText title, description, price;
    private Button chooseImage, addOffer;
    private ImageView picture;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        db = DataBaseHelper.getHelper(this);
        userMail = getIntent().getExtras().getString("userMail");
        setOnAddBtnListener();
    }

    public void setOnAddBtnListener() {
        final EditText title = (EditText) findViewById(R.id.add_offer_title);
        final EditText description = (EditText) findViewById(R.id.add_offer_descr);
        final EditText price = (EditText) findViewById(R.id.add_offer_price);
        Button chooseImage = (Button) findViewById(R.id.add_offer_choose_image);
        Button addOffer = (Button) findViewById(R.id.btn_add_offer_send);
        ImageView image = (ImageView) findViewById(R.id.add_offer_imageview);

        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String title, String img, String description, double price, String email
                boolean send = db.addNewOffer(
                        title.getText().toString(),
                        "image",
                        description.getText().toString(),
                        Double.parseDouble(price.getText().toString()),
                        userMail);
                if(send){
                    Intent in = new Intent("com.example.ivan.jantabg.OffersActivity");
                    in.putExtra("userMail", userMail);
                    startActivity(in);
                    finish();
                } else {
                    Toast.makeText(AddOfferActivity.this, "Sorry we didn't add your offer, pleace try again", Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.add_offer_imageview);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
