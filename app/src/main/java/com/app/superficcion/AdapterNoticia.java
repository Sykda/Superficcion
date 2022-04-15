package com.app.superficcion;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import java.util.List;
import java.util.stream.Collectors;

public class AdapterNoticia extends RecyclerView.Adapter<AdapterNoticia.MyViewHolder> {

    private ArrayList<Noticia> noticias;
    private ArrayList<Noticia> originalItems;
    private Context context;


    public AdapterNoticia(ArrayList<Noticia> noticias, Context context) {
        this.noticias = noticias;
        this.context = context;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(noticias);
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


    public void filter(final String strSearch) {
        if (strSearch.length() == 0) {
            noticias.clear();
            noticias.addAll(originalItems);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                noticias.clear();
                List<Noticia> collect = originalItems.stream()
                        //.filter(i -> i.getmTitulo().toLowerCase().contains(strSearch))
                        //.filter(i -> i.getmDescripcion().toLowerCase().contains(strSearch))
                        .filter(i -> i.getmCategoria().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                noticias.addAll(collect);
            }
            else {
                noticias.clear();
                for (Noticia i : originalItems) {
                    if (i.getmDescripcion().toLowerCase().contains(strSearch)) {
                        noticias.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
