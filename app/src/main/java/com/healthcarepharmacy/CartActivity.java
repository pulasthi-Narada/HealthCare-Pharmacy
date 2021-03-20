package com.healthcarepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.healthcarepharmacy.address.AddressViewActivity;
import com.healthcarepharmacy.cart.Cart;
import com.healthcarepharmacy.cart.CartViewHolder;
import com.healthcarepharmacy.product.Product;
import com.healthcarepharmacy.product.ProductViewHolder;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    FirebaseRecyclerOptions<Cart> options;
    FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter;
    RecyclerView recyclerView;
    double price=0;
    TextView mrp,payble;
    Dialog dialog;
    Button placeorder;
    DatabaseReference myRef,myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.CartActivity_layout_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        mrp = findViewById(R.id.CartActivity_mrp_value);
        payble = findViewById(R.id.CartActivity_total_value);
        placeorder = findViewById(R.id.CartActivity_place_order_btn);

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        String loginNumber = sharedPref.getString("number","0");

        myRef2 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber);

        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber).child("Cart");

        options = new  FirebaseRecyclerOptions.Builder<Cart>().setQuery(myRef, Cart.class).build();

        adapter= new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                holder.getTextName().setText(model.getName());
                holder.getTextType().setText("Type: "+model.getType());
                holder.getTextPrice().setText("LKR "+model.getPrice());
                holder.getQty().setText(model.getQty());
               Picasso.get().load(model.getImage()).into(holder.getImageView());
                holder.getDeleteImg().setImageResource(R.drawable.ic_delete_bin);


                holder.getV().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        dialog = new Dialog(CartActivity.this);
                        dialog.setContentView(R.layout.cart_edit_dailog_box);
                        // dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.daillog_background));
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(true);
                        dialog.show();

                        ImageButton add = dialog.findViewById(R.id.cart_dilog_add_imgBtn);
                        ImageView  delete = dialog.findViewById(R.id.cart_dilog_delete_imgview);
                        ImageButton remove = dialog.findViewById(R.id.cart_dilog_productRemove_imgBtn);


                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {



                            }
                        });

                        remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {



                            }
                        });

                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();

                            }
                        });






                        return true;
                    }
                });




            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if(snapshot.child("Cart").exists()) {

                            for (DataSnapshot snapshot2 : snapshot.child("Cart").getChildren()) {



                                if(!(snapshot2.child("price")==null || snapshot2.child("price").getValue()==null)) {

                                     price = price + Double.parseDouble(snapshot2.child("price").getValue().toString())*Integer.parseInt(snapshot2.child("qty").getValue().toString());
                                    //Log.i("Result", snapshot2.child("price").getValue().toString()+" "+snapshot2.child("qty").getValue().toString());

                                    mrp.setText(Double.toString(price));
                                    payble.setText(Double.toString(price));




                                }else {
//                                    mrp.setText("0");
//                                    payble.setText("0");
                                }
                            }


//
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart,parent,false);
                return new CartViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);



        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, PlaceOrderDetailsActivity.class);
                startActivity(intent);

            }
        });






    }



}