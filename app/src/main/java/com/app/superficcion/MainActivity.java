package com.app.superficcion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView todo, marvel, dc, starWars, fantasy, anime, comic;
    private SearchView searchView;
    private ImageButton home, play, moreRead, calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!CheckNetwork.isInternetAvailable(this)) {
            Toast.makeText(this, "Error: No Internet Connection", Toast.LENGTH_LONG).show();
        }


        //Todas las referencias
        todo = findViewById(R.id.tvTodo);
        marvel = findViewById(R.id.tvMarvel);
        dc = findViewById(R.id.tvDC);
        starWars = findViewById(R.id.tvStarwars);
        fantasy = findViewById(R.id.tvFantasy);
        anime = findViewById(R.id.tvAnime);
        comic = findViewById(R.id.tvComic);
        home = findViewById(R.id.ibHome);
        play = findViewById(R.id.ibPlay);
        moreRead = findViewById(R.id.ibMoreRead);
        calendar = findViewById(R.id.ibCalendar);

        searchView = findViewById(R.id.searchView);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleView);


        LectorRSS lectorRSS = new LectorRSS(getApplicationContext(), recyclerView, searchView);

        lectorRSS.execute(); // Lanzamos el hilo.

        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initCategoryfilter("");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: TODO", Toast.LENGTH_SHORT).show();
            }
        });
        marvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initCategoryfilter("marvel");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: MARVEL", Toast.LENGTH_SHORT).show();
            }
        });
        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initCategoryfilter("dc");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: DC", Toast.LENGTH_SHORT).show();
            }
        });
        starWars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initCategoryfilter("star wars");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: STAR WARS", Toast.LENGTH_SHORT).show();
            }
        });
        fantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initCategoryfilter("fantasía");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: Fantasía y Ciencia Ficción", Toast.LENGTH_SHORT).show();
            }
        });
        anime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initCategoryfilter("anime");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: Anime", Toast.LENGTH_SHORT).show();
            }
        });
        comic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectorRSS.initCategoryfilter("cómic");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: Cómic", Toast.LENGTH_SHORT).show();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recyclerView.scrollToPosition(0); // De esta forma no actualiza el contenido nuevo
                startActivity(new Intent(getApplicationContext(), MainActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyWebView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Enlace", "https://super-ficcion.com/test-y-trivias/");
                startActivity(intent);
            }
        });
        moreRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyWebView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Enlace", "https://super-ficcion.com/lo-mas-leido-super-ficcion/");
                startActivity(intent);
            }
        });

    }
}