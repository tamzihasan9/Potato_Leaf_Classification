package com.example.potatoleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Video extends AppCompatActivity {

 WebView webView;

    String video =" <iframe width=\"360\" height=\"244\" src=\"https://www.youtube.com/embed/DjjIZnM3jGo?si=OUCqwPPrIxmLIf41\" title=\"YouTube video player\" frameborder=\"1\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        webView=findViewById(R.id.viewid);


    }
    public void StartPlayideo(View view){
        webView.getSettings().setJavaScriptEnabled(true);
         webView.loadData(video,"text/html","utf-8");



    }

    }

