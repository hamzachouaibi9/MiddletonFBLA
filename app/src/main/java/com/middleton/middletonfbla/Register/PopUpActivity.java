package com.middleton.middletonfbla.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.middleton.middletonfbla.R;
import com.middleton.middletonfbla.View_Holders.CompetitionViewHolder;
import com.middleton.middletonfbla.View_Holders.OfficerViewHolder;
import com.middleton.middletonfbla.models.CompetitionModel;
import com.middleton.middletonfbla.models.OfficerModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopUpActivity extends AppCompatActivity {

    RecyclerView competitionList;
    Query query;
    String data;
    CollectionReference database;
    FirebaseAuth auth;
    FirestoreRecyclerAdapter<CompetitionModel, CompetitionViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance().collection("competitions");
        data = getIntent().getExtras().getString("data");
        query = database.whereEqualTo("type", data);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .85), (int)(height * .75));

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
                        DocumentReference userData = FirebaseFirestore.getInstance().collection("User_Information").document(auth.getCurrentUser().getUid());
                        userData.update("competition", data.getName());
                        finish();
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


