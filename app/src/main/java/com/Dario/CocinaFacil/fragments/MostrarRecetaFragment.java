package com.Dario.CocinaFacil.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Dario.CocinaFacil.R;
import com.Dario.CocinaFacil.adapter.IngredienteAdapter;
import com.Dario.CocinaFacil.models.Ingrediente;
import com.Dario.CocinaFacil.models.Receta;
import com.Dario.CocinaFacil.viewModel.AplicacionViewModel;

import java.util.List;

/**
 * The type Mostrar receta fragment.
 */
public class MostrarRecetaFragment extends Fragment {

    private AplicacionViewModel aplicacionViewModel;
    private Receta receta;

    // Vistas del fragmento
    private ImageView imagenReceta;
    private TextView nombreReceta;
    private TextView descripcionReceta;
    private TextView preparacionText;
    private RecyclerView ingredientesRecyclerView;
    private IngredienteAdapter ingredienteAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_receta, container, false);

        // Inicializamos las vistas
        imagenReceta = view.findViewById(R.id.imagen_receta);
        nombreReceta = view.findViewById(R.id.nombre_receta);
        descripcionReceta = view.findViewById(R.id.descripcion_receta);
        preparacionText = view.findViewById(R.id.descripcion_preparacion);
        ingredientesRecyclerView = view.findViewById(R.id.recycler_ingredientes);

        // Inicializamos RecyclerView
        ingredientesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtenemos el ViewModel
        aplicacionViewModel = new ViewModelProvider(requireActivity()).get(AplicacionViewModel.class);

        // Observamos los cambios en la receta
        aplicacionViewModel.getRecetaDelRecycler().observe(getViewLifecycleOwner(), new Observer<Receta>() {
            @Override
            public void onChanged(Receta receta) {
                Log.d("probandocosas", "Esta no? " + receta);

                // Actualizamos la receta con los nuevos datos
                if (receta != null) {
                    MostrarRecetaFragment.this.receta = receta;

                    // Actualizamos las vistas con los datos de la receta
                    nombreReceta.setText(receta.getNombreReceta());
                    descripcionReceta.setText(receta.getDescripcion());
                    imagenReceta.setImageBitmap(receta.getImagenReceta()); // Si la receta tiene una imagen

                    // Observamos la lista de ingredientes
                    aplicacionViewModel.getListaIngredientesReceta().observe(getViewLifecycleOwner(), new Observer<List<Ingrediente>>() {
                        @Override
                        public void onChanged(List<Ingrediente> ingredientes) {
                            // Actualizamos el Adapter con la lista de ingredientes
                            ingredienteAdapter = new IngredienteAdapter(ingredientes);
                            ingredientesRecyclerView.setAdapter(ingredienteAdapter);
                        }
                    });

                    // Mostramos la preparación de la receta
                    preparacionText.setText(receta.getInstrucciones());
                } else {
                    Log.d("probandocosas", "receta es una mierda " + receta);
                }
            }
        });

        return view;
    }
}
