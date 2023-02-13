package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {
    private String name, email;
    private Profile profile;
    private int avatarDrawable;

    private EditText editTextNameFragment, editTextEmailFragment;
    private ImageView imageViewAvatarFragment;
    private Button buttonSubmitFragment;

    private InterfaceToInClass03Activity fromEditToActivity;
    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profile = new Profile();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Edit Profile");
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_edit_profile, container, false);

        editTextNameFragment = rootView.findViewById(R.id.editTextNameFragment);
        editTextEmailFragment = rootView.findViewById(R.id.editTextEmailFragment);
        imageViewAvatarFragment = rootView.findViewById(R.id.imageViewAvatarFragment);
        buttonSubmitFragment = rootView.findViewById(R.id.buttonSubmitFragment);

        imageViewAvatarFragment.setOnClickListener(this::onSelectAvatarClicked);
        buttonSubmitFragment.setOnClickListener(this::onSubmitButtonClicked);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            Bundle receivedData = getArguments();

            if (receivedData.containsKey(Tags.TAG_ARG_DRAWABLW_ID)) {
                avatarDrawable = receivedData.getInt(Tags.TAG_ARG_DRAWABLW_ID);
                imageViewAvatarFragment.setImageResource(avatarDrawable);
            }
        }
    }

    private void onSubmitButtonClicked(View view) {
        // check for name & email
        name = String.valueOf(editTextNameFragment.getText());
        email = String.valueOf(editTextEmailFragment.getText());

        profile.setName(name);
        profile.setEmail(email);
        profile.setAvatar(avatarDrawable);

        fromEditToActivity.submitButtonClickedInEditProfile(profile);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceToInClass03Activity) {
            fromEditToActivity = (InterfaceToInClass03Activity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement InterfaceToInClass03Activity");
        }
    }
    private void onSelectAvatarClicked(View view) {
        fromEditToActivity.selectAvatarClickedInEditProfile();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}