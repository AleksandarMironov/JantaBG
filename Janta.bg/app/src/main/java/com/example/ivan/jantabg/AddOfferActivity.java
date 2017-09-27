package com.example.ivan.jantabg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddOfferActivity extends AppCompatActivity {

    DataBaseHelper db;
    private String userMail;
    private Button btnAdd;
    private EditText price;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        db = DataBaseHelper.getHelper(this);
        userMail = getIntent().getExtras().getString("userMail");
        setOnAddBtnListener();
    }

    public void setOnAddBtnListener(){
        btnAdd = (Button) findViewById(R.id.btn_add_offer_send);
        price = (EditText) findViewById(R.id.editText_add_offer_price);
        text = (EditText) findViewById(R.id.editText_add_offer_text);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String title, String img, String description, double price, String email
                boolean send = db.addNewOffer(text.getText().toString(), "no", text.getText().toString()+"description", Double.parseDouble(price.getText().toString()), userMail);
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
