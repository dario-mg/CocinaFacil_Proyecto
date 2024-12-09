package com.Dario.CocinaFacil.viewModel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Dario.CocinaFacil.models.Ingrediente;
import com.Dario.CocinaFacil.models.Receta;
import com.Dario.CocinaFacil.models.Usuario;
import com.Dario.CocinaFacil.model.AplicacionModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The type Aplicacion view model.
 */
public class AplicacionViewModel extends AndroidViewModel {

    private MutableLiveData<Usuario> sesionUsuario;
    private MutableLiveData<List<Receta>> recetasDelUsuario, todasLasRecetas;
    private MutableLiveData<Boolean> introducirReceta;
    private MutableLiveData<List<Ingrediente>> listaIngredientesReceta, listaIngredientesAnadir;
    private MutableLiveData<Receta> recetaDelRecycler;
    private MutableLiveData<List<Ingrediente>> todosLosIngredientes;
    private AplicacionModel aplicacionModel;
    private MutableLiveData<String> nombreReceta;
    private MutableLiveData<String> descripcionReceta;
    private MutableLiveData<String> instruccionesReceta;
    private MutableLiveData<Bitmap> imagenReceta;
    private Executor executor;

    /**
     * Instantiates a new Aplicacion view model.
     *
     * @param application the application
     */
    public AplicacionViewModel(@NonNull Application application) {
        super(application);
        sesionUsuario = new MutableLiveData<>();
        recetasDelUsuario = new MutableLiveData<>();
        introducirReceta = new MutableLiveData<>();
        todasLasRecetas = new MutableLiveData<>();
        listaIngredientesReceta = new MutableLiveData<>();
        listaIngredientesAnadir = new MutableLiveData<>();
        recetaDelRecycler = new MutableLiveData<>();
        todosLosIngredientes = new MutableLiveData<>();
        nombreReceta = new MutableLiveData<>();
        descripcionReceta = new MutableLiveData<>();
        instruccionesReceta = new MutableLiveData<>();
        imagenReceta = new MutableLiveData<>();

        aplicacionModel = new AplicacionModel();

        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Gets sesion usuario.
     *
     * @return the sesion usuario
     */
    public LiveData<Usuario> getSesionUsuario() {
        return sesionUsuario;
    }

    /**
     * Gets recetas del usuario.
     *
     * @return the recetas del usuario
     */
    public LiveData<List<Receta>> getRecetasDelUsuario() {
        return recetasDelUsuario;
    }

    /**
     * Gets introducir receta.
     *
     * @return the introducir receta
     */
    public LiveData<Boolean> getIntroducirReceta() {
        return introducirReceta;
    }

    /**
     * Gets todas las recetas.
     *
     * @return the todas las recetas
     */
    public LiveData<List<Receta>> getTodasLasRecetas() {
        return todasLasRecetas;
    }

    /**
     * Gets lista ingredientes receta.
     *
     * @return the lista ingredientes receta
     */
    public LiveData<List<Ingrediente>> getListaIngredientesReceta() {
        return listaIngredientesReceta;
    }

    /**
     * Gets receta del recycler.
     *
     * @return the receta del recycler
     */
    public LiveData<Receta> getRecetaDelRecycler() {
        return recetaDelRecycler;
    }

    /**
     * Gets todos los ingredientes.
     *
     * @return the todos los ingredientes
     */
    public LiveData<List<Ingrediente>> getTodosLosIngredientes() {
        return todosLosIngredientes;
    }

    /**
     * Gets lista ingredientes anadir.
     *
     * @return the lista ingredientes anadir
     */
    public LiveData<List<Ingrediente>> getListaIngredientesAnadir() {
        return listaIngredientesAnadir;
    }


    /**
     * Gets nombre receta.
     *
     * @return the nombre receta
     */
    public LiveData<String> getNombreReceta() {
        return nombreReceta;
    }

    /**
     * Gets descripcion receta.
     *
     * @return the descripcion receta
     */
    public LiveData<String> getDescripcionReceta() {
        return descripcionReceta;
    }

    /**
     * Gets instrucciones receta.
     *
     * @return the instrucciones receta
     */
    public LiveData<String> getInstruccionesReceta() {
        return instruccionesReceta;
    }

    /**
     * Gets imagen receta.
     *
     * @return the imagen receta
     */
    public LiveData<Bitmap> getImagenReceta() {
        return imagenReceta;
    }

    /**
     * Sets nombre receta.
     *
     * @param nombre the nombre
     */
    public void setNombreReceta(String nombre) {
        nombreReceta.setValue(nombre);
    }

    /**
     * Sets descripcion receta.
     *
     * @param descripcion the descripcion
     */
    public void setDescripcionReceta(String descripcion) {
        descripcionReceta.setValue(descripcion);
    }

    /**
     * Sets instrucciones receta.
     *
     * @param instrucciones the instrucciones
     */
    public void setInstruccionesReceta(String instrucciones) {
        instruccionesReceta.setValue(instrucciones);
    }

    /**
     * Sets imagen receta.
     *
     * @param imagen the imagen
     */
    public void setImagenReceta(Bitmap imagen) {
        imagenReceta.setValue(imagen);
    }


    /**
     * Sesion usuario.
     *
     * @param nombreUsuario the nombre usuario
     */
    public void sesionUsuario(final String nombreUsuario) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                aplicacionModel.usuarioSesion(nombreUsuario, new AplicacionModel.CallbackSesionUsuario() {
                    @Override
                    public void sesionUsuario(Usuario usuario) {
                        sesionUsuario.postValue(usuario);
                    }
                });
            }
        });
    }

    /**
     * Recetas del usuario.
     *
     * @param usuario the usuario
     */
    public void recetasDelUsuario(final Usuario usuario) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                aplicacionModel.recetasUsuario(usuario, new AplicacionModel.CallbackRecetasUsuario() {
                    @Override
                    public void recetasUsuario(List<Receta> recetas) {
                        recetasDelUsuario.postValue(recetas);
                    }
                });
            }
        });
    }

    /**
     * Introducir receta.
     *
     * @param receta       the receta
     * @param ingredientes the ingredientes
     */
    public void introducirReceta(final Receta receta, final List<Ingrediente> ingredientes) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                aplicacionModel.introducirReceta(receta, ingredientes, new AplicacionModel.CallbackIntroducirReceta() {
                    @Override
                    public void recetaIntroducida(Boolean introducido) {
                        introducirReceta.postValue(introducido);
                    }
                });
            }
        });
    }

    /**
     * Todas las recetas.
     */
    public void todasLasRecetas() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                aplicacionModel.todasLasRecetas(new AplicacionModel.CallbackTodasLasRecetas() {
                    @Override
                    public void todasLasRecetas(List<Receta> recetas) {
                        todasLasRecetas.postValue(recetas);
                    }
                });
            }
        });
    }

    /**
     * Lista ingredientes receta.
     *
     * @param receta the receta
     */
    public void ListaIngredientesReceta(final Receta receta) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                aplicacionModel.datosDeLasRecetas(receta, new AplicacionModel.CallbackDatosDeLaReceta() {
                    @Override
                    public void datosDeLaReceta(Receta receta) {
                        recetaDelRecycler.postValue(receta);
                    }

                    @Override
                    public void datosDeLosIngrdientes(List<Ingrediente> ingredietes) {
                        listaIngredientesReceta.postValue(ingredietes);
                    }
                });
            }
        });
    }

    /**
     * Lista todos los ingredientes.
     */
    public void ListaTodosLosIngredientes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                aplicacionModel.todosLosIngredientes(new AplicacionModel.CallbackTodosLosIngrdientes() {
                    @Override
                    public void todosLosIngredientes(List<Ingrediente> listaIngrdientes) {
                        todosLosIngredientes.postValue(listaIngrdientes);
                    }
                });
            }
        });
    }

    /**
     * Lista ingredientes anadir.
     *
     * @param ingredientes the ingredientes
     */
    public void ListaIngredientesAnadir(List<Ingrediente> ingredientes) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                aplicacionModel.listaIngredientesAnadir(ingredientes, new AplicacionModel.CallbackListaIngredietesAnadir() {
                    @Override
                    public void listaIngredientesAnadir(List<Ingrediente> listaIngedientesAnadir) {
                        listaIngredientesAnadir.postValue(listaIngedientesAnadir);
                    }
                });
            }
        });
    }




}


