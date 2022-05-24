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

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.MyViewHolder> {

    private final List<Option> optionList;
    private final Context context;
    private int selectedItem;

    public OptionAdapter(Context context, List<Option> optionList) {
        this.context = context;
        this.optionList = optionList;
        selectedItem = 0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OptionAdapter.MyViewHolder optionsHolder, @SuppressLint("RecyclerView") final int position) {
        optionsHolder.itemView.setTag(optionList.get(position));
        optionsHolder.opcion_id.setText(optionList.get(position).getName());
        optionsHolder.cardViewOpciones.setCardBackgroundColor(Color.parseColor("#3C3F41"));

        if (selectedItem == position) {
            optionsHolder.cardViewOpciones.setCardBackgroundColor(Color.BLUE);
        }

        optionsHolder.itemView.setOnClickListener((View v) -> {

            int previousItem = selectedItem;
            selectedItem = position;
            notifyItemChanged(previousItem);
            notifyItemChanged(position);

            switch (optionList.get(position).getName()) {
                case "TODO":
                    RSSReader.categoryFilter("");
                    CalendarReader.categoryFilter("");
                    MainActivity.recyclerView.scrollToPosition(0);
                    CalendarActivity.recyclerView.scrollToPosition(0);
                    Toast.makeText(context.getApplicationContext(), "Filtrando por: TODO", Toast.LENGTH_SHORT).show();
                    break;
                case "MARVEL":
                    RSSReader.categoryFilter("marvel");
                    CalendarReader.categoryFilter("marvel");
                    MainActivity.recyclerView.scrollToPosition(0);
                    CalendarActivity.recyclerView.scrollToPosition(0);
                    Toast.makeText(context.getApplicationContext(), "Filtrando por: MARVEL", Toast.LENGTH_SHORT).show();
                    break;
                case "DC":
                    RSSReader.categoryFilter("dc");
                    CalendarReader.categoryFilter("dc");
                    MainActivity.recyclerView.scrollToPosition(0);
                    CalendarActivity.recyclerView.scrollToPosition(0);
                    Toast.makeText(context.getApplicationContext(), "Filtrando por: DC", Toast.LENGTH_SHORT).show();
                    break;
                case "STAR WARS":
                    RSSReader.categoryFilter("star wars");
                    CalendarReader.categoryFilter("star wars");
                    MainActivity.recyclerView.scrollToPosition(0);
                    CalendarActivity.recyclerView.scrollToPosition(0);
                    Toast.makeText(context.getApplicationContext(), "Filtrando por: STAR WARS", Toast.LENGTH_SHORT).show();
                    break;
                case "SCI-FY/FANTASÍA":
                    RSSReader.categoryFilter("fantasía");
                    CalendarReader.categoryFilter("fantasía");
                    MainActivity.recyclerView.scrollToPosition(0);
                    CalendarActivity.recyclerView.scrollToPosition(0);
                    Toast.makeText(context.getApplicationContext(), "Filtrando por: SCI-FY/FANTASÍA", Toast.LENGTH_SHORT).show();
                    break;
                case "ANIME":
                    RSSReader.categoryFilter("anime");
                    CalendarReader.categoryFilter("anime");
                    MainActivity.recyclerView.scrollToPosition(0);
                    CalendarActivity.recyclerView.scrollToPosition(0);
                    Toast.makeText(context.getApplicationContext(), "Filtrando por: ANIME", Toast.LENGTH_SHORT).show();
                    break;
                case "CÓMIC":
                    RSSReader.categoryFilter("cómic");
                    CalendarReader.categoryFilter("cómic");
                    MainActivity.recyclerView.scrollToPosition(0);
                    CalendarActivity.recyclerView.scrollToPosition(0);
                    Toast.makeText(context.getApplicationContext(), "Filtrando por: CÓMIC", Toast.LENGTH_SHORT).show();
                    break;
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
            opcion_id = view.findViewById(R.id.opcion_id);
            cardViewOpciones = view.findViewById(R.id.cardViewOpciones);

        }
    }
}

