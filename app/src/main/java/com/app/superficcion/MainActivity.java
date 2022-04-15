package com.app.superficcion;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!CheckNetwork.isInternetAvailable(this)){
            Toast.makeText(this,"Error: No Internet Connection",Toast.LENGTH_LONG).show();
        }

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleView);

        LectorRSS lectorRSS = new LectorRSS(getApplicationContext(), recyclerView);
        lectorRSS.execute(); // Lanzamos el hilo.

    }
}