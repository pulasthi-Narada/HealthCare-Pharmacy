package com.healthcarepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.healthcarepharmacy.product.DiabeticCare;
import com.healthcarepharmacy.product.ProductViewHolder;
import com.squareup.picasso.Picasso;

public class product_recyclerview_Activity extends AppCompatActivity {
    FirebaseRecyclerOptions<DiabeticCare> options;
    FirebaseRecyclerAdapter<DiabeticCare, ProductViewHolder> adapter;
    RecyclerView recyclerView;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_recyclerview_);
        recyclerView = findViewById(R.id.product_recyclerview_layout_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

//        String [] name = {"Diabetasol Vanilla Powder 360g","Feel Fine Insulin Pen Needles","Humapen Ergo Ii","ntus Allstar Resusable Insulin Pen","Novofine Insulin Pen Needles","Novopen 4 Insulin Reusable Pen","Optifast Vanilla Flavour","Solo Sweetener 300s","Sugar Free Natura","Vidavance Milk Powder 200g"};
//        String [] image={"https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/Diabetic_care_product%2F1597852601_16_10.png?alt=media&token=93880368-bd3b-4f78-be3f-3423fc6940c0",
//                "https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/Diabetic_care_product%2F1597850038_11_4.png?alt=media&token=c89ea45c-e782-4ba5-a24f-658970e636fd",
//                "https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/Diabetic_care_product%2F1597850075_9_9.png?alt=media&token=1748bc60-65a4-4afb-aa9c-b35568606a39",
//                "https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/Diabetic_care_product%2F1597850087_6_0.jpg?alt=media&token=4642a4df-433b-41c9-ad09-3f287bfa2af4",
//                "https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/Diabetic_care_product%2F1597850087_8_19.png?alt=media&token=f95c829c-6455-402f-9cc6-d32946c5a0fa",
//                "https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/Diabetic_care_product%2F1597850237_7_0.jpg?alt=media&token=325a304c-c635-47f9-9285-c033ddf63383",
//                "https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/Diabetic_care_product%2F1597850719_2_1.jpg?alt=media&token=9d10734f-8c6c-4de3-b015-ec9a2977cb22",
//                "https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/Diabetic_care_product%2F1597850162_21_11.png?alt=media&token=1cf157c9-ad7f-4d5d-9cc7-0d5ddd71d9ef",
//                "https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/Diabetic_care_product%2F1597850162_26_19.png?alt=media&token=ab0c4c46-809e-49aa-808a-9beca85f2989",
//                "https://firebasestorage.googleapis.com/v0/b/healthcare-pharmacy-c28f4.appspot.com/o/Diabetic_care_product%2F1597852601_14_1.jpg?alt=media&token=722f6787-8b16-4e99-8481-4a9c6c5ec518"
//        };
//        String[] price={"1646.00","26.00","26.00","1550.00","21.00","1950.00","3845.74","770.00","3156.00","1250.00"};
//        String[] type = {"Powder","Insulin Pen Needles","Insulin Pen Needles","Insulin Pen","Insulin Pen Needles","Insulin Pen","Powder","Sweetner","Sweetner","Powder"};
//        myRef = FirebaseDatabase.getInstance().getReference().child("product").child("Diabetic_Care");
//        for(int i=0;i<10;i++) {
//            String key = myRef.push().getKey();
//            myRef.child(key).child("name").setValue(name[i]);
//            myRef.child(key).child("image").setValue(image[i]);
//            myRef.child(key).child("price").setValue(price[i]);
//            myRef.child(key).child("type").setValue(type[i]);
//        }
        myRef = FirebaseDatabase.getInstance().getReference().child("r");
      //   myRef = FirebaseDatabase.getInstance().getReference().child("product").child("Diabetic_Care");

        options = new  FirebaseRecyclerOptions.Builder<DiabeticCare>().setQuery(myRef,DiabeticCare.class).build();
        adapter= new FirebaseRecyclerAdapter<DiabeticCare, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull DiabeticCare model) {

                holder.getTextName().setText(model.getName());
                holder.getTextType().setText("Type: "+model.getType());
                holder.getTextPrice().setText("LKR "+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.getImageView());
                holder.getImagCart().setImageResource(R.mipmap.ic_pro_cart_foreground);

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);

                return new ProductViewHolder(v);

            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

}