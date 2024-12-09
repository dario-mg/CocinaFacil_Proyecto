package com.Dario.CocinaFacil.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.Dario.CocinaFacil.R;
import com.Dario.CocinaFacil.fragments.MostrarRecetaFragment;
import com.Dario.CocinaFacil.models.Receta;
import com.Dario.CocinaFacil.viewModel.AplicacionViewModel;

import java.util.List;

/**
 * The type Receta adapter.
 */
public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder> {

    private AplicacionViewModel aplicacionViewModel;
    private List<Receta> listaRecetas;
    private final Context context;

    /**
     * Instantiates a new Receta adapter.
     *
     * @param context             the context
     * @param listaRecetas        the lista recetas
     * @param aplicacionViewModel the aplicacion view model
     */
// Constructor actualizado para recibir el ViewModel
    public RecetaAdapter(Context context, List<Receta> listaRecetas, AplicacionViewModel aplicacionViewModel) {
        this.context = context;
        this.listaRecetas = listaRecetas;
        this.aplicacionViewModel = aplicacionViewModel;
    }

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño del item para cada receta
        View view = LayoutInflater.from(context).inflate(R.layout.item_receta, parent, false);

        // Pasamos el contexto y el ViewModel al ViewHolder
        return new RecetaViewHolder(view, context, aplicacionViewModel, listaRecetas);  // Ahora pasamos listaRecetas
    }

    @Override
    public void onBindViewHolder(@NonNull RecetaViewHolder holder, int position) {
        // Obtener la receta correspondiente
        Receta receta = listaRecetas.get(position);
        holder.nombreReceta.setText(receta.getNombreReceta());
        holder.descripcionReceta.setText(receta.getDescripcion());
        holder.imagenReceta.setImageBitmap(receta.getImagenReceta());

    }

    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }

    /**
     * Sets recetas.
     *
     * @param nuevasRecetas the nuevas recetas
     */
    public void setRecetas(List<Receta> nuevasRecetas) {
        this.listaRecetas = nuevasRecetas;
        notifyDataSetChanged();
    }

    /**
     * The type Receta view holder.
     */
// Clase ViewHolder que maneja el reciclaje de las vistas de cada item
    static class RecetaViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Imagen receta.
         */
        ImageView imagenReceta;
        /**
         * The Nombre receta.
         */
        TextView nombreReceta;
        /**
         * The Descripcion receta.
         */
        TextView descripcionReceta;
        /**
         * The Context.
         */
        Context context;
        /**
         * The Aplicacion view model.
         */
        AplicacionViewModel aplicacionViewModel;
        /**
         * The Lista recetas.
         */
        List<Receta> listaRecetas; // Lista de recetas que se pasará al ViewHolder

        /**
         * Instantiates a new Receta view holder.
         *
         * @param itemView     the item view
         * @param context      the context
         * @param viewModel    the view model
         * @param listaRecetas the lista recetas
         */
// Constructor del ViewHolder
        public RecetaViewHolder(@NonNull View itemView, Context context, AplicacionViewModel viewModel, List<Receta> listaRecetas) {
            super(itemView);
            this.context = context;
            this.aplicacionViewModel = viewModel;
            this.listaRecetas = listaRecetas; // Guardamos la lista de recetas

            imagenReceta = itemView.findViewById(R.id.imagen_receta);
            nombreReceta = itemView.findViewById(R.id.nombre_receta);
            descripcionReceta = itemView.findViewById(R.id.descripcion_receta);

            // Asignamos el listener de clic al itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtenemos la receta seleccionada
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Receta receta = listaRecetas.get(position); // Ahora accedemos correctamente a la lista
                        Log.d("probandocosas", "receta es una mierda "+receta);

                        // Introducimos la receta al ViewModel
                        aplicacionViewModel.ListaIngredientesReceta(receta);

                        // No es necesario pasarle nada al fragmento, ya que la receta está en el ViewModel
                        MostrarRecetaFragment mostrarRecetaFragment = new MostrarRecetaFragment(); // Instanciamos el fragmento

                        // Aseguramos que el contexto sea una instancia de AppCompatActivity
                        AppCompatActivity activity = (AppCompatActivity) context;

                        // Creamos una transacción de fragmentos para reemplazar el fragmento actual
                        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, mostrarRecetaFragment);
                        transaction.addToBackStack(null); // Añadimos a la pila de retroceso
                        transaction.commit(); // Ejecutamos la transacción
                    }
                }
            });
        }
    }

    /**
     * The interface On item click listener.
     */
    public interface OnItemClickListener {
        /**
         * On item click.
         *
         * @param receta the receta
         */
        void onItemClick(Receta receta);
    }
}
