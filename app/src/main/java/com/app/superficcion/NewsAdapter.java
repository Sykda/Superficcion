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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private final ArrayList<News> news;
    private final ArrayList<News> originalItems;
    private final Context context;

    //Constructor
    public NewsAdapter(ArrayList<News> news, Context context) {
        this.news = news;
        this.context = context;
        this.originalItems = new ArrayList<>();
        try {
            originalItems.addAll(news);
        } catch (Exception e) {
            System.out.println("Cannot read from the website");
        }

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
        holder.mTitulo.setText(HtmlCompat.fromHtml(news.getTitle(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mDescripcion.setText(HtmlCompat.fromHtml(news.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mFecha.setText(HtmlCompat.fromHtml(news.getDate(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.mCategoria.setText(HtmlCompat.fromHtml(news.getCategory(), HtmlCompat.FROM_HTML_MODE_LEGACY));

        Picasso.get().load(news.getImage()).into(holder.mImagen);

        holder.cardView.setOnClickListener((View v) -> {
            Intent intent = new Intent(context, MyWebView.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Enlace", news.getLink());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        try {
            return news.size();
        } catch (Exception e) {
            return 0;
        }
    }

    //Filter
    public void filter(String strSearch, int choice) {
        String searchQuery = strSearch.toLowerCase();

        if (searchQuery.isEmpty()) {
            news.clear();
            news.addAll(originalItems);
        } else {
            List<News> filteredList = new ArrayList<>();

            for (News newsItem : originalItems) {
                String title = newsItem.getTitle().toLowerCase();
                String category = newsItem.getCategory().toLowerCase();

                if (choice == 0 && title.contains(searchQuery)) {
                    filteredList.add(newsItem);
                } else if (choice == 1 && category.contains(searchQuery)) {
                    filteredList.add(newsItem);
                }
            }

            news.clear();
            news.addAll(filteredList);
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
