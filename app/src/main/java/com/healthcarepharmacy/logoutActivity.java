package com.healthcarepharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class logoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);




        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("value", 0);
        editor.apply();

        SharedPreferences sharedPreff = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPreff.getInt("value",0);


            if(loginCode==0) {
            Intent intent = new Intent(logoutActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreff = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPreff.getInt("value",0);


        if(loginCode==0) {
            Intent intent = new Intent(logoutActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreff = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPreff.getInt("value",0);


        if(loginCode==0) {
            Intent intent = new Intent(logoutActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}