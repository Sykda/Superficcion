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
import java.util.List;
import java.util.stream.Collectors;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private final ArrayList<News> news;
    private final ArrayList<News> originalItems;
    private final Context context;

    //Constructor
    public NewsAdapter(ArrayList<News> news, Context context) {
        this.news = news;
        this.context = context;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(news);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        MyViewHolder newsHolder = new MyViewHolder(view);
        return newsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        News news = this.news.get(position);
        holder.mTitulo.setText(HtmlCompat.fromHtml(news.getmTitulo(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mDescripcion.setText(HtmlCompat.fromHtml(news.getmDescripcion(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mFecha.setText(HtmlCompat.fromHtml(news.getmFecha(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mCategoria.setText(HtmlCompat.fromHtml(news.getmCategoria(), HtmlCompat.FROM_HTML_MODE_LEGACY));

        Picasso.get().load(news.getmImagen()).into(holder.mImagen);

        holder.cardView.setOnClickListener((View v) -> {
            Intent intent = new Intent(context, MyWebView.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Enlace", news.getmEnlace());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    //Filtro
    public void filter(final String strSearch, int choice) {

        if (strSearch.length() == 0) {
            news.clear();
            news.addAll(originalItems);
        } else {
            news.clear();
            List<News> collect;
            if (choice == 0) {
                collect = originalItems.stream()
                        .filter(i -> i.getmTitulo().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());
            } else {
                collect = originalItems.stream()
                        .filter(i -> i.getmCategoria().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());
            }
            news.addAll(collect);
        }
        notifyDataSetChanged();
    }

    //Adaptador personalizado
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
