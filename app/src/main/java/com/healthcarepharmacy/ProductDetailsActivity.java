package com.healthcarepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {
    TextView typeText,nameText,priceText,countText;
    ImageView imag;
    DatabaseReference ref;
    ImageButton addImgBtn,removeImagBtn;
    Button addCart;
    int quantity=1;
    String productKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        typeText = findViewById(R.id.ProductDetails_layout_productTypeAns_txtview);
        priceText = findViewById(R.id.ProductDetails_layout_price_txtview);
        nameText = findViewById(R.id.ProductDetails_layout_name_txtview);
        imag = findViewById(R.id.ProductDetails_layout_imgView);
        addImgBtn = findViewById(R.id.ProductDetails_layout_add_imgBtn);
        removeImagBtn = findViewById(R.id.ProductDetails_layout_productRemove_imgBtn);
        countText = findViewById(R.id.ProductDetails_layout_productCount_txtview);

        addCart = findViewById(R.id.ProductDetails_layout_addCart_imgBtn);


        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        // add quantity
        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                countText.setText(Integer.toString(quantity));
            }
        });

        // decrease  quantity
        removeImagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(quantity!=1) {
                    quantity--;
                }
                countText.setText(Integer.toString(quantity));

            }
        });



        // get product Key from  from intent (get selected product key from ProductDetailsActivity).
         productKey =  getIntent().getStringExtra("productkey");
        // get Selected product type from  intent (get selected product type from ProductDetailsActivity).
        String productType = getIntent().getStringExtra("cate");
//        Toast.makeText(getApplicationContext(),getIntent().getStringExtra("cate"),Toast.LENGTH_SHORT).show();

        // database Reference
        ref = FirebaseDatabase.getInstance().getReference().child("product").child(productType);

        // pass selected product key and add Value Event Listener for that product
        ref.child(productKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // if data is available
                if(snapshot.exists()){

                    // set selected product details to text views and image view in ProductDetailsActivity Activity
                    nameText.setText(snapshot.child("name").getValue().toString());
                    priceText.setText("LKR "+snapshot.child("price").getValue().toString());
                    typeText.setText(snapshot.child("type").getValue().toString());
                    // load image url using  Picasso library
                    Picasso.get().load(snapshot.child("image").getValue().toString()).into(imag);

                }else {
                    Toast.makeText(getApplicationContext(),"No Details About this Product",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });





    }

    private void addToCart() {

        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);
        String loginNumber = sharedPref.getString("number","0");



        DatabaseReference addCartRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(loginNumber).child("Cart").child(productKey);

        addCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    int qty = Integer.parseInt(snapshot.child("qty").getValue().toString());
                    addCartRef.child("qty").setValue( Integer.toString(qty+quantity));
                    Toast.makeText(ProductDetailsActivity.this, "Product added to cart successfully!.", Toast.LENGTH_SHORT).show();

                }else {

                    ref.child(productKey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            addCartRef.child("qty").setValue(Integer.toString(quantity));
                            addCartRef.child("type").setValue(snapshot.child("type").getValue().toString());
                            addCartRef.child("price").setValue(snapshot.child("price").getValue().toString());
                            addCartRef.child("name").setValue(snapshot.child("name").getValue().toString());
                            addCartRef.child("image").setValue(snapshot.child("image").getValue().toString());
                            Toast.makeText(ProductDetailsActivity.this, "Product added to cart successfully!.", Toast.LENGTH_SHORT).show();






                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }


                    });



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}