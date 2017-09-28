package com.example.ivan.jantabg;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddOfferActivity extends AppCompatActivity {

    final static int REQUEST_CODE_GALERY = 999;
    DataBaseHelper db;
    private String userMail;
    private EditText title, description, price;
    private Button chooseImage, addOffer;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        db = DataBaseHelper.getHelper(this);
        userMail = getIntent().getExtras().getString("userMail");
        setOnAddBtnListener();
    }

    public void setOnAddBtnListener(){
        final EditText title = (EditText)findViewById(R.id.add_offer_title);
        final EditText description = (EditText)findViewById(R.id.add_offer_descr);
        final EditText price = (EditText)findViewById(R.id.add_offer_price);
        Button chooseImage = (Button)findViewById(R.id.add_offer_choose_image);
        Button addOffer = (Button)findViewById(R.id.btn_add_offer_send);
        ImageView image = (ImageView)findViewById(R.id.add_offer_imageview);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActivityCompat.requestPermissions(AddOfferActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_CODE_GALERY});
            }
        });


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
}
