package com.app.superficcion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//AsincTask crea un hilo nuevo.
public class LectorRSS extends AsyncTask<Void, Void, Void>{

    private Context context;
    String direccion= "https://super-ficcion.com/feed/";
    URL url;
    private ProgressBar progressBar;

    public LectorRSS(Context context) {
        this.context=context;
        progressBar =  new ProgressBar(context);

    }

    @Override
    protected void onPreExecute(){
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        procesarXML(obtenerDatos()); // Llamamos a los métodos
        return null;
    }

    private void procesarXML(Document data){
        //si hay doc me devuelve el log
            if(data != null){
                Log.d("Elemento root",data.getDocumentElement().getNodeName());
            }
    }

    public Document obtenerDatos (){
        try{
            //Peticion a la URL, leemos  la peticion la procesamos y la guardamos en un doc.
            url = new URL(direccion);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");//tipo de petición
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream); //Parsear
            return xmlDoc;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
