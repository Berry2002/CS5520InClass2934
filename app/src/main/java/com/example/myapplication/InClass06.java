package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InClass06 extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    private ListView listView_category;
    private ListView listView_country;
    private List<Article> articles_list = new ArrayList<>();
    private ArrayAdapter<String> adapter_category;
    private ArrayAdapter<String> adapter_country;
    private static final String headlines_url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=081bb1a04370401c93830c68be6a4c71";
    private static final String[] CATEGORY = {"business", "entertainment", "general", "health", "science", "sports", "technology"};
    private static final String[] COUNTRY = {"ar", "cu", "fr", "mx", "ua"};

    private String chosen_category;
    private String chosen_country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class06);
        setTitle("Today's News");

        listView_category = findViewById(R.id.listview);
        adapter_category = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1, CATEGORY);
        listView_category.setAdapter(adapter_category);
        listView_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int item, long id) {
                chosen_category = CATEGORY[item];
                adapter_category.notifyDataSetChanged();
            }
        });

        listView_country = findViewById(R.id.listview2);
        adapter_country = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1, COUNTRY);
        listView_country.setAdapter(adapter_country);
        listView_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int item, long id) {
                chosen_country = COUNTRY[item];
                adapter_country.notifyDataSetChanged();
            }
        });

        getNews();
    }

    private void getNews() {
        HttpUrl url = HttpUrl.parse(headlines_url).newBuilder()
                .addQueryParameter("category", chosen_category)
                .addQueryParameter("country", chosen_country)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gsonData = new Gson();
                    Headline headline =  gsonData.fromJson(response.body().charStream(), Headline.class);
                    articles_list = headline.getArticles();
                    Intent intent = new Intent(InClass06.this, DisplayNews.class);
                    Log.d("demo", "onResponse: " + headline.toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(InClass06.this, "Could not fetch news", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}