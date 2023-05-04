package com.app.superficcion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

public class MyWebView extends AppCompatActivity {

    @SuppressLint({"RequiresFeature", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the status bar color to the default color of the window
        getWindow().setStatusBarColor(Window.getDefaultFeatures(this));

        setContentView(R.layout.activity_web_view);

        // Initialize the AdBlocker library
        AdBlocker.init(this);

        // Set up the toolbar
        Toolbar wevViewToolbar = findViewById(R.id.webViewToolbar);
        setSupportActionBar(wevViewToolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        // Set up the WebView
        WebView webView = findViewById(R.id.webView);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("Enlace");

        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);

        webView.setWebViewClient(new MyBrowser(true));
        webView.clearCache(true);
        webView.clearHistory();

        // Set the force dark strategy for the WebView if it is supported
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK_STRATEGY)) {
            WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
        }

        webView.loadUrl(url);

    }


    //Boton "<-" en la barra de herramientas
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.i("ActionBar", "Atrás!");
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}