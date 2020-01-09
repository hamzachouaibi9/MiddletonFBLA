package com.middleton.middletonfbla.View_Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.middleton.middletonfbla.R;
import com.squareup.picasso.Picasso;

public class OfficerViewHolder extends RecyclerView.ViewHolder {
    private View view;
    TextView name;
    ImageView pic;
    TextView pos;

    public OfficerViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        name = view.findViewById(R.id.officerName);
        pic = view.findViewById(R.id.officerPicture);
        pos = view.findViewById(R.id.officerPosition);
    }

    public void setName(String getName) {
        name.setText(getName);
    }

    public void setPicture(String picture) {
        Picasso.get().load(picture).into(pic);
    }

    public void setPosition(String position) {
        pos.setText(position);
    }
}
