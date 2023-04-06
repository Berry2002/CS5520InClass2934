package com.example.myapplication.Models;

import java.util.Objects;

import javax.annotation.Nullable;

public class User {
    private String firstname, lastname, email, imageurl;

    public User() {

    }

    public User(String firstname, String lastname, String email, String imageurl) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.imageurl = imageurl;
    }

    public User(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        User user = (User) obj;
        return getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}
