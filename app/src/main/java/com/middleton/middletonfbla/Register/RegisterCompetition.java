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
import com.middleton.middletonfbla.MainActivity;
import com.middleton.middletonfbla.R;

public class RegisterCompetition extends AppCompatActivity implements View.OnClickListener {

    CardView objective, speaking, design, hybrid, writing;
    FloatingActionButton registerCompBtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_competition);
        auth = FirebaseAuth.getInstance();
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
                startActivity(new Intent(RegisterCompetition.this, MainActivity.class));
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v == hybrid){
            Intent intent = new Intent(this, PopUpActivity.class);
            intent.putExtra("data", "hybrid");
            startActivity(intent);
        }
        if(v == objective){
            Intent intent = new Intent(this, PopUpActivity.class);
            intent.putExtra("data", "objective");
            startActivity(intent);
        }
        if (v == speaking){
            Intent intent = new Intent(this, PopUpActivity.class);
            intent.putExtra("data", "speaking");
            startActivity(intent);
        }
        if (v == design){
            Intent intent = new Intent(this, PopUpActivity.class);
            intent.putExtra("data", "design");
            startActivity(intent);
        }
        if (v == writing){
            Intent intent = new Intent(this, PopUpActivity.class);
            intent.putExtra("data", "writing");
            startActivity(intent);
        }
    }
}
