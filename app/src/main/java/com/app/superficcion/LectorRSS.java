package com.app.superficcion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class LectorRSS extends AsyncTask<Void, Void, Void>{

    private Context context;
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
        return null;
    }


}
