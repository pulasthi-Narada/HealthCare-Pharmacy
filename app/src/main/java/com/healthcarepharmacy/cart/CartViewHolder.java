package com.healthcarepharmacy.cart;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.healthcarepharmacy.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    TextView textName,textType,textPrice,qty;
    ImageView imageView,deleteImg;
    View v;

    public ImageView getDeleteImg() {
        return deleteImg;
    }

    public TextView getTextName() {
        return textName;
    }

    public TextView getTextType() {
        return textType;
    }

    public TextView getTextPrice() {
        return textPrice;
    }

    public TextView getQty() {
        return qty;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public View getV() {
        return v;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        textName = itemView.findViewById(R.id.cart_layout_name_txtview);
        textType = itemView.findViewById(R.id.cart_layout_type_txtview);
        textPrice = itemView.findViewById(R.id.cart_layout_price_txtview);
         qty = itemView.findViewById(R.id.cart_layout_productCount_txtview);
        imageView = itemView.findViewById(R.id.cart_layout_imgView);
        deleteImg = itemView.findViewById(R.id.cart_layout_cart_bin);


        v= itemView;
    }
}
