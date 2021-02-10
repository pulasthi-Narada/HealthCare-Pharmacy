package com.healthcarepharmacy.product;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.healthcarepharmacy.R;

public class ProductViewHolder  extends RecyclerView.ViewHolder {
    TextView textName,textType,textPrice;
     ImageView imageView,imagCart;


    public TextView getTextName() {
        return textName;
    }

    public TextView getTextType() {
        return textType;
    }

    public TextView getTextPrice() {
        return textPrice;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ImageView getImagCart() { return imagCart; }

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        textName = itemView.findViewById(R.id.Product_layout_name_txtview);
        textType = itemView.findViewById(R.id.Product_layout_type_txtview);
        textPrice = itemView.findViewById(R.id.Product_layout_price_txtview);
        imageView = itemView.findViewById(R.id.Product_layout_imgView);
        imagCart  = itemView.findViewById(R.id.Product_layout_cart);

    }
}
