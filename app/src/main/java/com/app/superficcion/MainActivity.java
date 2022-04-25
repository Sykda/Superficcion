package com.app.superficcion;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView todo, marvel, dc, starWars, fantasy, anime, comic;
    private SearchView searchView;
    private ImageButton home, play, moreRead, calendar;

    //Programamos el comportamiento del botón "atrás" de android.
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Quieres salir de Super-Ficción?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Comprobamos si hay conexión a internet
        if (!CheckNetwork.isInternetAvailable(this)) {
            Toast.makeText(this, "Error: No Tienes Conexión a Internet", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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

        //Instancia y lanzamiento de la clase lectorRSS
        LectorRSS lectorRSS = new LectorRSS(getApplicationContext(), recyclerView, searchView);
        lectorRSS.execute();

        //Programamos el comportamiento de los botones
        todo.setOnClickListener((View v) -> {
                lectorRSS.initCategoryfilter("");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: TODO", Toast.LENGTH_SHORT).show();
        });

        marvel.setOnClickListener((View v) -> {
                lectorRSS.initCategoryfilter("marvel");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: MARVEL", Toast.LENGTH_SHORT).show();
        });

        dc.setOnClickListener((View v) -> {
                lectorRSS.initCategoryfilter("dc");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: DC", Toast.LENGTH_SHORT).show();
        });

        starWars.setOnClickListener((View v) -> {
                lectorRSS.initCategoryfilter("star wars");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: STAR WARS", Toast.LENGTH_SHORT).show();
        });

        fantasy.setOnClickListener((View v) -> {
                lectorRSS.initCategoryfilter("fantasía");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: Fantasía y Ciencia Ficción", Toast.LENGTH_SHORT).show();
        });

        anime.setOnClickListener((View v) -> {
                lectorRSS.initCategoryfilter("anime");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: Anime", Toast.LENGTH_SHORT).show();
        });

        comic.setOnClickListener((View v) -> {
                lectorRSS.initCategoryfilter("cómic");
                recyclerView.scrollToPosition(0);
                Toast.makeText(MainActivity.this, "Filtrando por: Cómic", Toast.LENGTH_SHORT).show();
        });

        home.setOnClickListener((View v) ->
                startActivity(new Intent(getApplicationContext(), MainActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle())
        );

        play.setOnClickListener((View v) -> {
                Intent intent = new Intent(getApplicationContext(), MyWebView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Enlace", "https://super-ficcion.com/test-y-trivias/");
                startActivity(intent);
        });

        moreRead.setOnClickListener((View v) -> {
                Intent intent = new Intent(getApplicationContext(), MyWebView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Enlace", "https://super-ficcion.com/lo-mas-leido-super-ficcion/");
                startActivity(intent);
        });

        calendar.setOnClickListener((View v) -> Toast.makeText(MainActivity.this, "En desarrollo...", Toast.LENGTH_SHORT).show());
    }
}