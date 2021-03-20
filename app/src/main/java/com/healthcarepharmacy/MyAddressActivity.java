package com.healthcarepharmacy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.healthcarepharmacy.address.AddressViewActivity;

public class MyAddressActivity extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        String loginNumber = sharedPref.getString("number","0");

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber).child("Address");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                        int addressCount = (int) dataSnapshot.getChildrenCount();

                        if(!(addressCount==0)){

                            Intent intentA = new Intent(MyAddressActivity.this, AddressViewActivity .class);
                            intentA.putExtra("addressChage","000");
                            startActivity(intentA);
                            finish();

                        }



                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        btn = findViewById(R.id.address_btnadd);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAddressActivity.this, AddressDetailsActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}