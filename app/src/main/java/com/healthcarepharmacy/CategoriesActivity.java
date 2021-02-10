package com.healthcarepharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CategoriesActivity extends AppCompatActivity {

ImageView diabeticImg,householdImg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        diabeticImg = findViewById(R.id.categories_diabetic_care_imagView);
        householdImg = findViewById(R.id.categories_household_imagView);

        diabeticImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CategoriesActivity.this, product_recyclerview_Activity.class);
                intent.putExtra("categorie","diabetic");
                startActivity(intent);
            }
        });

        householdImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CategoriesActivity.this, product_recyclerview_Activity.class);
                intent.putExtra("categorie","household");
                startActivity(intent);
            }
        });

    }
}