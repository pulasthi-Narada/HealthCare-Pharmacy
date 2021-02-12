package com.healthcarepharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class UploadPrescriptionActivity extends AppCompatActivity {
ImageView imgSamplePre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_prescription);
        imgSamplePre = findViewById(R.id.Upload_prescription_layout_imagsample);
        //load sample prescription photo to image View
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/prescription.png?alt=media&token=d53eee21-6e50-4dfe-921c-68544b6e3838").into(imgSamplePre);


    }
}