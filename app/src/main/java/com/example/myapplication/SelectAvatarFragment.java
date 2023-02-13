package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SelectAvatarFragment extends Fragment {

    private ImageView imageView1Fragment;
    private ImageView imageView2Fragment;
    private ImageView imageView3Fragment;
    private ImageView imageView4Fragment;
    private ImageView imageView5Fragment;
    private ImageView imageView6Fragment;

    private int drawableID;

    private InterfaceToInClass03Activity fromSelectAvatarToActivity;

    public SelectAvatarFragment() {
        // Required empty public constructor
    }

    public static SelectAvatarFragment newInstance() {
        SelectAvatarFragment fragment = new SelectAvatarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Select Avatar");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select_avatar, container, false);
        imageView1Fragment = rootView.findViewById(R.id.imageView1Fragment);
        imageView2Fragment = rootView.findViewById(R.id.imageView2Fragment);
        imageView3Fragment = rootView.findViewById(R.id.imageView3Fragment);
        imageView4Fragment = rootView.findViewById(R.id.imageView4Fragment);
        imageView5Fragment = rootView.findViewById(R.id.imageView5Fragment);
        imageView6Fragment = rootView.findViewById(R.id.imageView6Fragment);

        imageView1Fragment.setOnClickListener(this::clickOnAvatar1);
        imageView2Fragment.setOnClickListener(this::clickOnAvatar2);
        imageView3Fragment.setOnClickListener(this::clickOnAvatar3);
        imageView4Fragment.setOnClickListener(this::clickOnAvatar4);
        imageView5Fragment.setOnClickListener(this::clickOnAvatar5);
        imageView6Fragment.setOnClickListener(this::clickOnAvatar6);
        return rootView;
    }

    private void clickOnAvatar1(View view) {
        fromSelectAvatarToActivity.avatarClickedInSelectAvatar(R.drawable.avatar_f_1);
    }
    private void clickOnAvatar2(View view) {
        fromSelectAvatarToActivity.avatarClickedInSelectAvatar(R.drawable.avatar_m_3);
    }
    private void clickOnAvatar3(View view) {
        fromSelectAvatarToActivity.avatarClickedInSelectAvatar(R.drawable.avatar_f_2);
    }
    private void clickOnAvatar4(View view) {
        fromSelectAvatarToActivity.avatarClickedInSelectAvatar(R.drawable.avatar_m_2);
    }
    private void clickOnAvatar5(View view) {
        fromSelectAvatarToActivity.avatarClickedInSelectAvatar(R.drawable.avatar_f_3);
    }
    private void clickOnAvatar6(View view) {
        fromSelectAvatarToActivity.avatarClickedInSelectAvatar(R.drawable.avatar_m_1);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceToInClass03Activity){
            fromSelectAvatarToActivity = (InterfaceToInClass03Activity) context;
        } else{
            throw new RuntimeException(context.toString()
                    + "must implement InterfaceFromSelectAvatarToActivity");
        }
    }

}