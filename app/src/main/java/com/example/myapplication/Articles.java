package com.example.myapplication;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Articles {
    private ArrayList<Article> articles_list;

    public Articles() {
    }

    public ArrayList<Article> getArticles() {
        return articles_list;
    }

    public void setArticles(ArrayList<Article> articles) {
        articles_list = articles;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "articles_list=" + articles_list +
                '}';
    }
}
