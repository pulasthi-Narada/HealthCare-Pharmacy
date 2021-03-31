package com.healthcarepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.healthcarepharmacy.address.AddressViewActivity;
import com.healthcarepharmacy.datepcker.DatePickerFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlaceOrderDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText  dateEditText,orderRemark;
    CardView addressCardview;
    Dialog dialog;
    String selectedAddresskey;
    TextView address,name,province,city,number,mail,total;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button placeOrder;
    double price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order_details);
        dateEditText = findViewById(R.id.PlaceOrderDetailsActivity_expectEditText);
        addressCardview = findViewById(R.id.PlaceOrderDetailsActivity_address_cardview);
        address = findViewById(R.id.PlaceOrderDetailsActivity_textView_address);
        name = findViewById(R.id.PlaceOrderDetailsActivity_textView_name);
        province = findViewById(R.id.PlaceOrderDetailsActivity_textView_pro);
        city = findViewById(R.id.PlaceOrderDetailsActivity_textView_city);
        number = findViewById(R.id.PlaceOrderDetailsActivity_textView_number);
        mail = findViewById(R.id.PlaceOrderDetailsActivity_textView_mail);
        radioGroup = findViewById(R.id.PlaceOrderDetailsActivityas_radiogroup);
        orderRemark=findViewById(R.id.PlaceOrderDetailsActivity_order_remark_EditText);

        total = findViewById(R.id.PlaceOrderDetailsActivity_total_textview);
        placeOrder = findViewById(R.id.PlaceOrderDetailsActivity_btn);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 placeOrderNow();


            }
        });

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        if(!(getIntent().getStringExtra("PlaceOrderDetailsActivityDate2").equals("000"))){

            String[] values = getIntent().getStringExtra("PlaceOrderDetailsActivityDate2").split(",");

            if(values.length==2){

                orderRemark.setText(values[0]);
                radioButton.setId(Integer.parseInt(values[1]));
                totalPrice();
            }
            else  if(values.length==4){
                dateEditText.setText(values[0]+","+values[1]);
                orderRemark.setText(values[2]);
                radioButton.setId(Integer.parseInt(values[3]));
                totalPrice();

            }


        }

        totalPrice();


        selectedAddresskey =  getIntent().getStringExtra("selectedAddresskey");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                if(checkedId==R.id.PlaceOrderDetailsActivity_shdule_radioBtn){
                    dateEditText.setVisibility(View.VISIBLE);

                }else {
                    dateEditText.setVisibility(View.INVISIBLE);
                    dateEditText.setText("");
                }
            }
        });




        if(selectedAddresskey.equals(null)){

        }else {



            SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
            String loginNumber = sharedPref.getString("number", "0");
            // database Reference
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber).child("Address");



            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.child(selectedAddresskey).exists()) {

                        address.setText(snapshot.child(selectedAddresskey).child("address").getValue().toString());
                        name.setText(snapshot.child(selectedAddresskey).child("name").getValue().toString());
                        province.setText(snapshot.child(selectedAddresskey).child("province").getValue().toString());
                        city.setText(snapshot.child(selectedAddresskey).child("city").getValue().toString());
                        number.setText(snapshot.child(selectedAddresskey).child("number").getValue().toString());


                        mail.setText(snapshot.child(selectedAddresskey).child("mail").getValue().toString());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        addressCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(PlaceOrderDetailsActivity.this);
                dialog.setContentView(R.layout.adddress_dilog_box);

                // dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.daillog_background));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                dialog.show();
                TextView edit = dialog.findViewById(R.id.address_dilog_box_edit_txt);
                TextView remove = dialog.findViewById(R.id.address_dilog_box_remove_txt);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(radioGroup.getCheckedRadioButtonId()==R.id.PlaceOrderDetailsActivity_shdule_radioBtn) {

                            Intent intentA = new Intent(PlaceOrderDetailsActivity.this, AddressViewActivity.class);
                            intentA.putExtra("addressChage", "111");
                            intentA.putExtra("PlaceOrderDetailsActivityDate", dateEditText.getText().toString()+","+orderRemark.getText().toString()+","+Integer.toString(radioButton.getId()));
                            startActivity(intentA);
                            finish();
                            dialog.dismiss();
                        }else {

                            Intent intentA = new Intent(PlaceOrderDetailsActivity.this, AddressViewActivity.class);
                            intentA.putExtra("addressChage", "111");
                            intentA.putExtra("PlaceOrderDetailsActivityDate",  orderRemark.getText().toString()+","+ Integer.toString(radioButton.getId()));
                            startActivity(intentA);
                            finish();
                            dialog.dismiss();

                        }

                    }
                });

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentA = new Intent(PlaceOrderDetailsActivity.this, AddressViewActivity.class);
                        intentA.putExtra("addressChage","111");
                        startActivity(intentA);
                        finish();
                        dialog.dismiss();
                    }
                });




            }
        });

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });



    }

    private void placeOrderNow() {


        List<String> product = new ArrayList<String>();


        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        String loginNumber = sharedPref.getString("number", "0");
        // database Reference
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber);
        String key1 =  myRef.child("Order").push().getKey();
        String key2 =  myRef.child("Order").child(key1).child("product").push().getKey();

        Time now = new Time();
        now.setToNow();
        int month = now.month+1;

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                if(snapshot.child("Cart").exists()) {

                    for (DataSnapshot snapshot2 : snapshot.child("Cart").getChildren()) {

                        product.add(snapshot2.getKey());

                    }

                    for (int counter = 0; counter < product.size(); counter++) {

                        if(snapshot.child("Cart").exists()) {

                            myRef.child("Order").child(key1).child("product").child(key2).child(product.get(counter)).child("image").setValue(snapshot.child("Cart").child(product.get(counter)).child("image").getValue());
                            myRef.child("Order").child(key1).child("product").child(key2).child(product.get(counter)).child("name").setValue(snapshot.child("Cart").child(product.get(counter)).child("name").getValue());
                            myRef.child("Order").child(key1).child("product").child(key2).child(product.get(counter)).child("price").setValue(snapshot.child("Cart").child(product.get(counter)).child("price").getValue());
                            myRef.child("Order").child(key1).child("product").child(key2).child(product.get(counter)).child("type").setValue(snapshot.child("Cart").child(product.get(counter)).child("type").getValue());
                            myRef.child("Order").child(key1).child("product").child(key2).child(product.get(counter)).child("qty").setValue(snapshot.child("Cart").child(product.get(counter)).child("qty").getValue());
//

                            myRef.child("Order").child(key1).child("OrderAt").setValue(now.year+","+Integer.toString(month)+","+now.monthDay+","+now.hour+","+now.minute+","+now.second);

                            myRef.child("Order").child(key1).child("total").setValue("1672");


                            if(snapshot.child("Address").child(selectedAddresskey).exists()) {
                                        myRef.child("Order").child(key1).child("phoneNumber").setValue(snapshot.child("Address").child(selectedAddresskey).child("number").getValue().toString());

                                        myRef.child("Order").child(key1).child("name").setValue(snapshot.child("Address").child(selectedAddresskey).child("name").getValue().toString());

                                        myRef.child("Order").child(key1).child("addressText").setValue(snapshot.child("Address").child(selectedAddresskey).child("address").getValue().toString());

                                        myRef.child("Order").child(key1).child("latitude").setValue(snapshot.child("Address").child(selectedAddresskey).child("latitude").getValue().toString());
                                       myRef.child("Order").child(key1).child("longitude").setValue(snapshot.child("Address").child(selectedAddresskey).child("longitude").getValue().toString());


                                    }


                        }
                    }


//
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


            myRef.child("Cart").removeValue();
            Toast.makeText(getApplicationContext(),"Successfully Request Your Order!",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(PlaceOrderDetailsActivity.this, MyOrdersActivity.class);
            startActivity(intent);
            finish();





    }

    private void totalPrice() {
        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        String loginNumber = sharedPref.getString("number", "0");

        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber);

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child("Cart").exists()) {

                    for (DataSnapshot snapshot2 : snapshot.child("Cart").getChildren()) {



                        if(!(snapshot2.child("price")==null || snapshot2.child("price").getValue()==null)) {

                            price = price + Double.parseDouble(snapshot2.child("price").getValue().toString())*Integer.parseInt(snapshot2.child("qty").getValue().toString());
                            //Log.i("Result", snapshot2.child("price").getValue().toString()+" "+snapshot2.child("qty").getValue().toString());

                            total.setText("Total : "+Double.toString(price));





                        }else {
                            total.setText("");
                        }
                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        Calendar c = Calendar.getInstance();
         c.set(Calendar.YEAR,year );
         c.set(Calendar.MONTH,month);
         c.set(Calendar.DAY_OF_MONTH,day);
         String curentDate = DateFormat.getDateInstance().format(c.getTime());

         dateEditText.setText(curentDate);


    }


}