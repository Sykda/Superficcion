package com.app.superficcion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReleaseAdapter extends RecyclerView.Adapter<ReleaseAdapter.MyViewHolder> {

    private final ArrayList<Release> originalItems;
    private final ArrayList<Release> releases;
    private final Context context;

    //Constructor
    public ReleaseAdapter(ArrayList<Release> releases, Context context) {
        this.releases = releases;
        this.context = context;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(releases);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_release, parent, false);
        MyViewHolder releaseHolder = new MyViewHolder(view);
        return releaseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Release release = this.releases.get(position);
        holder.mTitulo.setText(release.getTitulo());
        holder.mDescripcion.setText(release.getDescripcion());
        holder.mFecha.setText(release.getFecha());
        holder.mCategoria.setText(release.getCategoria());

        Picasso.get().load(release.getImagen()).into(holder.mImagen);

    }

    @Override
    public int getItemCount() {
        return releases.size();
    }

    //Filtro
    public void filter(final String strSearch, int choice) {

        if (strSearch.length() == 0) {
            releases.clear();
            releases.addAll(originalItems);
        } else {
            releases.clear();
            List<Release> collect;
            if (choice == 0) {
                collect = originalItems.stream()
                        .filter(i -> i.getTitulo().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());
            } else {
                collect = originalItems.stream()
                        .filter(i -> i.getCategoria().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());
            }
            releases.addAll(collect);
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
