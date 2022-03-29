package com.app.superficcion;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

//AsincTask crea un hilo nuevo.
public class LectorRSS extends AsyncTask<Void, Void, Void> {

    private Context context;
    String direccion = "https://super-ficcion.com/feed/";
    URL url;
    private ProgressBar progressBar;

    public LectorRSS(Context context) {
        this.context = context;
        progressBar = new ProgressBar(context);

    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        procesarXML(obtenerDatos()); // Llamamos a los métodos
        return null;
    }

    private void procesarXML(Document data) {
        //si hay doc me devuelve el log
        if (data != null) {
            ArrayList<Noticia> noticias = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node hijoActual = items.item(i);
                if (hijoActual.getNodeName().equalsIgnoreCase("item")) {
                    Noticia noticia = new Noticia();
                    NodeList itemChilds = hijoActual.getChildNodes();
                    for (int j = 0; j < itemChilds.getLength(); j++) {
                        Node actual = itemChilds.item(j);
                        if (actual.getNodeName().equalsIgnoreCase("title")) {
                            noticia.setmTitulo(actual.getTextContent());
                        } else if (actual.getNodeName().equalsIgnoreCase("link")) {
                            noticia.setmEnlace(actual.getTextContent());
                        } else if (actual.getNodeName().equalsIgnoreCase("description")) {
                            noticia.setmDescripcion(actual.getTextContent());
                        } else if (actual.getNodeName().equalsIgnoreCase("enclosure")) {
                            String mUrl = actual.getAttributes().item(0).getTextContent();
                            noticia.setmImagen(mUrl);
                        } else if (actual.getNodeName().equalsIgnoreCase("pubDate")) {
                            noticia.setmFecha(actual.getTextContent());
                        }
                    }
                    noticias.add(noticia);
                    Log.d("Titulo", noticia.getmTitulo());
                    Log.d("Link", noticia.getmEnlace());
                    Log.d("Descripcion", noticia.getmDescripcion());
                    Log.d("Imagen", noticia.getmImagen());
                    Log.d("Fecha", noticia.getmFecha());
                }
            }
        }
    }

    public Document obtenerDatos() {
        try {
            //Peticion a la URL, leemos  la peticion la procesamos y la guardamos en un doc.
            url = new URL(direccion);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");//tipo de petición
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream); //Parsear
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
