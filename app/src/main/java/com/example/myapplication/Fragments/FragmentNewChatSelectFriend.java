package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Models.Message;
import com.example.myapplication.Models.User;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerAdapters.FriendsAdapter;
import com.example.myapplication.RecyclerAdapters.MessagesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
public class FragmentNewChatSelectFriend extends Fragment {
    private static final String ARG_USERS = "users";
    private static final String ARG_CURUSER = "mUser";
    private ArrayList<User> users;
    private User currentUser;
    private RecyclerView recyclerViewFriends;
    private RecyclerView.LayoutManager recyclerViewFriendsLayoutManager;
    private FriendsAdapter friendsAdapter;

    public FragmentNewChatSelectFriend() {
        // Required empty public constructor
    }
    public FragmentNewChatSelectFriend(ArrayList<User> users, User currentUser) {
        // Required empty public constructor
        this.users = users;
        this.currentUser =  currentUser;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_chat_select_friend, container, false);
        // remove the currentUser from users array list...
        users.remove(currentUser);
        recyclerViewFriends = rootView.findViewById(R.id.recyclerViewFriends);
        recyclerViewFriendsLayoutManager = new LinearLayoutManager(getContext());
        friendsAdapter = new FriendsAdapter(users,getContext());
        recyclerViewFriends.setLayoutManager(recyclerViewFriendsLayoutManager);
        recyclerViewFriends.setAdapter(friendsAdapter);
        return rootView;
    }
}