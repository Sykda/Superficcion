package com.app.superficcion;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CalendarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
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

        recyclerView=findViewById(R.id.recycleView);

        CalendarReader calendarReader = new CalendarReader(this, recyclerView);
        calendarReader.execute();

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
}