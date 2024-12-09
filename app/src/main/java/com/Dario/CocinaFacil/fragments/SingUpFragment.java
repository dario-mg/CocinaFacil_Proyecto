package com.Dario.CocinaFacil.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.Dario.CocinaFacil.AplicacionActivity;
import com.Dario.CocinaFacil.R;
import com.Dario.CocinaFacil.models.Usuario;
import com.Dario.CocinaFacil.viewModel.VerificacionViewModel;

/**
 * The type Sing up fragment.
 */
public class SingUpFragment extends Fragment {

    private VerificacionViewModel verificacionViewModel;
    private EditText campoUsuario, campoContrasena, campoCorreo;
    private TextView texto_login, mensajeRegistro;
    private Button botonRegistrarse;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sing_up, container, false);

        // Inicializar vistas
        texto_login = rootView.findViewById(R.id.texto_inicio_sesion);
        campoUsuario = rootView.findViewById(R.id.campo_usuario);
        campoContrasena = rootView.findViewById(R.id.campo_contrasena);
        campoCorreo = rootView.findViewById(R.id.campo_correo);
        botonRegistrarse = rootView.findViewById(R.id.boton_registro);
        mensajeRegistro = rootView.findViewById(R.id.mensaje_registro);
        progressBar = rootView.findViewById(R.id.progress_bar);

        verificacionViewModel = new ViewModelProvider(this).get(VerificacionViewModel.class);

        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreUsuario = campoUsuario.getText().toString();
                String contrasena = campoContrasena.getText().toString();
                String correo = campoCorreo.getText().toString();

                if (nombreUsuario.isEmpty() || contrasena.isEmpty() || correo.isEmpty()) {
                    mensajeRegistro.setText("Por favor, rellena todos los campos.");
                    mensajeRegistro.setTextColor(ContextCompat.getColor(requireContext(), R.color.error_red));
                    mensajeRegistro.setVisibility(View.VISIBLE);
                } else {
                    mensajeRegistro.setVisibility(View.GONE); // Ocultar mensaje anterior
                    progressBar.setVisibility(View.VISIBLE); // Mostrar barra de carga
                    Usuario usuario = new Usuario(nombreUsuario, contrasena, correo);
                    verificacionViewModel.registroUsuario(usuario);
                }
            }
        });

        verificacionViewModel.getUsuarioRegistradoLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean verificado) {
                Log.e("VerificacionModel", "Usuario" + verificado);

                progressBar.setVisibility(View.GONE); // Ocultar barra de carga

                // Mostrar el mensaje correspondiente
                if (verificado) {
                    mensajeRegistro.setText("¡Tu cuenta ha sido creada correctamente!");
                    mensajeRegistro.setTextColor(ContextCompat.getColor(requireContext(), R.color.success_green));
                    // Iniciar la siguiente actividad
                    Intent intent = new Intent(getActivity(), AplicacionActivity.class);
                    intent.putExtra("usuario", campoUsuario.getText().toString()); // "usuario" es la clave, nombreUsuario es el valor
                    startActivity(intent);
                } else {
                    mensajeRegistro.setText("Este usuario ya ha sido registrado.");
                    mensajeRegistro.setTextColor(ContextCompat.getColor(requireContext(), R.color.error_red));
                }

                mensajeRegistro.setVisibility(View.VISIBLE);

                // Limpiar los campos de texto
                campoUsuario.setText("");
                campoContrasena.setText("");
                campoCorreo.setText("");
            }
        });

        texto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(SingUpFragment.this);
                navController.navigate(R.id.action_singUpFragment_to_logInFragment);
            }
        });

        return rootView;
    }
}
