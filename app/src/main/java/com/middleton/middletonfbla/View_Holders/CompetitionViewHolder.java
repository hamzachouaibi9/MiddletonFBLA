package com.middleton.middletonfbla.View_Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.middleton.middletonfbla.R;

public class CompetitionViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView name;

    public CompetitionViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        name = view.findViewById(R.id.compName);
    }

    public void setName(String getName){
        name.setText(getName);
    }



}
