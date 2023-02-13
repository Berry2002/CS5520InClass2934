package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Profile implements Parcelable {
    private int avatar;
    private String name;
    private String email;
    private String device;
    private String mood;
    private int moodImage;

    public Profile() {

    }
    public Profile(int avatar, String name, String email, String device, String mood, int moodImage) {
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.device = device;
        this.mood = mood;
        this.moodImage = moodImage;
    }

    protected Profile(Parcel in) {
        avatar = in.readInt();
        name = in.readString();
        email = in.readString();
        device = in.readString();
        mood = in.readString();
        moodImage = in.readInt();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDevice() {
        return device;
    }

    public String getMood() {
        return mood;
    }

    public int getMoodImage() {
        return moodImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void setMoodImage(int moodImage) {
        this.moodImage = moodImage;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(avatar);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(device);
        parcel.writeString(mood);
        parcel.writeInt(moodImage);
    }
}
