package com.Dario.CocinaFacil.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Dario.CocinaFacil.R;
import com.Dario.CocinaFacil.models.Ingrediente;
import com.Dario.CocinaFacil.models.Receta;
import com.Dario.CocinaFacil.adapter.RecetaAdapter;
import com.Dario.CocinaFacil.viewModel.AplicacionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Inicio fragment.
 */
public class InicioFragment extends Fragment {

    private AplicacionViewModel aplicacionViewModel;
    private RecyclerView recyclerView;
    private RecetaAdapter recetaAdapter;
    private SearchView buscadorRecetas;
    private List<Receta> listaReceta;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        recyclerView = view.findViewById(R.id.listaTodasRecetas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        aplicacionViewModel = new ViewModelProvider(requireActivity()).get(AplicacionViewModel.class);

        recetaAdapter = new RecetaAdapter(getContext(), new ArrayList<Receta>(), aplicacionViewModel);

        recyclerView.setAdapter(recetaAdapter);


        aplicacionViewModel.getTodasLasRecetas().observe(getViewLifecycleOwner(), new Observer<List<Receta>>() {
            @Override
            public void onChanged(List<Receta> recetas) {
                listaReceta = recetas;
                recetaAdapter.setRecetas(recetas);
            }
        });

        // Configurar el SearchView
        buscadorRecetas = view.findViewById(R.id.buscadorRecetas);
        buscadorRecetas.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListByNombreIngrediente(newText);
                return true;
            }
        });

        return view;
    }

    private void filterListByNombreIngrediente(String query) {
        List<Receta> filteredList = new ArrayList<>();
        if (query != null && !query.isEmpty()) {
            for (Receta ingrediente : listaReceta) {
                // Filtra por nombre del ingrediente, ignorando mayúsculas/minúsculas
                if (ingrediente.getNombreReceta().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(ingrediente);
                }
            }
        } else {
            // Si el texto de búsqueda está vacío, mostramos todos los ingredientes
            filteredList = listaReceta;
        }
        // Actualizar la lista filtrada en el adaptador
        recetaAdapter.updateList(filteredList);
    }

}
