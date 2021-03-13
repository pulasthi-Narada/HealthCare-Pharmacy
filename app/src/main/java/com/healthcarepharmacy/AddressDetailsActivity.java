package com.healthcarepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class AddressDetailsActivity extends AppCompatActivity {
    Button addressDetailsActivityBtn;
    EditText name,number,mail,address,city,province;
    CountryCodePicker ccp;

    String phoneNumberWithCountryCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        addressDetailsActivityBtn = findViewById(R.id.AddressDetailsActivity_btn);
        name = findViewById(R.id.AddressDetailsActivity_editTxt_Name);
        number = findViewById(R.id.AddressDetailsActivity_editTxt_Phone);
        mail = findViewById(R.id.AddressDetailsActivity_editTxt_Email);
        address = findViewById(R.id.AddressDetailsActivity_editTxt_address);
        city = findViewById(R.id.AddressDetailsActivity_editTxt_city);
        province = findViewById(R.id.AddressDetailsActivity_editTxt_province);
        ccp = findViewById(R.id.AddressDetailsActivity_cpp);





        addressDetailsActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(RegInputValidation2()==1) {

                    phoneNumberWithCountryCode="+"+ccp.getSelectedCountryCode()+number.getText().toString();

                    SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
                    String loginNumber = sharedPref.getString("number","0");

                    DatabaseReference cusSetAddress = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber).child("Address").push();

                    final String  addressPushKey= cusSetAddress.getKey();

                    cusSetAddress.child("name").setValue(name.getText().toString());
                    cusSetAddress.child("number").setValue(phoneNumberWithCountryCode);
                    cusSetAddress.child("mail").setValue(mail.getText().toString());
                    cusSetAddress.child("address").setValue(address.getText().toString());
                   cusSetAddress.child("city").setValue(city.getText().toString());
                    cusSetAddress.child("province").setValue(province.getText().toString());




                    Intent addressDetailsActivityIntent = new Intent(AddressDetailsActivity.this, AddressPermissionActivity.class);
                    addressDetailsActivityIntent.putExtra("addressPushKey", addressPushKey);
                    startActivity(addressDetailsActivityIntent);
                    finish();



                }
                

            }
        });

    }

    private int RegInputValidation2() {
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(AddressDetailsActivity.this, "Name can't be blank", Toast.LENGTH_SHORT).show();
            return 0;
        }

        else if(TextUtils.isEmpty(number.getText().toString()) || (!(number.getText().length()==9)) ){
            Toast.makeText(AddressDetailsActivity.this, "phone number can't be blank and phone number have 9 digit ", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if( !(ccp.getSelectedCountryCode().toString().equalsIgnoreCase("94"))  ){
            Toast.makeText(AddressDetailsActivity.this, "This services only available in  Sri lanka", Toast.LENGTH_SHORT).show();
            return 0;
        }

        else if(TextUtils.isEmpty(mail.getText().toString())){
            Toast.makeText(AddressDetailsActivity.this, "email can't be blank", Toast.LENGTH_SHORT).show();
            return 0;
        }


        else if( TextUtils.isEmpty(address.getText().toString())  ){
            Toast.makeText(AddressDetailsActivity.this, "Address can't be blank ", Toast.LENGTH_SHORT).show();
            return 0;
        }

        else if(  TextUtils.isEmpty(city.getText().toString())) {
            Toast.makeText(AddressDetailsActivity.this, "City can't be blank ", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(  TextUtils.isEmpty(province.getText().toString())) {
            Toast.makeText(AddressDetailsActivity.this, "Province can't be blank ", Toast.LENGTH_SHORT).show();
            return 0;
        }

        else {
            return 1;
        }
    }
}