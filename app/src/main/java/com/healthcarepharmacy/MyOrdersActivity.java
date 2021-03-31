package com.healthcarepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.healthcarepharmacy.cart.Cart;
import com.healthcarepharmacy.cart.CartViewHolder;
import com.healthcarepharmacy.orders.order;
import com.healthcarepharmacy.orders.orderViewHolder;
import com.squareup.picasso.Picasso;

public class MyOrdersActivity extends AppCompatActivity {

    FirebaseRecyclerOptions<order> options;
    FirebaseRecyclerAdapter<order, orderViewHolder> adapter;
    RecyclerView recyclerView;
    DatabaseReference myRef,myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        recyclerView = findViewById(R.id.MyOrders_layout_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        String loginNumber = sharedPref.getString("number", "0");

        myRef2 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber);

        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber).child("Order");

        options = new FirebaseRecyclerOptions.Builder<order>().setQuery(myRef, order.class).build();

        adapter = new FirebaseRecyclerAdapter<order, orderViewHolder>(options) {
            @NonNull
            @Override
            public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders,parent,false);
                return new orderViewHolder(v);

            }

            @Override
            protected void onBindViewHolder(@NonNull orderViewHolder holder, int position, @NonNull order model) {

                holder.getTextName().setText("Name: "+model.getName());
                holder.getAddress().setText("Address: "+model.getAddressText());
                holder.getTotal().setText("Total Amount:LKR "+model.getTotal());
                holder.getTime().setText("Order At: "+model.getOrderAt());
                holder.getNumber().setText("Phone Number: "+model.getPhoneNumber());

            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }
}