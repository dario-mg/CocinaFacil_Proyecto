package com.Dario.CocinaFacil.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.Dario.CocinaFacil.AplicacionActivity;
import com.Dario.CocinaFacil.R;
import com.Dario.CocinaFacil.models.Usuario;
import com.Dario.CocinaFacil.viewModel.VerificacionViewModel;

/**
 * The type Login fragment.
 */
public class LoginFragment extends Fragment {

    private VerificacionViewModel verificacionViewModel;
    private EditText campoUsuario, campoContrasena;
    private TextView texto_registro, textoCargando, textoResultado;
    private Button botonIniciarSesion;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        texto_registro = rootView.findViewById(R.id.texto_registro);
        campoUsuario = rootView.findViewById(R.id.campo_usuario);
        campoContrasena = rootView.findViewById(R.id.campo_contrasena);
        botonIniciarSesion = rootView.findViewById(R.id.boton_iniciar_sesion);
        progressBar = rootView.findViewById(R.id.progress_bar);  // ProgressBar
        textoResultado = rootView.findViewById(R.id.texto_cargando);  // Nuevo TextView para resultado

        verificacionViewModel = new ViewModelProvider(this).get(VerificacionViewModel.class);


        //ELIMINAR
        campoUsuario.setText("dario");
        campoContrasena.setText("dario");

        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreUsuario = campoUsuario.getText().toString();
                String contrasena = campoContrasena.getText().toString();

                if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
                    textoResultado.setVisibility(View.VISIBLE);
                    textoResultado.setText("Por favor, ingrese usuario y contraseña");
                    textoResultado.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    textoResultado.setVisibility(View.GONE);

                    Usuario usuario = new Usuario(nombreUsuario, contrasena);
                    verificacionViewModel.verificarUsuario(usuario);
                }
            }
        });

        verificacionViewModel.getUsuarioVerificadoLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean verificado) {
                progressBar.setVisibility(View.GONE);

                if (verificado) {
                    textoResultado.setVisibility(View.VISIBLE);
                    textoResultado.setText("Usuario y contraseña correctos");
                    textoResultado.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

                    // Iniciar la siguiente actividad
                    Intent intent = new Intent(getActivity(), AplicacionActivity.class);
                    intent.putExtra("usuario", campoUsuario.getText().toString()); // "usuario" es la clave, nombreUsuario es el valor
                    startActivity(intent);
                } else {
                    textoResultado.setVisibility(View.VISIBLE);
                    textoResultado.setText("Usuario o contraseña incorrectos");
                    textoResultado.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }
        });

        texto_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_logInFragment_to_singUpFragment);
            }
        });

        return rootView;
    }
}
