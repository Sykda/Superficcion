package com.app.superficcion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.MyViewHolder>  {

    private List<Opciones> optionList;
    private Context context;
    private static int lastClickedPosition = -1;
    private int selectedItem;

    public OptionAdapter(Context context, List<Opciones> petsList) {
        this.context = context;
        this.optionList = petsList;
        selectedItem = 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OptionAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.itemView.setTag(optionList.get(position));
        holder.opcion_id.setText(optionList.get(position).getName());
        holder.cardViewOpciones.setCardBackgroundColor(Color.parseColor("#3C3F41"));

        if (selectedItem == position) {
            holder.cardViewOpciones.setCardBackgroundColor(Color.BLUE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousItem = selectedItem;
                selectedItem = position;
                notifyItemChanged(previousItem);
                notifyItemChanged(position);

               switch (optionList.get(position).getName()){
                   case "TODO":
                       LectorRSS.initCategoryfilter("");
                       MainActivity.recyclerView.scrollToPosition(0);
                       Toast.makeText(context.getApplicationContext(), "Filtrando por: TODO", Toast.LENGTH_SHORT).show();
                       break;
                   case "MARVEL":
                       LectorRSS.initCategoryfilter("marvel");
                       MainActivity.recyclerView.scrollToPosition(0);
                       Toast.makeText(context.getApplicationContext(), "Filtrando por: MARVEL", Toast.LENGTH_SHORT).show();
                       break;
                   case "DC":
                       LectorRSS.initCategoryfilter("dc");
                       MainActivity.recyclerView.scrollToPosition(0);
                       Toast.makeText(context.getApplicationContext(), "Filtrando por: DC", Toast.LENGTH_SHORT).show();
                       break;
                   case "STAR WARS":
                       LectorRSS.initCategoryfilter("star wars");
                       MainActivity.recyclerView.scrollToPosition(0);
                       Toast.makeText(context.getApplicationContext(), "Filtrando por: STAR WARS", Toast.LENGTH_SHORT).show();
                       break;
                   case "SCI-FY/FANTASÍA":
                       LectorRSS.initCategoryfilter("fantasía");
                       MainActivity.recyclerView.scrollToPosition(0);
                       Toast.makeText(context.getApplicationContext(), "Filtrando por: SCI-FY/FANTASÍA", Toast.LENGTH_SHORT).show();
                       break;
                   case "ANIME":
                       LectorRSS.initCategoryfilter("anime");
                       MainActivity.recyclerView.scrollToPosition(0);
                       Toast.makeText(context.getApplicationContext(), "Filtrando por: ANIME", Toast.LENGTH_SHORT).show();
                       break;
                   case "CÓMIC":
                       LectorRSS.initCategoryfilter("cómic");
                       MainActivity.recyclerView.scrollToPosition(0);
                       Toast.makeText(context.getApplicationContext(), "Filtrando por: CÓMIC", Toast.LENGTH_SHORT).show();
                       break;


               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView opcion_id;
        public CardView cardViewOpciones;

        public MyViewHolder(View view) {
            super(view);
            opcion_id = (TextView) view.findViewById(R.id.opcion_id);
            cardViewOpciones = (CardView) view.findViewById(R.id.cardViewOpciones);

        }
    }
}

