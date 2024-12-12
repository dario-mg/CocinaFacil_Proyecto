package com.Dario.CocinaFacil.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Dario.CocinaFacil.R;
import com.Dario.CocinaFacil.models.Ingrediente;
import com.Dario.CocinaFacil.models.Receta;
import com.Dario.CocinaFacil.models.Usuario;
import com.Dario.CocinaFacil.viewModel.AplicacionViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Anadir fragment.
 */
public class AnadirFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private Bitmap imagenRecetaBitmap;

    private AplicacionViewModel aplicacionViewModel;

    private TextView etiquetaPrevisualizarImagen, verificarReceta;
    private TextInputEditText editarNombreReceta, editarDescripcionReceta, editarInstruccionesReceta;
    private Button botonIngredientes, botonAnadirImagen, botonGuardarReceta;
    private ImageView imagenRecetaPrevisualizar;
    private Receta receta;
    private Usuario usuarioSesion;
    private BottomNavigationView bottomNavigationView;

    /**
     * Instantiates a new Anadir fragment.
     */
    public AnadirFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anadir, container, false);

        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setVisibility(View.VISIBLE);

        editarNombreReceta = view.findViewById(R.id.editarNombreReceta);
        editarDescripcionReceta = view.findViewById(R.id.editarDescripcionReceta);
        editarInstruccionesReceta = view.findViewById(R.id.editarInstruccionesReceta);
        botonIngredientes = view.findViewById(R.id.botonIngredientes);
        botonAnadirImagen = view.findViewById(R.id.botonAnadirImagen);
        imagenRecetaPrevisualizar = view.findViewById(R.id.imagenRecetaPrevisualizar);
        botonGuardarReceta = view.findViewById(R.id.botonGuardarReceta);
        verificarReceta = view.findViewById(R.id.verificar_receta);

        etiquetaPrevisualizarImagen = view.findViewById(R.id.etiquetaPrevisualizarImagen);

        aplicacionViewModel = new ViewModelProvider(requireActivity()).get(AplicacionViewModel.class);

        // Observar los valores guardados en el ViewModel
        aplicacionViewModel.getNombreReceta().observe(getViewLifecycleOwner(), nombre -> {
            if (nombre != null) {
                editarNombreReceta.setText(nombre);
            }
        });

        aplicacionViewModel.getDescripcionReceta().observe(getViewLifecycleOwner(), descripcion -> {
            if (descripcion != null) {
                editarDescripcionReceta.setText(descripcion);
            }
        });

        aplicacionViewModel.getInstruccionesReceta().observe(getViewLifecycleOwner(), instrucciones -> {
            if (instrucciones != null) {
                editarInstruccionesReceta.setText(instrucciones);
            }
        });

        botonAnadirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        botonIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la pantalla de añadir ingredientes
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.anadirIngredientesFragment);

                // Guardar los datos de la receta en el ViewModel
                aplicacionViewModel.setNombreReceta(editarNombreReceta.getText().toString());
                aplicacionViewModel.setDescripcionReceta(editarDescripcionReceta.getText().toString());
                aplicacionViewModel.setInstruccionesReceta(editarInstruccionesReceta.getText().toString());
            }
        });

        aplicacionViewModel.getSesionUsuario().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    usuarioSesion = usuario;
                }
            }
        });

        // Acción para el botón de guardar
        botonGuardarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comprobar que los campos no estén vacíos
                if (editarNombreReceta.getText().toString().isEmpty() ||
                        editarDescripcionReceta.getText().toString().isEmpty() ||
                        editarInstruccionesReceta.getText().toString().isEmpty()) {

                    // Mostrar un mensaje de error si algún campo está vacío
                    Toast.makeText(getActivity(), "Todos los campos deben ser completados", Toast.LENGTH_SHORT).show();
                } else {
                    // Observar los ingredientes y guardar la receta solo cuando los ingredientes estén disponibles
                    aplicacionViewModel.getListaIngredientesAnadir().observe(getViewLifecycleOwner(), new Observer<List<Ingrediente>>() {
                        @Override
                        public void onChanged(List<Ingrediente> ingredientes) {
                            if (ingredientes != null && !ingredientes.isEmpty()) {
                                receta = new Receta(
                                        editarNombreReceta.getText().toString(),
                                        editarDescripcionReceta.getText().toString(),
                                        editarInstruccionesReceta.getText().toString(),
                                        usuarioSesion.getId(),
                                        imagenRecetaBitmap
                                );
                                aplicacionViewModel.introducirReceta(receta, ingredientes);

                                // Limpiar campos y resetear ViewModel
                                limpiarCamposYReceta();

                                // Actualizar el ViewModel para todas las recetas
                                aplicacionViewModel.todasLasRecetas();
                            } else {
                                Toast.makeText(getActivity(), "No hay ingredientes para guardar.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        // Observar si la receta se ha introducido correctamente
        aplicacionViewModel.getIntroducirReceta().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    verificarReceta.setText("RECETA INTRODUCIDA CORRECTAMENTE");
                    verificarReceta.setTextColor(Color.parseColor("#4CAF50"));
                    onResume();
                } else {
                    verificarReceta.setText("ERROR AL INTRODUCIR RECETA");
                    verificarReceta.setTextColor(Color.parseColor("#F44336"));
                }
                verificarReceta.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void limpiarCamposYReceta() {
        // Limpia los campos de texto
        editarNombreReceta.setText("");
        editarDescripcionReceta.setText("");
        editarInstruccionesReceta.setText("");

        // Oculta la imagen de previsualización
        imagenRecetaPrevisualizar.setVisibility(View.GONE);
        etiquetaPrevisualizarImagen.setVisibility(View.GONE);
        imagenRecetaBitmap = null;

        // Limpia la lista de ingredientes en el ViewModel
        aplicacionViewModel.ListaIngredientesAnadir(new ArrayList<>()); // Lista vacía

        // Notifica al usuario
        Toast.makeText(getActivity(), "Receta guardada y campos limpiados", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                // Convierte la URI seleccionada a un Bitmap
                imagenRecetaBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);

                // Muestra la imagen seleccionada en el ImageView de previsualización
                imagenRecetaPrevisualizar.setImageBitmap(imagenRecetaBitmap);
                imagenRecetaPrevisualizar.setVisibility(View.VISIBLE);
                etiquetaPrevisualizarImagen.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Limpiar los valores del ViewModel al regresar al fragmento de añadir receta
        aplicacionViewModel.setNombreReceta(null);
        aplicacionViewModel.setDescripcionReceta(null);
        aplicacionViewModel.setInstruccionesReceta(null);
    }

}
