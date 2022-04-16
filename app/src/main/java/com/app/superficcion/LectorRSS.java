package com.app.superficcion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.SearchView;

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

//AsincTask crea un hilo nuevo.
public class LectorRSS extends AsyncTask<Void, Void, Void> implements SearchView.OnQueryTextListener {

    private final Context context;
    private final RecyclerView recyclerView;
    private final String direccion = "https://super-ficcion.com/feed/";
    private final SearchView searchView;
    private ArrayList<Noticia> noticias;
    private URL url;
    private AdapterNoticia adapterNoticia;


    public LectorRSS(Context context, RecyclerView recyclerView, SearchView searchView) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.searchView = searchView;
        initListener();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapterNoticia = new AdapterNoticia(noticias, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapterNoticia);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        procesarXML(obtenerDatos()); // Llamamos a los métodos
        return null;
    }

    private void procesarXML(Document data) {
        //si hay doc me devuelve el log
        if (data != null) {
            noticias = new ArrayList<>();
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
                            Node imagechild = actual.getFirstChild();
                            String node = imagechild.getTextContent();

                            String imagen = getImageFromDescription(node);
                            noticia.setmImagen(imagen);

                            String resume = getResumeFromDescription(node);
                            noticia.setmDescripcion(resume);
                        } else if (actual.getNodeName().equalsIgnoreCase("pubDate")) {
                            noticia.setmFecha(formatDate(actual.getTextContent()));
                        }

                        String categoria = itemChilds.item(9).getTextContent();
                        noticia.setmCategoria(categoria);

                    }
                    noticias.add(noticia);
                    //Mock
                    /**
                     Log.d("Titulo", noticia.getmTitulo());
                     Log.d("Link", noticia.getmEnlace());
                     Log.d("Descripcion", noticia.getmDescripcion());
                     Log.d("Imagen", noticia.getmImagen());
                     Log.d("Fecha", noticia.getmFecha());
                     Log.d("Categoria", noticia.getmCategoria());
                     */

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

    public String getImageFromDescription(String string) {
        String[] splitSRC = string.split("src=");
        String[] splitCLASS = splitSRC[1].split("class");
        String[] noLineFeed = splitCLASS[0].split("\n");
        return noLineFeed[0]
                .replace("\"", "")
                .replace("\"", "")
                .replace("/></p>", "");
    }

    public String getResumeFromDescription(String string) {
        String[] splitPar = string.split("<p>");
        String resume = splitPar[3].replace("</p>", "");
        String[] limitedWords = resume.split(" ");
        ArrayList<String> only15 = new ArrayList<>();
        for (String i :
                limitedWords) {
            if (only15.size() < 20) {
                only15.add(i);
            }
        }
        String str = String.join(" ", only15);

        if (str.contains("list") || str.contains("paragraph")) {
            return "¡¡Entra para ver la noticia!!";
        }
        return str + "...";
    }

    public String formatDate(String string) {
        String[] splitDate = string.split(" ");
        //Day
        if (splitDate[0].equalsIgnoreCase("Mon,")) {
            splitDate[0] = "Lunes,";
        } else if (splitDate[0].equalsIgnoreCase("Tue,")) {
            splitDate[0] = "Martes,";
        } else if (splitDate[0].equalsIgnoreCase("Wen,")) {
            splitDate[0] = "Miércoles,";
        } else if (splitDate[0].equalsIgnoreCase("Thu,")) {
            splitDate[0] = "Jueves,";
        } else if (splitDate[0].equalsIgnoreCase("Fri,")) {
            splitDate[0] = "Viernes,";
        } else if (splitDate[0].equalsIgnoreCase("Sat,")) {
            splitDate[0] = "Sábado,";
        } else if (splitDate[0].equalsIgnoreCase("Sun,")) {
            splitDate[0] = "Domingo,";
        }
        //Month
        if (splitDate[2].equalsIgnoreCase("Jan")) {
            splitDate[2] = "Enero";
        } else if (splitDate[2].equalsIgnoreCase("Feb")) {
            splitDate[2] = "Febrero";
        } else if (splitDate[2].equalsIgnoreCase("Mar")) {
            splitDate[2] = "Marzo";
        } else if (splitDate[2].equalsIgnoreCase("Apr")) {
            splitDate[2] = "Abril";
        } else if (splitDate[2].equalsIgnoreCase("May")) {
            splitDate[2] = "Mayo";
        } else if (splitDate[2].equalsIgnoreCase("Jun")) {
            splitDate[2] = "Junio";
        } else if (splitDate[2].equalsIgnoreCase("Jul")) {
            splitDate[2] = "Julio";
        } else if (splitDate[2].equalsIgnoreCase("Aug")) {
            splitDate[2] = "Agosto";
        } else if (splitDate[2].equalsIgnoreCase("Sep")) {
            splitDate[2] = "Septiembre";
        } else if (splitDate[2].equalsIgnoreCase("Oct")) {
            splitDate[2] = "Octubre";
        } else if (splitDate[2].equalsIgnoreCase("Nov")) {
            splitDate[2] = "Noviembre";
        } else if (splitDate[2].equalsIgnoreCase("Dec")) {
            splitDate[2] = "Diciembre";
        }
        return splitDate[0] + " " + splitDate[1] + " " + splitDate[2] + " " + splitDate[3];
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapterNoticia.filter(s);
        return false;
    }

    private void initListener() {
        searchView.setOnQueryTextListener(this);
    }

    public void initCategoryfilter(String s) {
        adapterNoticia.categoryFilter(s);
    }

}


