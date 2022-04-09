package com.app.superficcion;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

//Interface para que el js de la pagina permita toast
public class WebAppInterface {
    Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}