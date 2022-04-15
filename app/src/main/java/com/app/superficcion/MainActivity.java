package com.app.superficcion;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView marvel, dc, starWars, fantasy, anime, comic;
    private SearchView searchView;
    private String search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!CheckNetwork.isInternetAvailable(this)){
            Toast.makeText(this,"Error: No Internet Connection",Toast.LENGTH_LONG).show();
        }

        //Todas las referencias
        marvel=findViewById(R.id.tvMarvel);
        dc=findViewById(R.id.tvDC);
        starWars=findViewById(R.id.tvStarwars);
        fantasy=findViewById(R.id.tvFantasy);
        anime=findViewById(R.id.tvAnime);
        comic=findViewById(R.id.tvComic);

        searchView=findViewById(R.id.searchView);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleView);

        marvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search="marvel";
                Toast.makeText(MainActivity.this, "Funcionando saca: "+search, Toast.LENGTH_SHORT).show();
            }
        });

        LectorRSS lectorRSS = new LectorRSS(getApplicationContext(), recyclerView, searchView, search);
        lectorRSS.execute(); // Lanzamos el hilo.

    }



}