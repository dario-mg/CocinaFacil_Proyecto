package com.Dario.CocinaFacil.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Dario.CocinaFacil.R;
import com.Dario.CocinaFacil.models.Ingrediente;

import java.util.List;

/**
 * The type Ingrediente adapter.
 */
public class IngredienteAdapter extends RecyclerView.Adapter<IngredienteAdapter.IngredienteViewHolder> {

    private List<Ingrediente> ingredientes;

    /**
     * Instantiates a new Ingrediente adapter.
     *
     * @param ingredientes the ingredientes
     */
// Constructor
    public IngredienteAdapter(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    // Crear el ViewHolder para el RecyclerView
    @Override
    public IngredienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingrdiente, parent, false);
        return new IngredienteViewHolder(itemView);
    }

    // Enlazar los datos del ingrediente al ViewHolder
    @Override
    public void onBindViewHolder(IngredienteViewHolder holder, int position) {
        Ingrediente ingrediente = ingredientes.get(position);
        holder.nombreIngrdiente.setText(ingrediente.getNombreIngrdiente());
        holder.cantidad.setText(ingrediente.getCantidad());
    }

    @Override
    public int getItemCount() {
        return ingredientes.size();
    }

    /**
     * The type Ingrediente view holder.
     */
// ViewHolder que define los elementos a enlazar
    public static class IngredienteViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Nombre ingrdiente.
         */
        public TextView nombreIngrdiente;
        /**
         * The Cantidad.
         */
        public TextView cantidad;
        /**
         * The Card view.
         */
        public CardView cardView;

        /**
         * Instantiates a new Ingrediente view holder.
         *
         * @param itemView the item view
         */
        public IngredienteViewHolder(View itemView) {
            super(itemView);
            nombreIngrdiente = itemView.findViewById(R.id.nombreIngrdiente);
            cantidad = itemView.findViewById(R.id.cantidad);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
