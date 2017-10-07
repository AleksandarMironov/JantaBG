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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.jantabg.DataBaseHelper;
import com.example.ivan.jantabg.R;
import com.example.ivan.jantabg.Utilities;

public class Fragment_Update_Information extends Fragment {

    View view;
    DataBaseHelper db;
    String userMail;
    private TextView email;
    private EditText username, address, phone, password;
    private RadioGroup gender, ltd;
    private Button save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_update_information, container, false);
        db = DataBaseHelper.getHelper(view.getContext());
        userMail = getArguments().getString("userMail");
        email = (TextView)view.findViewById(R.id.update_info_mail);
        username = (EditText)view.findViewById(R.id.update_info_username);
        address = (EditText)view.findViewById(R.id.update_info_address);
        phone = (EditText)view.findViewById(R.id.update_info_phone);
        gender = (RadioGroup)view.findViewById(R.id.radioGroup_gender_update_info);
        ltd = (RadioGroup)view.findViewById(R.id.radioGroup_firm_update_info);
        password  = (EditText)view.findViewById(R.id.update_info_password_confirmation);
        save = (Button)view.findViewById(R.id.update_info_save_btn);
        setInformation();
        saveBtnListener();
        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frMain,fragment);
        ft.commit();
    }

    public void saveBtnListener(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor userData = db.getUserData(userMail);
                userData.moveToFirst();
                //data unmodifiables
                String mail = userData.getString(0);
                String pass = userData.getString(1);
                //data modifiables
                String user = username.getText().toString();
                String addr = address.getText().toString();
                String ph = phone.getText().toString();
                String gend = ((RadioButton)view.findViewById(gender.getCheckedRadioButtonId())).getText().toString();
                String firm = ((RadioButton)view.findViewById(ltd.getCheckedRadioButtonId())).getText().toString();

                if(!Utilities.checkString(user)){
                    username.setError("Forbidden symbols or empty");
                    return;
                }
                if(!Utilities.checkString(addr)){
                    address.setError("Forbidden symbols or empty");
                    return;
                }
                if(!Utilities.checkString(ph)){
                    phone.setError("Forbidden symbols or empty");
                    return;
                }

                String psw = password.getText().toString();
                if (pass.equals(psw)) {
                    boolean temp = db.changeUserInfo(mail, pass, user, addr, gend, ph, firm);
                    if (temp) {
                        Toast.makeText(view.getContext(), "Successful update", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("userMail", userMail);
                        Fragment_User_Info userInformation = new Fragment_User_Info();
                        userInformation.setArguments(bundle);
                        loadFragment(userInformation);
                    } else {
                        Toast.makeText(view.getContext(), "Unsuccessful update", Toast.LENGTH_SHORT).show();
                    }
                } else {
                        Toast.makeText(view.getContext(), "Please insert valid password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void setInformation() {
        Cursor userData = db.getUserData(userMail);
        userData.moveToFirst();

        String mailData = userData.getString(0);
        String usernameData = userData.getString(2);
        String addressData = userData.getString(3);
        String phoneData = userData.getString(5);
        String genderData = userData.getString(4);
        String ltdData = userData.getString(6);

        email.setText(email.getText() + mailData);
        username.setText(usernameData);
        address.setText(addressData);
        phone.setText(phoneData);
        if (genderData.equalsIgnoreCase("male")) {
            gender.check(R.id.radio_reg_male);
        } else if (genderData.equalsIgnoreCase("female")) {
            gender.check(R.id.radio_reg_female);
        } else {
            gender.check(R.id.radio_reg_other);
        }
        if (ltdData.equalsIgnoreCase("Private Person")) {
            ltd.check(R.id.radio_reg_private);
        } else {
            ltd.check(R.id.radio_reg_ltd);
        }
    }
}
