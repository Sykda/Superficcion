package com.app.superficcion;

import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class MyBrowser extends WebViewClient {

    private final Map<String, Boolean> loadedUrls = new HashMap<>();
    private final boolean blockAds;

    public MyBrowser(boolean blockAds) {
        this.blockAds = blockAds;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        boolean ad;
        if (!loadedUrls.containsKey(url)) {
            ad = AdBlocker.isAd(view.getContext(), url);
            loadedUrls.put(url, ad);
        } else {
            ad = loadedUrls.get(url);
        }
        if (ad && blockAds) {
            // Load an empty resource instead of the ad
            return AdBlocker.createEmptyResource();
        } else {
            // Let the WebView load the original resource
            return super.shouldInterceptRequest(view, url);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        view.loadUrl("javascript:(function() { " +
                "var adDiv = document.getElementById('video-evo-player'); " +
                "if (adDiv) { adDiv.remove(); } " +
                "})()");
    }
}
