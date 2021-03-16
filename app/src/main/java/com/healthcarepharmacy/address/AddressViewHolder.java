package com.healthcarepharmacy.address;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.healthcarepharmacy.LoginActivity;
import com.healthcarepharmacy.R;

public class AddressViewHolder extends RecyclerView.ViewHolder {
    TextView name,number,mail,address,city,province,edit,remove;
    Button editBtn,removeBtn;
    View v;



    public TextView getName() {
        return name;
    }

    public TextView getNumber() {
        return number;
    }

    public TextView getMail() {
        return mail;
    }

    public TextView getAddress() {
        return address;
    }

    public TextView getCity() {
        return city;
    }

    public TextView getProvince() {
        return province;
    }

    public Button getEditBtn() {
        return editBtn;
    }

    public Button getRemoveBtn() {
        return removeBtn;
    }

    public TextView getEdit() {
        return edit;
    }

    public TextView getRemove() {
        return remove;
    }

    public View getV() {
        return v;
    }

    public AddressViewHolder(@NonNull View itemView ) {
        super(itemView);

        name = itemView.findViewById(R.id.all_address_layout_textView_name);
        number = itemView.findViewById(R.id.all_address_layout_textView_number);
        mail = itemView.findViewById(R.id.all_address_layout_textView_mail);
        address = itemView.findViewById(R.id.all_address_layout_textView_address);
        city = itemView.findViewById(R.id.all_address_layout_textView_city);
        province = itemView.findViewById(R.id.all_address_layout_textView_pro);
        edit = itemView.findViewById(R.id.all_address_layout_edit_txt);
        remove = itemView.findViewById(R.id.all_address_layout_remove_txt);

        v= itemView;






    }
}
