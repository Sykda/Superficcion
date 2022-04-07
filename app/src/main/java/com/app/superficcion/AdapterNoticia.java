package com.app.superficcion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterNoticia extends RecyclerView.Adapter<AdapterNoticia.MyViewHolder> {

    ArrayList<Noticia> noticias;
    Context context;

    public AdapterNoticia(ArrayList<Noticia> noticias, Context context) {
        this.noticias = noticias;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_noticia, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Noticia actual = noticias.get(position);
        holder.mTitulo.setText(HtmlCompat.fromHtml(actual.getmTitulo(),HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mDescripcion.setText(HtmlCompat.fromHtml(formatearTexto(actual.getmDescripcion()),HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mFecha.setText(HtmlCompat.fromHtml(actual.getmFecha(),HtmlCompat.FROM_HTML_MODE_LEGACY));

        Picasso.get().load(actual.getmImagen()).into(holder.mImagen);

        holder.mImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyWebView.class);
                intent.putExtra("Enlace", actual.getmEnlace());
                context.startActivity(intent);
            }
        });
    }

    private String formatearTexto(String s) {

        String descripcionOriginal = s;
        String[] partes = descripcionOriginal.split("</p>");

        return partes[0];
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTitulo, mDescripcion, mFecha;
        ImageView mImagen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.tituloId);
            mDescripcion = itemView.findViewById(R.id.descripcionId);
            mFecha = itemView.findViewById(R.id.fechaId);
            mImagen = itemView.findViewById(R.id.imageViewId);
        }
    }


}
