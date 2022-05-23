package com.app.superficcion;

import android.content.Context;
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

public class ReleaseAdapter extends RecyclerView.Adapter<ReleaseAdapter.MyViewHolder> {

    private ArrayList<Release> releases;
    private Context context;

    //Constructor
    public ReleaseAdapter(ArrayList<Release> releases, Context context) {
        this.releases = releases;
        this.context = context;
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
