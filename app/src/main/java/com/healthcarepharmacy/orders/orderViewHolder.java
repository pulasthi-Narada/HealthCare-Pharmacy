package com.healthcarepharmacy.orders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.healthcarepharmacy.R;

public class orderViewHolder extends RecyclerView.ViewHolder{

    TextView textName,number,total,address,time;
    View v;

    public TextView getTextName() {
        return textName;
    }

    public TextView getNumber() {
        return number;
    }

    public TextView getTotal() {
        return total;
    }

    public TextView getAddress() {
        return address;
    }

    public TextView getTime() {
        return time;
    }

    public View getV() {
        return v;
    }

    public orderViewHolder(@NonNull View itemView) {
        super(itemView);

        textName = itemView.findViewById(R.id.orders_layout_textView_name);
        number = itemView.findViewById(R.id.orders_layout_textView_number);
        total = itemView.findViewById(R.id.orders_layout_textView_total);
        address = itemView.findViewById(R.id.orders_layout_textView_address);
        time = itemView.findViewById(R.id.orders_layout_textView_time);


        v= itemView;
    }
}
