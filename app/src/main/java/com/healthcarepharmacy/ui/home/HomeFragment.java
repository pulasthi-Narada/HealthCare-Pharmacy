package com.healthcarepharmacy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.healthcarepharmacy.CategoriesActivity;
import com.healthcarepharmacy.OffersActivity;
import com.healthcarepharmacy.R;
import com.healthcarepharmacy.product_recyclerview_Activity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageSlider imgs;
    ImageButton diabetiImgBtn,householadImgBtn;
    TextView categorySeeAll;
    LinearLayout offersLinearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

       // set Home_imgeslider object in imgs  variable
        imgs = root.findViewById(R.id.Home_imgeslider);

        // set Home_Products_see_all_txtView  object in categorySeeAll  variable
        categorySeeAll = root.findViewById(R.id.Home_Products_see_all_txtView);

        // set Home_diabetic_care_imgBtn  object in diabetiImgBtn  variable
        diabetiImgBtn = root.findViewById(R.id.Home_diabetic_care_imgBtn);

        // set Home_householad_imgBtn  object in householadImgBtn  variable
        householadImgBtn = root.findViewById(R.id.Home_householad_imgBtn);

        // set Offers_linear_layout  object in offersLinearLayout  variable
        offersLinearLayout = root.findViewById(R.id.Offers_linear_layout);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                //image slider method
                setImageSlider();
                //  offers method
                goToOffers();
                // shop by category See All method
               categorySeeAll();

                // go to Diabetic Care category
               goToDiabeticCare();

                // go to house holad  category
                goToHouseholad();




            }
        });


        return root;

    }

    private void goToHouseholad() {
        householadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start product_recyclerview_Activity Activity
                Intent intent = new Intent(getActivity(), product_recyclerview_Activity.class);

                // start put Extra  data to Activity
                intent.putExtra("categorie","household");
                startActivity(intent);
            }
        });
    }

    private void goToDiabeticCare() {
        diabetiImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start product_recyclerview_Activity Activity
                Intent intent = new Intent(getActivity(), product_recyclerview_Activity.class);

                // start put Extra  data to Activity
                intent.putExtra("categorie","diabetic");
                startActivity(intent);
            }
        });

    }

    private void categorySeeAll() {
        // this is category See All txtview   click kListener
        categorySeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start Categories Activity
                Intent intent = new Intent(getActivity(), CategoriesActivity.class);
                startActivity(intent);

            }
        });


    }

    private void goToOffers() {
         // this is offers layout click kListener
        offersLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start Offers Activity
                Intent intent = new Intent(getActivity(), OffersActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setImageSlider() {


        List<SlideModel> slideModels = new ArrayList<>();
        // set image url foor image sliders
        slideModels.add(new SlideModel("https://raw.githubusercontent.com/HealthCare-Pharmacy/images/main/HealthCare%20Pharmacy%20logo.png"));
        slideModels.add(new SlideModel("https://github.com/HealthCare-Pharmacy/images/blob/main/Best-online-Medicine-Delivery-App.jpg?raw=true"));
        slideModels.add(new SlideModel("https://github.com/HealthCare-Pharmacy/images/blob/main/addd4.jpg?raw=true"));
        slideModels.add(new SlideModel("https://github.com/HealthCare-Pharmacy/images/blob/main/_20201225_214131.JPG?raw=true"));

        // set image in   image sliders
        imgs.setImageList(slideModels,true);
    }
}