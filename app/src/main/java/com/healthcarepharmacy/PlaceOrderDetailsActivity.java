package com.healthcarepharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.healthcarepharmacy.address.AddressViewActivity;
import com.healthcarepharmacy.datepcker.DatePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class PlaceOrderDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText  dateEditText;
    CardView addressCardview;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order_details);
        dateEditText = findViewById(R.id.PlaceOrderDetailsActivity_expectEditText);
        addressCardview = findViewById(R.id.PlaceOrderDetailsActivity_address_cardview);

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

                        Intent intentA = new Intent(PlaceOrderDetailsActivity.this, AddressViewActivity.class);
                        intentA.putExtra("addressChage","111");
                        startActivity(intentA);
                        dialog.dismiss();

                    }
                });

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentA = new Intent(PlaceOrderDetailsActivity.this, AddressViewActivity.class);
                        intentA.putExtra("addressChage","111");
                        startActivity(intentA);
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