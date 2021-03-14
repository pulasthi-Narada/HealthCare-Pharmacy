package com.healthcarepharmacy.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.healthcarepharmacy.AddressDetailsActivity;
import com.healthcarepharmacy.MyAddressActivity;
import com.healthcarepharmacy.ProductDetailsActivity;
import com.healthcarepharmacy.R;
import com.healthcarepharmacy.product.Product;
import com.healthcarepharmacy.product.ProductViewHolder;
import com.healthcarepharmacy.product_recyclerview_Activity;
import com.squareup.picasso.Picasso;

public class AddressViewActivity extends AppCompatActivity {
    FirebaseRecyclerOptions<Address> options;
    FirebaseRecyclerAdapter<Address, AddressViewHolder> adapter;
    RecyclerView recyclerView;

    DatabaseReference myRef;
    TextView addNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_view);

        recyclerView = findViewById(R.id.AddressViewActivity_layout_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        addNew = findViewById(R.id.all_address_layout_textView_add);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressViewActivity.this,  AddressDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        String loginNumber = sharedPref.getString("number","0");

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber).child("Address");

        options = new  FirebaseRecyclerOptions.Builder<Address>().setQuery(myRef, Address.class).build();
        adapter= new FirebaseRecyclerAdapter<Address, AddressViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AddressViewHolder holder, int position, @NonNull Address model) {
               holder.getName().setText(model.getName());
               holder.getAddress().setText(model.getAddress());
               holder.getCity().setText(model.getCity());
               holder.getProvince().setText(model.getProvince());
               holder.getNumber().setText(model.getNumber());
               holder.getMail().setText(model.getMail());

                // set On Click Listener to  product recyclerview
//                holder.getV().setOnClickListener(new View.OnClickListener() {
//                    //if  product recyclerview item click onClick() method work
//                    @Override
//                    public void onClick(View v) {
//                        // start ProductDetailsActivity Activity
//                        Intent intentt = new Intent(AddressViewActivity.this, .class);
//                        // pass product key to ProductDetailsActivity (pass selected  Product item to ProductDetailsActivity)
//                        intentt.putExtra("productkey",getRef(position).getKey());
//
//                        // pass selected product type to ProductDetailsActivity (pass selected  Product type to ProductDetailsActivity)
//
//                        intentt.putExtra("cate",intent.getStringExtra("categorie"));
//                        startActivity(intentt);
//                    }
//                });

            }

            @NonNull
            @Override
            public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_address,parent,false);

                return new AddressViewHolder(v);

            }
        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);



    }
}