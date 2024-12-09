package com.Dario.CocinaFacil.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Dario.CocinaFacil.models.Usuario;
import com.Dario.CocinaFacil.model.VerificacionModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The type Verificacion view model.
 */
public class VerificacionViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> usuarioRegistradoLiveData;
    private MutableLiveData<Boolean> usuarioVerificadoLiveData;
    private VerificacionModel verificacionModel;
    private Executor executor;

    /**
     * Instantiates a new Verificacion view model.
     *
     * @param application the application
     */
    public VerificacionViewModel(Application application) {
        super(application);
        usuarioRegistradoLiveData = new MutableLiveData<>();
        usuarioVerificadoLiveData = new MutableLiveData<>();
        verificacionModel = new VerificacionModel();


        // Inicializa el Executor con un hilo en el pool
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Gets usuario registrado live data.
     *
     * @return the usuario registrado live data
     */
    public LiveData<Boolean> getUsuarioRegistradoLiveData() {
        return usuarioRegistradoLiveData;
    }

    /**
     * Gets usuario verificado live data.
     *
     * @return the usuario verificado live data
     */
    public LiveData<Boolean> getUsuarioVerificadoLiveData() {
        return usuarioVerificadoLiveData;
    }


    /**
     * Registro usuario.
     *
     * @param usuario the usuario
     */
    public void registroUsuario(Usuario usuario) {
        VerificacionModel.InicioSesion registroSesion = new VerificacionModel.InicioSesion(usuario);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                verificacionModel.introducirUsuario(registroSesion, new VerificacionModel.CallbackUsuarioRegistrado() {
                    @Override
                    public void usuarioRegistrado(Boolean verificado) {
                        usuarioRegistradoLiveData.postValue(verificado);
                    }
                });
            }
        });
    }

    /**
     * Verificar usuario.
     *
     * @param usuario the usuario
     */
    public void verificarUsuario(Usuario usuario) {
        VerificacionModel.InicioSesion inicioSesion = new VerificacionModel.InicioSesion(usuario);
        // Ejecuta la tarea en un hilo del Executor
        executor.execute(new Runnable() {
            @Override
            public void run() {
                verificacionModel.verificarUsuario(inicioSesion, new VerificacionModel.CallbackUsuarioComprobado() {
                    @Override
                    public void usuarioComprobado(Boolean comprobado) {
                        usuarioVerificadoLiveData.postValue(comprobado);
                    }
                });
            }
        });
    }
}