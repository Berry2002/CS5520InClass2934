package com.example.myapplication.Interfaces;

import com.google.firebase.auth.FirebaseUser;
import com.example.myapplication.Models.User;
import com.example.myapplication.Models.ChatRecord;

import java.util.ArrayList;

public interface IconnectToActivity {
    void populateMainFragment(FirebaseUser mUser);
    void populateRegisterFragment();
    void registerDone(FirebaseUser mUser, User user);
    void logoutPressed();
    void newMessageButtonPressedFromMainFragment(ArrayList<User> users);

    void onFriendSelectedFromSelectFriendFragment(User user);

    void onChatSelectedFromRecentChats(ChatRecord record);
}
