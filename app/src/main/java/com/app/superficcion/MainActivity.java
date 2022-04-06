package com.app.superficcion;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);


        recyclerView = findViewById(R.id.recycleView);



        LectorRSS lectorRSS = new LectorRSS(getApplicationContext(), recyclerView);
        lectorRSS.execute(); // Lanzamos el hilo.


    }
}