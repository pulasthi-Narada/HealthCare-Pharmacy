package com.healthcarepharmacy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageSlider imgs;
    TextView categorySeeAll;
    LinearLayout offersLinearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);

        imgs = root.findViewById(R.id.Home_imgeslider);

        categorySeeAll = root.findViewById(R.id.Home_Products_see_all_txtView);


        offersLinearLayout = root.findViewById(R.id.Offers_linear_layout);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);

                //image slider method
                setImageSlider();
                //  offers method
                goToOffers();
                // shop by category See All method
               categorySeeAll();




            }
        });


        return root;

    }

    private void categorySeeAll() {

        categorySeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                Intent intent = new Intent(getActivity(), OffersActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setImageSlider() {

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://raw.githubusercontent.com/HealthCare-Pharmacy/images/main/HealthCare%20Pharmacy%20logo.png"));
        slideModels.add(new SlideModel("https://github.com/HealthCare-Pharmacy/images/blob/main/Best-online-Medicine-Delivery-App.jpg?raw=true"));
        slideModels.add(new SlideModel("https://github.com/HealthCare-Pharmacy/images/blob/main/addd4.jpg?raw=true"));
        slideModels.add(new SlideModel("https://github.com/HealthCare-Pharmacy/images/blob/main/_20201225_214131.JPG?raw=true"));


        imgs.setImageList(slideModels,true);
    }
}