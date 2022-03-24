package com.example.gestordelibros;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class MyDialogFragment extends DialogFragment {

    private final MetodosSQL metodos = MetodosSQL.getInstance();
    private Spinner spinner;
    private EditText titulo, autor, coleccion, notas;
    private Bitmap imagen;
    private String estado;
    private Button button;
    private ImageView imageView;
    BitmapFactory.Options options = new BitmapFactory.Options();

    static MyDialogFragment newInstance() {

        return new MyDialogFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        //Referencias.
        button = view.findViewById(R.id.aceptar);
        titulo = view.findViewById(R.id.titulo);
        autor = view.findViewById(R.id.autor);
        coleccion = view.findViewById(R.id.coleccion);
        notas = view.findViewById(R.id.notas);
        imageView = view.findViewById(R.id.imageView);

        //Bundle que trae los objetos enviados desde los Activity.
        Bundle bundle = this.getArguments();
        int position = bundle.getInt("position", 0);

        //Spinner.
        spinner = view.findViewById(R.id.spinner);
        String[] estados = {"Leído", "Por leer", "Leyendo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getContext(), R.layout.spinner_layout, estados);
        spinner.setAdapter(adapter);

        //Lo que pasa cuando seleccionas algo del spinner.
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn,
                                               android.view.View v,
                                               int posicion,
                                               long id) {

                        String select = spinner.getSelectedItem().toString();

                        if (select.equals("Leído")) {
                       