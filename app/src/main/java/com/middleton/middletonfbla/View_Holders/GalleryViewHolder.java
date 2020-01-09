package com.middleton.middletonfbla.View_Holders;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.middleton.middletonfbla.R;
import com.squareup.picasso.Picasso;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    View view;
    ImageView image;
    TextView name;

    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        name = view.findViewById(R.id.nameGallery);
        image = view.findViewById(R.id.ImgGallery);
    }

    public void setName(String getName){
        name.setText(getName);
    }

    public void setImage(String getImage){
        Picasso.get().load(getImage).into(image);
    }


}
