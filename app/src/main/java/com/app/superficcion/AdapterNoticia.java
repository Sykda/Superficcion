package com.app.superficcion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        holder.mTitulo.setText(HtmlCompat.fromHtml(actual.getmTitulo(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mDescripcion.setText(HtmlCompat.fromHtml(actual.getmDescripcion(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mFecha.setText(HtmlCompat.fromHtml(actual.getmFecha(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mCategoria.setText(HtmlCompat.fromHtml(actual.getmCategoria(), HtmlCompat.FROM_HTML_MODE_LEGACY));

        Picasso.get().load(actual.getmImagen()).into(holder.mImagen);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyWebView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Enlace", actual.getmEnlace());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTitulo, mDescripcion, mFecha, mCategoria;
        ImageView mImagen;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.tituloId);
            mDescripcion = itemView.findViewById(R.id.descripcionId);
            mFecha = itemView.findViewById(R.id.fechaId);
            mImagen = itemView.findViewById(R.id.imageViewId);
            mCategoria = itemView.findViewById(R.id.categoriaId);
            cardView = itemView.findViewById(R.id.cardViewNoticia);
        }
    }
}
