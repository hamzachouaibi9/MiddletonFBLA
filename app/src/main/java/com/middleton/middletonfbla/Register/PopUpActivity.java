package com.middleton.middletonfbla.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.middleton.middletonfbla.MainActivity;
import com.middleton.middletonfbla.R;
import com.middleton.middletonfbla.View_Holders.CompetitionViewHolder;
import com.middleton.middletonfbla.View_Holders.OfficerViewHolder;
import com.middleton.middletonfbla.models.CompetitionModel;
import com.middleton.middletonfbla.models.OfficerModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class PopUpActivity extends AppCompatActivity {

    RecyclerView competitionList;
    Query query;
    String data;
    CollectionReference database;
    FirebaseAuth auth;
    FirestoreRecyclerAdapter<CompetitionModel, CompetitionViewHolder> adapter;
    int index = -1;
    CircularProgressButton continueBtn;
    ImageView exit;
    Bitmap icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance().collection("competitions");
        data = getIntent().getExtras().getString("data");
        query = database.whereEqualTo("type", data);
        continueBtn = findViewById(R.id.continueBtn);
        exit = findViewById(R.id.exitBtn);

        icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_check_solid);

        continueBtn.setVisibility(View.INVISIBLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .85), (int)(height * .79));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -25;

        getWindow().setAttributes(params);


        competitionList = findViewById(R.id.competitionList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        competitionList.setLayoutManager(llm);
        competitionList.setClickable(true);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        FirestoreRecyclerOptions<CompetitionModel> options = new FirestoreRecyclerOptions.Builder<CompetitionModel>()
                .setQuery(query, CompetitionModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<CompetitionModel, CompetitionViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CompetitionViewHolder competition, final int position, @NonNull final CompetitionModel data) {
                competition.setName(data.getName());

                competition.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        index = position;
                        notifyDataSetChanged();
                        continueBtn.setVisibility(View.VISIBLE);
                    }
                });

                if(index==position){
                    competition.itemView.setBackgroundColor(Color.parseColor("#000000"));
                }else{
                    competition.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }

                continueBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        continueBtn.startAnimation();
                        DocumentReference userData = FirebaseFirestore.getInstance().collection("User_Information").document(auth.getCurrentUser().getUid());
                        userData.update("competition", data.getName()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(PopUpActivity.this, MainActivity.class));
                                    }
                                }, 1000);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                continueBtn.revertAnimation();
                            }
                        });
                    }
                });

            }

            @NonNull
            @Override
            public CompetitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter, parent, false);
                return new CompetitionViewHolder(view);
            }
        };
        competitionList.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }


}


