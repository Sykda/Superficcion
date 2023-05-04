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
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//AsincTask crea un hilo nuevo.
public class RSSReader extends AsyncTask<Void, Void, Void> implements SearchView.OnQueryTextListener {

    private static NewsAdapter newsAdapter;
    private final RecyclerView recyclerView;
    private final String direccion = "https://super-ficcion.com/feed/";
    private final SearchView searchView;
    private final Context context;
    private ArrayList<News> news;
    private URL url;

    //Constructor
    public RSSReader(Context context, RecyclerView recyclerView, SearchView searchView) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.searchView = searchView;
        initListener();
    }

    public static void categoryFilter(String s) {
        if (newsAdapter != null) {
            newsAdapter.filter(s, 1);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        newsAdapter = new NewsAdapter(news, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        procesarXML(obtenerDatos()); // Llamamos a los métodos
        return null;
    }

    //Leemos el documento y sacamos la información de cada nodo
    private void procesarXML(Document data) {
        //si hay doc me devuelve el log
        if (data != null) {
            news = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node hijoActual = items.item(i);
                if (hijoActual.getNodeName().equalsIgnoreCase("item")) {
                    News news = new News();
                    NodeList itemChilds = hijoActual.getChildNodes();
                    for (int j = 0; j < itemChilds.getLength(); j++) {
                        Node actual = itemChilds.item(j);
                        if (actual.getNodeName().equalsIgnoreCase("title")) {
                            news.setTitle(actual.getTextContent());
                        } else if (actual.getNodeName().equalsIgnoreCase("link")) {
                            news.setLink(actual.getTextContent());
                        } else if (actual.getNodeName().equalsIgnoreCase("description")) {
                            Node imagechild = actual.getFirstChild();
                            String node = imagechild.getTextContent();

                            String imagen = getImageFromDescription(node);
                            news.setImage(imagen);

                            String resume = getResumeFromDescription(node);
                            news.setDescription(resume);
                        } else if (actual.getNodeName().equalsIgnoreCase("pubDate")) {
                            news.setDate(formatDate(actual.getTextContent()));
                        }

                        String categoria = itemChilds.item(9).getTextContent();
                        news.setCategory(categoria);

                    }
                    this.news.add(news);
                }
            }
        }
    }

    //Leemos la web y la guardamos en un documento
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

    //Sacamos la imagen desde la descripción
    public String getImageFromDescription(String string) {
        if (string.contains("src")) {
            String[] splitSRC = string.split("src=");
            String[] splitCLASS = splitSRC[1].split("class");
            String[] noLineFeed = splitCLASS[0].split("\n");
            return noLineFeed[0]
                    .replace("\"", "")
                    .replace("\"", "")
                    .replace("/></p>", "");
        }
        return null;
    }

    //Sacamos el resumen desde la descripción
    public String getResumeFromDescription(String string) {
        String[] splitPar = string.split("<p>");

        if (splitPar.length < 4 || splitPar[3].isEmpty()) {
            return "¡¡Entra para ver la noticia!!";
        }

        String resume = splitPar[3].replace("</p>", "");
        String[] limitedWords = resume.split(" ");
        ArrayList<String> limited = new ArrayList<>();
        for (String i :
                limitedWords) {
            if (limited.size() < 20) {
                limited.add(i);
            }
        }
        String str = String.join(" ", limited);

        if (str.contains("list") || str.contains("paragraph")) {
            return "¡¡Entra para ver la noticia!!";
        }
        return str + "...";
    }

    // Formats a date string in the format "EEE, dd MMM yyyy HH:mm:ss zzz" into a human-readable format.
    public String formatDate(String string) {
        String[] splitDate = string.split(" ");

        Map<String, String> daysOfWeek = new HashMap<>();
        daysOfWeek.put("Mon,", "Lunes,");
        daysOfWeek.put("Tue,", "Martes,");
        daysOfWeek.put("Wed,", "Miércoles,");
        daysOfWeek.put("Thu,", "Jueves,");
        daysOfWeek.put("Fri,", "Viernes,");
        daysOfWeek.put("Sat,", "Sábado,");
        daysOfWeek.put("Sun,", "Domingo,");

        Map<String, String> months = new HashMap<>();
        months.put("Jan", "Enero");
        months.put("Feb", "Febrero");
        months.put("Mar", "Marzo");
        months.put("Apr", "Abril");
        months.put("May", "Mayo");
        months.put("Jun", "Junio");
        months.put("Jul", "Julio");
        months.put("Aug", "Agosto");
        months.put("Sep", "Septiembre");
        months.put("Oct", "Octubre");
        months.put("Nov", "Noviembre");
        months.put("Dec", "Diciembre");

        splitDate[0] = daysOfWeek.getOrDefault(splitDate[0], splitDate[0]);
        splitDate[2] = months.getOrDefault(splitDate[2], splitDate[2]);

        return splitDate[0] + " " + splitDate[1] + " " + splitDate[2] + " " + splitDate[3];
    }


    // Filters the news list based on a search string and choice of category
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        newsAdapter.filter(s, 0);
        recyclerView.scrollToPosition(0);
        return false;
    }

    private void initListener() {
        searchView.setOnQueryTextListener(this);
    }
}


