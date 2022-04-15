package com.app.superficcion;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView marvel, dc, starWars, fantasy, anime, comic;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!CheckNetwork.isInternetAvailable(this)) {
            Toast.makeText(this, "Error: No Internet Connection", Toast.LENGTH_LONG).show();
        }


        //Todas las referencias
        marvel = findViewById(R.id.tvMarvel);
        dc = findViewById(R.id.tvDC);
        starWars = findViewById(R.id.tvStarwars);
        fantasy = findViewById(R.id.tvFantasy);
        anime = findViewById(R.id.tvAnime);
        comic = findViewById(R.id.tvComic);

        searchView = findViewById(R.id.searchView);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleView);



        LectorRSS lectorRSS = new LectorRSS(getApplicationContext(), recyclerView, searchView);

        lectorRSS.execute(); // Lanzamos el hilo.

        marvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initfilter("marvel");
                Toast.makeText(MainActivity.this, "Filtrando por: MARVEL", Toast.LENGTH_SHORT).show();
            }
        });
        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initfilter("dc");
                Toast.makeText(MainActivity.this, "Filtrando por: DC", Toast.LENGTH_SHORT).show();
            }
        });
        starWars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initfilter("star wars");
                Toast.makeText(MainActivity.this, "Filtrando por: STAR WARS", Toast.LENGTH_SHORT).show();
            }
        });
        fantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initfilter("fantasía");
                Toast.makeText(MainActivity.this, "Filtrando por: Fantasía y Ciencia Ficción", Toast.LENGTH_SHORT).show();
            }
        });
        anime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initfilter("anime");
                Toast.makeText(MainActivity.this, "Filtrando por: Anime", Toast.LENGTH_SHORT).show();
            }
        });
        comic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initfilter("cómic");
                Toast.makeText(MainActivity.this, "Filtrando por: Comic", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }


}