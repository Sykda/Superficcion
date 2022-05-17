package com.app.superficcion;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static RecyclerView recyclerView, recyclerOpciones;
    private final ArrayList<Option> optionList = new ArrayList<>();
    private SearchView searchView;
    private ImageButton home, play, moreRead, calendar;
    private OptionAdapter optionAdapter;

    //Programamos el comportamiento del botón "atrás" de android.
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Quieres salir de Super-Ficción?")
                .setPositiveButton("Si", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("Cancelar", (dialog, which) ->
                        dialog.dismiss()
                );
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
        home = findViewById(R.id.ibHome);
        play = findViewById(R.id.ibPlay);
        moreRead = findViewById(R.id.ibMoreRead);
        calendar = findViewById(R.id.ibCalendar);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recycleView);

        //Instancia y lanzamiento de la clase RSSReader
        RSSReader RSSReader = new RSSReader(getApplicationContext(), recyclerView, searchView);
        RSSReader.execute();

        //
        optionAdapter = new OptionAdapter(MainActivity.this, optionList);
        recyclerOpciones = findViewById(R.id.recyclerOpciones);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerOpciones.setLayoutManager(linearLayoutManager);
        recyclerOpciones.setAdapter(optionAdapter);
        prepareOptionsData();

        //Programamos el comportamiento de los botones
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

    private void prepareOptionsData() {
        Option opcion = new Option(1, "TODO");
        optionList.add(opcion);
        opcion = new Option(2, "MARVEL");
        optionList.add(opcion);
        opcion = new Option(3, "DC");
        optionList.add(opcion);
        opcion = new Option(4, "STAR WARS");
        optionList.add(opcion);
        opcion = new Option(5, "SCI-FY/FANTASÍA");
        optionList.add(opcion);
        opcion = new Option(6, "ANIME");
        optionList.add(opcion);
        opcion = new Option(7, "CÓMIC");
        optionList.add(opcion);
    }
}