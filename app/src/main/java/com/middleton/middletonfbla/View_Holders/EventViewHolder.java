package com.middleton.middletonfbla.View_Holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.middleton.middletonfbla.R;
import com.squareup.picasso.Picasso;

public class EventViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView name,address,date;
    public Button register;
    ImageView image;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        name = view.findViewById(R.id.eventName);
        date = view.findViewById(R.id.eventDate);
        register = view.findViewById(R.id.eventRegister);
        image = view.findViewById(R.id.eventImage);
        address = view.findViewById(R.id.eventAddy);
    }

    public void setName(String getName){
        name.setText(getName);
    }

    public void setAddress(String getAddress){
        address.setText(getAddress);
    }

    public void setDate(String getDate){
        date.setText(getDate);
    }

    public void setImage(String getImage){
        Picasso.get().load(getImage).into(image);
    }


}
