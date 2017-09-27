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
    private Button btnAdd;
    private EditText mail;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        db = DataBaseHelper.getHelper(this);
        setOnAddBtnListener();
    }

    public void setOnAddBtnListener(){
        btnAdd = (Button) findViewById(R.id.btn_add_offer_send);
        mail = (EditText) findViewById(R.id.editText_add_offer_mail);
        text = (EditText) findViewById(R.id.editText_add_offer_text);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean send = db.addNewOffer(text.getText().toString(), "no", text.getText().toString()+"description", 100, mail.getText().toString());
                if(send){
                    Intent in = new Intent("com.example.ivan.jantabg.OffersActivity");
                    startActivity(in);
                } else {
                    Toast.makeText(AddOfferActivity.this, "Sorry we didn't add your offer, pleace try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
