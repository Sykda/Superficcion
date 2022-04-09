package com.app.superficcion;

import android.os.Bundle;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MyWebView extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("Enlace");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        webSettings.setLoadsImagesAutomatically(true);

        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.loadUrl(url);
    }

}