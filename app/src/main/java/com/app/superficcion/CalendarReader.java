package com.app.superficcion;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
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

public class CalendarReader extends AsyncTask<Void, Void, Void> {

    private ReleaseAdapter releaseAdapter;
    private final RecyclerView recyclerView;
    private final Context context;
    private ArrayList<Release> releases;
    private final String direccion = "https://raw.githubusercontent.com/Sykda/xml_calendario/master/xml_calendario.xml";
    private URL url;

    //Constructor
    public CalendarReader(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        releaseAdapter = new ReleaseAdapter(releases, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(releaseAdapter);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        procesarXML(obtenerDatos()); // Llamamos a los métodos
        return null;
    }

    public Document obtenerDatos() {
        try {
            //Peticion a la URL, leemos  la peticion la procesamos y la guardamos en un doc.
            url = new URL(direccion);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");//Tipo de petición
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream); //Parseamos
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void procesarXML(Document data) {
        //si hay doc me devuelve el log
        if (data != null) {
            releases = new ArrayList<>();
            Element root = data.getDocumentElement();

            for (int j = 0; j < root.getChildNodes().getLength(); j++) {
                Node peliculas = root.getChildNodes().item(j);
                NodeList pelicula = peliculas.getChildNodes();
                Release release = new Release();

                if (peliculas.getNodeName().equalsIgnoreCase("pelicula")) {

                    for (int i = 0; i < pelicula.getLength(); i++) {
                        Node atributos = pelicula.item(i);

                        if (atributos.getNodeName().equalsIgnoreCase("categoria")) {
                            release.setCategoria(atributos.getTextContent());
                        } else if (atributos.getNodeName().equalsIgnoreCase("fecha")) {
                            release.setFecha(atributos.getTextContent());
                        } else if (atributos.getNodeName().equalsIgnoreCase("titulo")) {
                            release.setTitulo(atributos.getTextContent());
                        } else if (atributos.getNodeName().equalsIgnoreCase("imagen")) {
                            release.setImagen(atributos.getTextContent());
                        } else if (atributos.getNodeName().equalsIgnoreCase("descripcion")) {
                            release.setDescripcion(atributos.getTextContent());
                        }
                    }
                    this.releases.add(release);
                    Log.d("categoria: ", release.getCategoria());
                    Log.d("fecha: ", release.getFecha());
                    Log.d("titulo: ", release.getTitulo());
                    Log.d("imagen: ", release.getImagen());
                    Log.d("descripcion: ", release.getDescripcion());
                }
            }


        }
    }
}
