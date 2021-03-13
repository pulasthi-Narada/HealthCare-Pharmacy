package com.healthcarepharmacy;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.healthcarepharmacy.Data.Customer;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class RegisterActivity extends AppCompatActivity {
   EditText phoneNumber,name,email,password,passwordCom;
   Button   reg;
    CountryCodePicker ccp;

    String phoneNumberWithCountryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phoneNumber = findViewById(R.id.Register_activity_txt_phone);
        name = findViewById(R.id.Register_activity_txt_name);
        email = findViewById(R.id.Register_activity_Login_txt_email);
        email = findViewById(R.id.Register_activity_Login_txt_email);
        password = findViewById(R.id.Register_activity_Login_txt_pwd);
        passwordCom = findViewById(R.id.Register_activity_txt_pwdhint);

        ccp = findViewById(R.id.Register_activity_cpp);

        reg = findViewById(R.id.Register_activity_btnreg);

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPref.getInt("value",0);

        if(loginCode==1){
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(RegInputValidation()==1) {

                    phoneNumberWithCountryCode="+"+ccp.getSelectedCountryCode()+phoneNumber.getText().toString();
                    SendData();




                }


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPref.getInt("value",0);

        if(loginCode==1){
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPref.getInt("value",0);

        if(loginCode==1){
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private int RegInputValidation() {
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Name can't be blank", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(RegisterActivity.this, "email can't be blank", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(TextUtils.isEmpty(phoneNumber.getText().toString()) || (!(phoneNumber.getText().length()==9)) ){
            Toast.makeText(RegisterActivity.this, "phone number can't be blank and phone number have 9 digit ", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if( !(ccp.getSelectedCountryCode().toString().equalsIgnoreCase("94"))  ){
            Toast.makeText(RegisterActivity.this, "This services only available in  Sri lanka", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if( TextUtils.isEmpty(password.getText().toString())  ){
            Toast.makeText(RegisterActivity.this, "password can't be blank ", Toast.LENGTH_SHORT).show();
            return 0;
        }

        else if(  TextUtils.isEmpty(passwordCom.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Confirm password can't be blank ", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if( !(password.getText().toString().equals(passwordCom.getText().toString())) ){
            Toast.makeText(RegisterActivity.this, "password and Confirm password must be same ", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else {
            return 1;
        }
    }


    private void SendData() {




            DatabaseReference customer_registerCheck = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers");


            customer_registerCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    if (dataSnapshot.child(phoneNumberWithCountryCode).exists()) {
                        Toast.makeText(RegisterActivity.this, "all ready have an account in this number", Toast.LENGTH_SHORT).show();
                    } else {

                        Customer customer = new Customer();


                        DatabaseReference customer_register = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(phoneNumberWithCountryCode);


                        customer.setEmail(email.getText().toString());
                        customer.setName(name.getText().toString());

                        customer.setPassword(passwordHashing(password.getText().toString()));

                        customer_register.setValue(customer);
                        Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                        rememberMe();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



    }

    private void rememberMe() {

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("value", 1);
        editor.putString("number", phoneNumberWithCountryCode);
        editor.apply();
    }

    private String passwordHashing(String password){

        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}