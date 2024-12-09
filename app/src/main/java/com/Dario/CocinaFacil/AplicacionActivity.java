package com.Dario.CocinaFacil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.Dario.CocinaFacil.databinding.ActivityAplicacionBinding;
import com.Dario.CocinaFacil.viewModel.AplicacionViewModel;

/**
 * The type Aplicacion activity.
 */
public class AplicacionActivity extends AppCompatActivity {

    private ActivityAplicacionBinding binding;
    private AplicacionViewModel aplicacionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityAplicacionBinding.inflate(getLayoutInflater())).getRoot());

        String usuario = getIntent().getStringExtra("usuario");

        aplicacionViewModel = new ViewModelProvider(this).get(AplicacionViewModel.class);
        aplicacionViewModel.sesionUsuario(usuario);

        aplicacionViewModel.todasLasRecetas();


        if (savedInstanceState == null) {
            NavController navController = ((NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment)).getNavController();
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        }
    }
}
