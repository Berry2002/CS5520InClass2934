package com.example.myapplication.Models;

import com.example.myapplication.RecyclerAdapters.MessagesAdapter;

import java.util.Date;

public class Message {
    private String text;
    private String imageurl;

    private User sender;
    private long time;

    public Message() {

    }
    public Message(String text, User sender) {
        this.text = text;
        this.sender = sender;
        Date date = new Date();
        this.time = date.getTime();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", sender=" + sender +
                ", time=" + time +
                '}';
    }
}
