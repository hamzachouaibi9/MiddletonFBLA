package com.middleton.middletonfbla.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.middleton.middletonfbla.R;

public class RegisterCompetition extends AppCompatActivity implements View.OnClickListener {

    CardView objective, speaking, design, hybrid, writing;
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

        final Animation animationLeft = AnimationUtils.loadAnimation(this, R.anim.bounce_left);
        final Animation animationRight = AnimationUtils.loadAnimation(this, R.anim.bounce_right);
        speaking.startAnimation(animationRight);
        design.startAnimation(animationLeft);
        objective.startAnimation(animationRight);
        hybrid.startAnimation(animationLeft);
        writing.startAnimation(animationRight);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },900);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },1400);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },1900);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },2400);


        objective.setOnClickListener(this);
        speaking.setOnClickListener(this);
        design.setOnClickListener(this);
        hybrid.setOnClickListener(this);
        writing.setOnClickListener(this);


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
