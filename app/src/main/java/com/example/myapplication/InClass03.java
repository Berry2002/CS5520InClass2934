package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class InClass03 extends AppCompatActivity implements InterfaceToInClass03Activity {

//    private ConstraintLayout constraintLayoutEditProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class03);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerView, EditProfileFragment.newInstance(),
                        Tags.TAG_EDIT_PROFILE)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void selectAvatarClickedInEditProfile() {
        //Populating Select Avatar Fragment from Edit Profile Fragment.....
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, SelectAvatarFragment.newInstance(),
                        Tags.TAG_SELECT_AVATAR)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void submitButtonClickedInEditProfile(Profile profile) {
        //Creating the DisplayFragment with the profile data....
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, DisplayActivityFragment.newInstance(profile),
                        Tags.TAG_DISPLAY)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void avatarClickedInSelectAvatar(int drawableID) {
        //Popping the top fragment to load the Edit Profile from back stack.....
        getSupportFragmentManager().popBackStack();
        // Finding the Edit Profile Fragment from the restored UI stack....
        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager()
                .findFragmentByTag(Tags.TAG_EDIT_PROFILE);

        // Sending the selected avatar drawable using Fragment arguments....
        // Arguments can be retrieved onResume().....
        Bundle bundle = new Bundle();
        bundle.putInt(Tags.TAG_ARG_DRAWABLW_ID, drawableID);
        editProfileFragment.setArguments(bundle);
    }

    //    Handling the stack so that we re not popping the Edit Profile Fragment....
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>1){
            super.onBackPressed();
        }
    }
}