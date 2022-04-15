package com.app.superficcion;

import android.Manifest;
import android.os.Bundle;
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

        toolbar = findViewById(R.id.toolbar);

        verificarPermisos();

        recyclerView = findViewById(R.id.recycleView);

        LectorRSS lectorRSS = new LectorRSS(getApplicationContext(), recyclerView);
        lectorRSS.execute(); // Lanzamos el hilo.


    }

    private void verificarPermisos(){
        ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        requestPermissions(new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
    }
}