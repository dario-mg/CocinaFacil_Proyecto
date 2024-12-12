package com.Dario.CocinaFacil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Dario.CocinaFacil.databinding.ActivityAplicacionBinding;
import com.Dario.CocinaFacil.viewModel.AplicacionViewModel;
import com.google.android.material.navigation.NavigationView;

/**
 * The type Aplicacion activity.
 */
public class AplicacionActivity extends AppCompatActivity {

    private ActivityAplicacionBinding binding;
    private AplicacionViewModel aplicacionViewModel;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityAplicacionBinding.inflate(getLayoutInflater())).getRoot());

        String usuario = getIntent().getStringExtra("usuario");

        aplicacionViewModel = new ViewModelProvider(this).get(AplicacionViewModel.class);
        aplicacionViewModel.sesionUsuario(usuario);
        aplicacionViewModel.todasLasRecetas();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navLateral);

        // Configurar el NavigationController para el NavHostFragment
        if (savedInstanceState == null) {
            NavController navController = ((NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment)).getNavController();
            // Asociar el NavController con el BottomNavigationView
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
            // Asociar el NavController con el NavigationView
            NavigationUI.setupWithNavController(navigationView, navController);
        }

        // Configurar las acciones de los elementos del menú lateral
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    private boolean onNavigationItemSelected(MenuItem item) {
        NavController navController = ((NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment)).getNavController();  // Usar NavHostFragment para obtener el NavController

        switch (item.getItemId()) {
            case R.id.logoutActivity:
                Intent intent = new Intent(AplicacionActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.inicioFragment:
                navController.navigate(R.id.inicioFragment);
                break;
            case R.id.anadirFragment:
                navController.navigate(R.id.anadirFragment);
                break;
            case R.id.perfilFragment:
                navController.navigate(R.id.perfilFragment);
                break;
        }

        drawerLayout.closeDrawer(navigationView);
        return true;
    }
}
