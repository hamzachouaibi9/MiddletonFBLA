package com.middleton.middletonfbla.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.middleton.middletonfbla.R;
import com.middleton.middletonfbla.View_Holders.EventViewHolder;
import com.middleton.middletonfbla.View_Holders.OfficerViewHolder;
import com.middleton.middletonfbla.models.EventModel;
import com.middleton.middletonfbla.models.OfficerModel;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {
    CollectionReference database;
    FirestoreRecyclerAdapter<EventModel, EventViewHolder> volunteerAdapter;
    FirestoreRecyclerAdapter<EventModel, EventViewHolder> conferenceAdapter;
    RecyclerView volunteerList;
    RecyclerView conferenceList;
    Query queryVolunteer;
    Query queryConference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsFragment newInstance(String param1, String param2) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        volunteerList = view.findViewById(R.id.volunteerList);
        conferenceList = view.findViewById(R.id.conferenceList);
        database = firestore.collection("events");
        queryVolunteer = database.whereEqualTo("type", "volunteer");
        queryConference = database.whereEqualTo("type", "conference");
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        volunteerList.setLayoutManager(llm);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        conferenceList.setLayoutManager(lm);

    }


    @Override
    public void onStart() {
        super.onStart();

        FirestoreRecyclerOptions<EventModel> volunteer = new FirestoreRecyclerOptions.Builder<EventModel>()
                .setQuery(queryVolunteer, EventModel.class)
                .build();

        volunteerAdapter = new FirestoreRecyclerAdapter<EventModel, EventViewHolder>(volunteer) {
            @Override
            protected void onBindViewHolder(@NonNull EventViewHolder holder, int i, @NonNull final EventModel data) {
                holder.setName(data.getName());
                holder.setAddress("Location: "+data.getAddress());
                holder.setDate("Date: "+data.getDate());
                holder.setImage(data.getPicture());
                holder.register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("You have signed up for " + data.getName() + ". On " + data.getDate() + ". At " + data.getAddress());
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
                return new EventViewHolder(view);
            }
        };

        volunteerList.setAdapter(volunteerAdapter);
        volunteerAdapter.startListening();

        FirestoreRecyclerOptions<EventModel> conference = new FirestoreRecyclerOptions.Builder<EventModel>()
                .setQuery(queryConference, EventModel.class)
                .build();

        conferenceAdapter = new FirestoreRecyclerAdapter<EventModel, EventViewHolder>(conference) {
            @Override
            protected void onBindViewHolder(@NonNull EventViewHolder holder, int i, @NonNull final EventModel data) {
                holder.setName(data.getName());
                holder.setAddress("Location: "+data.getAddress());
                holder.setDate("Date: "+data.getDate());
                holder.setImage(data.getPicture());
                holder.register.setVisibility(View.INVISIBLE);
            }

            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
                return new EventViewHolder(view);
            }
        };
        conferenceList.setAdapter(conferenceAdapter);
        conferenceAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (volunteerAdapter != null) {
            volunteerAdapter.stopListening();
        }
        if (conferenceAdapter != null){
            conferenceAdapter.stopListening();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
