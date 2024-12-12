package com.Dario.CocinaFacil.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.Dario.CocinaFacil.R;
import com.Dario.CocinaFacil.adapter.AnadirIngredientesAdapter;
import com.Dario.CocinaFacil.models.Ingrediente;
import com.Dario.CocinaFacil.viewModel.AplicacionViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Anadir ingredientes fragment.
 */
public class AnadirIngredientesFragment extends Fragment {

    private AplicacionViewModel aplicacionViewModel;
    private RecyclerView recyclerView;
    private AnadirIngredientesAdapter adapter;
    private List<Ingrediente> listaIngredientes;
    private BottomNavigationView bottomNavigationView;
    private SearchView buscadorIngredientes;

    /**
     * Instantiates a new Anadir ingredientes fragment.
     */
    public AnadirIngredientesFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout
        View view = inflater.inflate(R.layout.fragment_anadir_ingredientes, container, false);

        // Inicializar el BottomNavigationView
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setVisibility(View.GONE);

        // Inicializar el RecyclerView
        recyclerView = view.findViewById(R.id.lista_ingredientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar el ViewModel
        aplicacionViewModel = new ViewModelProvider(requireActivity()).get(AplicacionViewModel.class);

        // Llamar al método para cargar los ingredientes
        aplicacionViewModel.ListaTodosLosIngredientes();

        // Observar la lista de ingredientes desde el ViewModel
        aplicacionViewModel.getTodosLosIngredientes().observe(getViewLifecycleOwner(), new Observer<List<Ingrediente>>() {
            @Override
            public void onChanged(List<Ingrediente> ingredientes) {
                listaIngredientes = ingredientes;
                if (adapter == null) {
                    // Si el adaptador aún no está creado, lo creamos
                    adapter = new AnadirIngredientesAdapter(listaIngredientes);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Si el adaptador ya está creado, solo actualizamos la lista
                    adapter.updateList(listaIngredientes);
                }
            }
        });

        // Configurar el botón "Volver"
        Button volverButton = view.findViewById(R.id.button);
        volverButton.setOnClickListener(v -> {
            ingredientesCantidadReceta();
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.anadirFragment);
        });

        // Configurar el SearchView
        buscadorIngredientes = view.findViewById(R.id.buscador_ingredientes);
        buscadorIngredientes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void ingredientesCantidadReceta() {
        List<Ingrediente> filteredList = new ArrayList<>();
        for (Ingrediente ingrediente : listaIngredientes) {
            // Comprobar si la cantidad es nula o está vacía
            if (ingrediente.getCantidad() != null && !ingrediente.getCantidad().isEmpty()) {
                filteredList.add(ingrediente);
            }
        }
        // Actualizar el ViewModel con la lista filtrada
        aplicacionViewModel.ListaIngredientesAnadir(filteredList);
    }

    private void filterListByNombreIngrediente(String query) {
        List<Ingrediente> filteredList = new ArrayList<>();
        if (query != null && !query.isEmpty()) {
            for (Ingrediente ingrediente : listaIngredientes) {
                // Filtra por nombre del ingrediente, ignorando mayúsculas/minúsculas
                if (ingrediente.getNombreIngrdiente().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(ingrediente);
                }
            }
        } else {
            // Si el texto de búsqueda está vacío, mostramos todos los ingredientes
            filteredList = listaIngredientes;
        }
        // Actualizar la lista filtrada en el adaptador
        adapter.updateList(filteredList);
    }
}
