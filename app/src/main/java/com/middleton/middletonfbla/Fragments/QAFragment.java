package com.middleton.middletonfbla.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.middleton.middletonfbla.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QAFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QAFragment extends Fragment {
    CardView cv1, cv2,cv3,cv4,cv5,cv6,cv7;
    TextView text;
    ExpandableRelativeLayout layout1, layout2, layout3,layout4,layout5,layout6,layout7;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QAFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QAFragment newInstance(String param1, String param2) {
        QAFragment fragment = new QAFragment();
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

        cv1 = view.findViewById(R.id.cv1);
        cv2 = view.findViewById(R.id.cv2);
        cv3 = view.findViewById(R.id.cv3);
        cv4 = view.findViewById(R.id.cv4);
        cv5 = view.findViewById(R.id.cv5);
        cv6 = view.findViewById(R.id.cv6);
        cv7 = view.findViewById(R.id.cv7);

        TextView text = view.findViewById(R.id.text);

        Spanned Text = Html.fromHtml("To view the FBLA Dress Code, please visit " +
                "<a href='https://www.fbla-pbl.org/cmh/dress-code/'>https://www.fbla-pbl.org/cmh/dress-code/</a>"+
                ". This dress code will be enforced during all conferences and events, unless otherwise noted.");

        text.setMovementMethod(LinkMovementMethod.getInstance());
        text.setText(Text);

        layout1 = view.findViewById(R.id.expandableLayout1);
        layout2 = view.findViewById(R.id.expandableLayout2);
        layout3 = view.findViewById(R.id.expandableLayout3);
        layout4 = view.findViewById(R.id.expandableLayout4);
        layout5 = view.findViewById(R.id.expandableLayout5);
        layout6 = view.findViewById(R.id.expandableLayout6);
        layout7 = view.findViewById(R.id.expandableLayout7);

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout1.toggle();
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout2.toggle();
            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout3.toggle();
            }
        });

        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout4.toggle();
            }
        });

        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout5.toggle();
            }
        });

        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout6.toggle();
            }
        });

        cv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout7.toggle();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qa, container, false);
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
