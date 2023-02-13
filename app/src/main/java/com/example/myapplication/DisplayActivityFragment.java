package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class DisplayActivityFragment extends Fragment {

    private static final String ARG_ProfileObj = "profile";
    private Profile profile;

    private TextView textViewDisplayNameFragment, textViewDisplayEmailFragment;
    private ImageView imageViewDisplayAvatarFragment;


    public DisplayActivityFragment() {
        // Required empty public constructor
    }

    public static DisplayActivityFragment newInstance(Profile profile) {
        DisplayActivityFragment fragment = new DisplayActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ProfileObj, profile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            profile = getArguments().getParcelable(ARG_ProfileObj);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Display Profile");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_display_activity, container, false);
        textViewDisplayNameFragment = rootView.findViewById(R.id.textViewDisplayNameFragment);
        textViewDisplayEmailFragment = rootView.findViewById(R.id.textViewDisplayEmailFragment);
        imageViewDisplayAvatarFragment = rootView.findViewById(R.id.imageViewDisplayAvatarFragment);

        textViewDisplayNameFragment.setText(profile.getName());
        textViewDisplayEmailFragment.setText(profile.getEmail());
        imageViewDisplayAvatarFragment.setImageResource(profile.getMoodImage());

        return rootView;
    }
}