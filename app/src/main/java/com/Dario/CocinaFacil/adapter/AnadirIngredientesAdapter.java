package com.Dario.CocinaFacil.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Dario.CocinaFacil.R;
import com.Dario.CocinaFacil.models.Ingrediente;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Anadir ingredientes adapter.
 */
public class AnadirIngredientesAdapter extends RecyclerView.Adapter<AnadirIngredientesAdapter.ViewHolder> {
    private List<Ingrediente> listaIngredientes;

    /**
     * Instantiates a new Anadir ingredientes adapter.
     *
     * @param listaIngredientes the lista ingredientes
     */
// Constructor del adaptador
    public AnadirIngredientesAdapter(List<Ingrediente> listaIngredientes) {
        this.listaIngredientes = listaIngredientes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar la vista del item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_introducir_ingrediente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Asignar los datos al ViewHolder
        Ingrediente ingrediente = listaIngredientes.get(position);
        holder.nombreIngrdiente.setText(ingrediente.getNombreIngrdiente());

        // Remover listeners existentes antes de configurar el campo
        if (holder.cantidad.getTag() != null) {
            holder.cantidad.removeTextChangedListener((TextWatcher) holder.cantidad.getTag());
        }

        // Configurar el InputEditText con el valor de cantidad
        holder.cantidad.setText(ingrediente.getCantidad());

        // Crear un TextWatcher para actualizar el modelo de datos
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Actualizar la cantidad del ingrediente
                ingrediente.setCantidad(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // Agregar el listener y almacenar como tag
        holder.cantidad.addTextChangedListener(watcher);
        holder.cantidad.setTag(watcher);
    }


    @Override
    public int getItemCount() {
        return listaIngredientes.size();
    }

    /**
     * Update list.
     *
     * @param newList the new list
     */
// Método para actualizar la lista de ingredientes
    public void updateList(List<Ingrediente> newList) {
        this.listaIngredientes.clear();
        this.listaIngredientes.addAll(newList);
        notifyDataSetChanged();
    }

    /**
     * The type View holder.
     */
// ViewHolder para el RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Nombre ingrdiente.
         */
        TextView nombreIngrdiente;
        /**
         * The Cantidad.
         */
        TextInputEditText cantidad;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            nombreIngrdiente = itemView.findViewById(R.id.nombreIngredienteAnadir);
            cantidad = itemView.findViewById(R.id.cantidadEditText);  // Suponiendo que este es el campo para cantidad
        }
    }
}
