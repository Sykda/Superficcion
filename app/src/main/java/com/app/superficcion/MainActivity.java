package com.app.superficcion;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static RecyclerView recyclerView, recyclerOptions;

    public static OptionAdapter optionAdapter;

    private final ArrayList<Option> optionList = new ArrayList<>();

    private SearchView searchView;

    private ImageButton home, play, moreRead, calendar;

    private CheckNetwork checkNetwork;

    //Button back
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

        // Set the layout
        setContentView(R.layout.activity_main);

        // Set the status bar color
        getWindow().setStatusBarColor(Window.getDefaultFeatures(this));

        // Check internet connection
        checkNetwork = new CheckNetwork();
        checkNetwork.isInternetAvailable(this);

        // Find the views by their IDs
        home = findViewById(R.id.ibHome);
        play = findViewById(R.id.ibPlay);
        moreRead = findViewById(R.id.ibMoreRead);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recycleView);

        // Create an instance of the RSSReader class and execute it
        RSSReader RSSReader = new RSSReader(getApplicationContext(), recyclerView, searchView);
        RSSReader.execute();

        // Create the optionAdapter and set it to the recyclerOptions
        optionAdapter = new OptionAdapter(MainActivity.this, optionList);
        recyclerOptions = findViewById(R.id.recyclerOpciones);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerOptions.setLayoutManager(linearLayoutManager);
        recyclerOptions.setAdapter(optionAdapter);

        // Prepare the data for the options
        prepareOptionsData();

        // Set onClickListeners for the ImageButtons
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
    }

    // Prepare the data for the options
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkNetwork.dismissDialog();
    }
}