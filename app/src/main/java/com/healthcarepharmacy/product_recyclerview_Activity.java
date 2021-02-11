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


        myRef = FirebaseDatabase.getInstance().getReference().child("product").child(intent.getStringExtra("categorie"));

        options = new  FirebaseRecyclerOptions.Builder<Product>().setQuery(myRef, Product.class).build();
        adapter= new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {

                holder.getTextName().setText(model.getName());
                holder.getTextType().setText("Type: "+model.getType());
                holder.getTextPrice().setText("LKR "+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.getImageView());
                holder.getImagCart().setImageResource(R.mipmap.ic_pro_cart_foreground);

                // set On Click Listener to  product recyclerview
                holder.getV().setOnClickListener(new View.OnClickListener() {
                    //if  product recyclerview item click onClick() method work
                    @Override
                    public void onClick(View v) {
                        // start ProductDetailsActivity Activity
                        Intent intentt = new Intent(product_recyclerview_Activity.this, ProductDetailsActivity.class);
                        // pass product key to ProductDetailsActivity (pass selected  Product item to ProductDetailsActivity)
                        intentt.putExtra("productkey",getRef(position).getKey());

                        // pass selected product type to ProductDetailsActivity (pass selected  Product type to ProductDetailsActivity)

                        intentt.putExtra("cate",intent.getStringExtra("categorie"));
                        startActivity(intentt);
                    }
                });

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