package com.middleton.middletonfbla.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.middleton.middletonfbla.PopUpActivity;
import com.middleton.middletonfbla.R;

public class RegisterCompetition extends AppCompatActivity implements View.OnClickListener {

    CardView objective, speaking, design, hybrid, writing;
    FloatingActionButton registerCompBtn;
    FirebaseAuth auth;
    DocumentReference documentReference;
    String competition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_competition);
        auth = FirebaseAuth.getInstance();
        documentReference = FirebaseFirestore.getInstance().collection("User_Information").document(auth.getCurrentUser().getUid());

        objective = (CardView) findViewById(R.id.objectiveCardview);
        speaking = (CardView) findViewById(R.id.speakingCardview);
        design = (CardView) findViewById(R.id.designCardview);
        hybrid = (CardView) findViewById(R.id.hybridCardview);
        writing = (CardView) findViewById(R.id.writingCardview);
        registerCompBtn = (FloatingActionButton) findViewById(R.id.registerComp);

        objective.setOnClickListener(this);
        speaking.setOnClickListener(this);
        design.setOnClickListener(this);
        hybrid.setOnClickListener(this);
        writing.setOnClickListener(this);


        registerCompBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentReference.update("competition", competition);
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v == hybrid){
            startActivity(new Intent(RegisterCompetition.this, PopUpActivity.class));
        }
        if(v == objective){
            startActivity(new Intent(RegisterCompetition.this, PopUpActivity.class));
        }
        if (v == speaking){
            startActivity(new Intent(RegisterCompetition.this, PopUpActivity.class));
        }
        if (v == design){
            startActivity(new Intent(RegisterCompetition.this, PopUpActivity.class));
        }
        if (v == writing){
            startActivity(new Intent(RegisterCompetition.this, PopUpActivity.class));
        }
    }
}
