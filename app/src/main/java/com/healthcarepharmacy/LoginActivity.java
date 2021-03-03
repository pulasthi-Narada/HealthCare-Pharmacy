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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
        TextView reg;
         EditText phoneNumber,password;
        Button login;
        CountryCodePicker ccp;

         String phoneNumberWithCountryCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reg =findViewById(R.id.Login_txt_register);
        login =findViewById(R.id.Login_btnlogin);
        phoneNumber =findViewById(R.id.Login_txt_email);
        password =findViewById(R.id.Login_txt_pwd);
        ccp =findViewById(R.id.Login_activity_cpp);

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPref.getInt("value",0);

        if(loginCode==1){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(RegInputValidation()==1) {

                    phoneNumberWithCountryCode="+"+ccp.getSelectedCountryCode()+phoneNumber.getText().toString();
                    Login();

                }

            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPref.getInt("value",0);

        if(loginCode==1){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private void Login() {

        DatabaseReference customer_login = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers");//This is the database reference



        customer_login.addValueEventListener(new ValueEventListener() {//An event which checks if the record exists
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(phoneNumberWithCountryCode).exists()) {//Checks if the phone number exists or not within the database


                    if (Objects.equals(dataSnapshot.child(phoneNumberWithCountryCode).child("password").getValue(), passwordHashing(password.getText().toString()))){//Checks if the password matches the phone number
                        Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();//Shows a message


                            rememberMe();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);//After the login process is completed it starts the map activity.
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();//If the password is typed incorrectly this message is shown.
                    }

                }else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();//If the phone number is typed incorrectly this message is shown.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {//If any Database error occurs this method will execute

            }
        });



    }

    private void rememberMe() {

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("value", 1);
        editor.apply();
    }


    private int RegInputValidation() {

         if(TextUtils.isEmpty(phoneNumber.getText().toString()) || (!(phoneNumber.getText().length()==9)) ){
            Toast.makeText(LoginActivity.this, "phone number can't be blank and phone number have 9 digit ", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if( !(ccp.getSelectedCountryCode().toString().equalsIgnoreCase("94"))  ){
            Toast.makeText(LoginActivity.this, "This services only available in  Sri lanka", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if( TextUtils.isEmpty(password.getText().toString())  ){
            Toast.makeText(LoginActivity.this, "password can't be blank ", Toast.LENGTH_SHORT).show();
            return 0;
        }


        else {
            return 1;
        }
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