package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;

import javax.xml.validation.Validator;

public class InClass05 extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final String TAG = "demo";

    private Button buttonGo;
    private List<String> keyWords = new ArrayList<>();
    private String KEYWORDS_URL = "http://ec2-54-164-201-39.compute-1.amazonaws.com/apis/\n" +
            "images/keywords";
    private String GET_PHOTO_URL = "http://ec2-54-164-201-39.compute-1.amazonaws.com/apis/\n" +
            "images/retrieve";

    private List<String> url_list = new ArrayList<>();

    private ImageButton imageButtonPrev;
    private ImageButton imageButtonNext;

    private ProgressBar progressBarLoad;

    private ImageView imageView;

    private EditText editTextKeyword;

    private TextView textViewLoad;

    private int index = 0; // current index for the photo url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class05);
        setTitle("Image Search");

        buttonGo = findViewById(R.id.buttonGo);
        imageButtonPrev = findViewById(R.id.imageButtonPrev);
        imageButtonNext = findViewById(R.id.imageButtonNext);
        progressBarLoad = findViewById(R.id.progressBarLoad);
        imageView = findViewById(R.id.imageView);
        editTextKeyword = findViewById(R.id.editTextKeyword);
        textViewLoad = findViewById(R.id.textViewLoad);

        buttonGo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // get keywords
                Request request = new Request.Builder().url(KEYWORDS_URL).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            assert responseBody != null;
                            Log.d("words", responseBody.string());
                            String[] body = responseBody.string().split(",");
                            keyWords.addAll(Arrays.asList(body)); // get keywords
                            Log.d("key words", keyWords.toString());
                        } else {
                            Toast.makeText(InClass05.this, "Could not fetch key words", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                String keyWord = editTextKeyword.getText().toString();
                if (!keyWords.contains(keyWord)) {  // if invalid key word
                    Toast.makeText(InClass05.this, "Invalid Keyword", Toast.LENGTH_SHORT).show();
                }

                // build url with key word
                HttpUrl url = HttpUrl.parse(GET_PHOTO_URL).newBuilder()
                        .addQueryParameter("keyword", keyWord)
                        .build();

                Log.d("demo", url.toString());
                Request request2 = new Request.Builder().url(url).build();
                client.newCall(request2).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            assert responseBody != null;
                            String[] body = responseBody.string().split("\n"); // get image urls
                            url_list.addAll(Arrays.asList(body)); // get keywords

                            Log.d("demo", url_list.toString());
                            // display first image
                            String image_url = url_list.get(0);
                            loadImage(image_url);
                        } else {
                            Toast.makeText(InClass05.this, "No images found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // if at most 1 photo, disable prev & next buttons
        if (keyWords.size() <= 1) {
            imageButtonPrev.setClickable(false);
            imageButtonNext.setClickable(false);
        } else if (index == 0) {  // at first photo, disable prev
            imageButtonPrev.setClickable(false);
        } else if (index == keyWords.size() - 1) {  // at last photo, disable next
            imageButtonNext.setClickable(false);
        }

        imageButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                loadImage(keyWords.get(index));
            }
        });

        imageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                loadImage(keyWords.get(index));
            }
        });
    }

    private void loadImage(String url) {
        RequestBuilder<Drawable> requestBuilder = Glide.with(this).load(url);
        requestBuilder
                .thumbnail(Glide.with(this)
                        .load(this))
                .listener((RequestListener<Drawable>) this)
                .load(url)
                .into(imageView);
        progressBarLoad.setVisibility(View.INVISIBLE);  // set load bar & text to invisible
        textViewLoad.setVisibility(View.INVISIBLE);
    }
}

