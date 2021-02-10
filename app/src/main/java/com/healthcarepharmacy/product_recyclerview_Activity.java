package com.healthcarepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.healthcarepharmacy.product.Product;
import com.healthcarepharmacy.product.ProductViewHolder;
import com.squareup.picasso.Picasso;

public class product_recyclerview_Activity extends AppCompatActivity {
    FirebaseRecyclerOptions<Product> options;
    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;
    RecyclerView recyclerView;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_recyclerview_);
        recyclerView = findViewById(R.id.product_recyclerview_layout_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));



        myRef = FirebaseDatabase.getInstance().getReference().child("product");

        Intent intent = getIntent();

        if(intent.getStringExtra("categorie").equals("diabetic")){
            myRef = FirebaseDatabase.getInstance().getReference().child("product").child("Diabetic_Care");

        } else if(intent.getStringExtra("categorie").equals("household")) {
            myRef = FirebaseDatabase.getInstance().getReference().child("product").child("Household_Remedies");

        }
        options = new  FirebaseRecyclerOptions.Builder<Product>().setQuery(myRef, Product.class).build();
        adapter= new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {

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