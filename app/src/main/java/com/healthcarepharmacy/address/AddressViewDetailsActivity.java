package com.healthcarepharmacy.address;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;
import com.healthcarepharmacy.AddressPermissionActivity;
import com.healthcarepharmacy.R;

public class AddressViewDetailsActivity extends AppCompatActivity {

    Button AddressViewDetailsActivityBtn;
    EditText name,number,mail,address,city,province;
    CountryCodePicker ccp;
    String phoneNumberWithCountryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_view_details);


        AddressViewDetailsActivityBtn = findViewById(R.id.AddressViewDetailsActivity_btn);
        name = findViewById(R.id.AddressViewDetailsActivity_editTxt_Name);
        number = findViewById(R.id.AddressViewDetailsActivity_editTxt_Phone);
        mail = findViewById(R.id.AddressViewDetailsActivity_editTxt_Email);
        address = findViewById(R.id.AddressViewDetailsActivity_editTxt_address);
        city = findViewById(R.id.AddressViewDetailsActivity_editTxt_city);
        province = findViewById(R.id.AddressViewDetailsActivity_editTxt_province);
        ccp = findViewById(R.id.AddressViewDetailsActivity_cpp);





        AddressViewDetailsActivityBtn.setOnClickListener(new View.OnClickListener() {
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




                    Intent AddressViewDetailsActivityIntent = new Intent(AddressViewDetailsActivity.this, AddressPermissionActivity.class);
                    AddressViewDetailsActivityIntent.putExtra("addressPushKey", addressPushKey);
                    startActivity(AddressViewDetailsActivityIntent);
                    finish();



                }


            }
        });
    }


    private int RegInputValidation2() {
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(AddressViewDetailsActivity.this, "Name can't be blank", Toast.LENGTH_SHORT).show();
            return 0;
        }

        else if(TextUtils.isEmpty(number.getText().toString()) || (!(number.getText().length()==9)) ){
            Toast.makeText(AddressViewDetailsActivity.this, "phone number can't be blank and phone number have 9 digit ", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if( !(ccp.getSelectedCountryCode().toString().equalsIgnoreCase("94"))  ){
            Toast.makeText(AddressViewDetailsActivity.this, "This services only available in  Sri lanka", Toast.LENGTH_SHORT).show();
            return 0;
        }

        else if(TextUtils.isEmpty(mail.getText().toString())){
            Toast.makeText(AddressViewDetailsActivity.this, "email can't be blank", Toast.LENGTH_SHORT).show();
            return 0;
        }


        else if( TextUtils.isEmpty(address.getText().toString())  ){
            Toast.makeText(AddressViewDetailsActivity.this, "Address can't be blank ", Toast.LENGTH_SHORT).show();
            return 0;
        }

        else if(  TextUtils.isEmpty(city.getText().toString())) {
            Toast.makeText(AddressViewDetailsActivity.this, "City can't be blank ", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(  TextUtils.isEmpty(province.getText().toString())) {
            Toast.makeText(AddressViewDetailsActivity.this, "Province can't be blank ", Toast.LENGTH_SHORT).show();
            return 0;
        }

        else {
            return 1;
        }
    }
    
    
}