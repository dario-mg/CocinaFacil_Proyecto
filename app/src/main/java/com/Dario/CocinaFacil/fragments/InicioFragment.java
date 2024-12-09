package com.Dario.CocinaFacil.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
                recetaAdapter.setRecetas(recetas);
            }
        });

        return view;
    }

}
