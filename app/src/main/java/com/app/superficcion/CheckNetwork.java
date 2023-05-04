package com.app.superficcion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckNetwork {
    public static void isInternetAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            // Hay conexión a internet
        } else {
            // No hay conexión a internet
            Toast.makeText(context.getApplicationContext(), "No hay conexión a internet", Toast.LENGTH_SHORT).show();
        }
    }
}