package com.Dario.CocinaFacil.models;

import android.graphics.Bitmap;

/**
 * The type Receta.
 */
public class Receta {
    private int idReceta;
    private String nombreReceta;
    private String descripcion;
    private String instrucciones;
    private int idUsuario;
    private Bitmap imagenReceta;

    /**
     * Instantiates a new Receta.
     *
     * @param nombreReceta the nombre receta
     * @param descripcion  the descripcion
     */
    public Receta(String nombreReceta, String descripcion) {
        this.nombreReceta = nombreReceta;
        this.descripcion = descripcion;
    }

    /**
     * Instantiates a new Receta.
     *
     * @param nombreReceta  the nombre receta
     * @param descripcion   the descripcion
     * @param instrucciones the instrucciones
     * @param idUsuario     the id usuario
     * @param imagenReceta  the imagen receta
     */
    public Receta(String nombreReceta, String descripcion, String instrucciones, int idUsuario, Bitmap imagenReceta) {
        this.nombreReceta = nombreReceta;
        this.descripcion = descripcion;
        this.instrucciones = instrucciones;
        this.idUsuario = idUsuario;
        this.imagenReceta = imagenReceta;
    }

    /**
     * Instantiates a new Receta.
     *
     * @param idReceta      the id receta
     * @param nombreReceta  the nombre receta
     * @param descripcion   the descripcion
     * @param instrucciones the instrucciones
     * @param idUsuario     the id usuario
     * @param imagenReceta  the imagen receta
     */
    public Receta(int idReceta, String nombreReceta, String descripcion, String instrucciones, int idUsuario, Bitmap imagenReceta) {
        this.idReceta = idReceta;
        this.nombreReceta = nombreReceta;
        this.descripcion = descripcion;
        this.instrucciones = instrucciones;
        this.idUsuario = idUsuario;
        this.imagenReceta = imagenReceta;
    }

    /**
     * Gets id receta.
     *
     * @return the id receta
     */
    public int getIdReceta() {
        return idReceta;
    }

    /**
     * Sets id receta.
     *
     * @param idReceta the id receta
     */
    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    /**
     * Gets nombre receta.
     *
     * @return the nombre receta
     */
    public String getNombreReceta() {
        return nombreReceta;
    }

    /**
     * Sets nombre receta.
     *
     * @param nombreReceta the nombre receta
     */
    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    /**
     * Gets descripcion.
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets descripcion.
     *
     * @param descripcion the descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets instrucciones.
     *
     * @return the instrucciones
     */
    public String getInstrucciones() {
        return instrucciones;
    }

    /**
     * Sets instrucciones.
     *
     * @param instrucciones the instrucciones
     */
    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    /**
     * Gets id usuario.
     *
     * @return the id usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Sets id usuario.
     *
     * @param idUsuario the id usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Gets imagen receta.
     *
     * @return the imagen receta
     */
    public Bitmap getImagenReceta() {
        return imagenReceta;
    }

    /**
     * Sets imagen receta.
     *
     * @param imagenReceta the imagen receta
     */
    public void setImagenReceta(Bitmap imagenReceta) {
        this.imagenReceta = imagenReceta;
    }
}
