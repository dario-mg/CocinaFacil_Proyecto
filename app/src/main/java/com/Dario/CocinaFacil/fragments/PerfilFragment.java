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
import android.widget.TextView;

import com.Dario.CocinaFacil.R;
import com.Dario.CocinaFacil.models.Receta;
import com.Dario.CocinaFacil.adapter.RecetaAdapter;
import com.Dario.CocinaFacil.models.Usuario;
import com.Dario.CocinaFacil.viewModel.AplicacionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Perfil fragment.
 */
public class PerfilFragment extends Fragment {

    private AplicacionViewModel aplicacionViewModel;
    private RecyclerView recyclerView;
    private RecetaAdapter recetaAdapter;
    private TextView textoUsuario, textoCorreo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        textoUsuario = view.findViewById(R.id.nombre_usuario);
        textoCorreo = view.findViewById(R.id.correo_usuario);
        recyclerView = view.findViewById(R.id.lista_recetas);

        aplicacionViewModel = new ViewModelProvider(requireActivity()).get(AplicacionViewModel.class);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        recetaAdapter = new RecetaAdapter(getContext(), new ArrayList<Receta>(), aplicacionViewModel);
        recyclerView.setAdapter(recetaAdapter);


        aplicacionViewModel.getSesionUsuario().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                textoUsuario.setText(usuario.getNombreUsuario());
                textoCorreo.setText(usuario.getCorreo());
                aplicacionViewModel.recetasDelUsuario(usuario);
            }
        });


        aplicacionViewModel.getRecetasDelUsuario().observe(getViewLifecycleOwner(), new Observer<List<Receta>>() {
            @Override
            public void onChanged(List<Receta> recetas) {
                recetaAdapter.setRecetas(recetas);
            }
        });


        return view;
    }
}
