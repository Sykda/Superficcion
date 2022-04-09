package com.app.superficcion;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MyWebView extends AppCompatActivity {

    WebView webView;
    Toolbar wevViewToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        wevViewToolbar =  findViewById(R.id.webViewToolbar);
        setSupportActionBar(wevViewToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        webView = findViewById(R.id.webView);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("Enlace");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        webSettings.setLoadsImagesAutomatically(true);

        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.loadUrl(url);
    }


    //Boton "<-".
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}