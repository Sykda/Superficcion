package com.app.superficcion;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {

    public static OptionAdapter optionAdapter;
    public static RecyclerView recyclerView, recyclerOpciones;
    private final ArrayList<Option> optionList = new ArrayList<>();
    private Toolbar wevViewToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Window.getDefaultFeatures(this));
        setContentView(R.layout.activity_calendar);

        //Definimos la barra de herramientas.
        wevViewToolbar = findViewById(R.id.webViewToolbar);
        setSupportActionBar(wevViewToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recycleView);

        CalendarReader calendarReader = new CalendarReader(this, recyclerView);
        calendarReader.execute();

        //
        optionAdapter = MainActivity.optionAdapter;
        recyclerOpciones = findViewById(R.id.recyclerOpciones);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CalendarActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerOpciones.setLayoutManager(linearLayoutManager);
        recyclerOpciones.setAdapter(optionAdapter);
        prepareOptionsData();

    }

    //Boton "<-" en la barra de herramientas
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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