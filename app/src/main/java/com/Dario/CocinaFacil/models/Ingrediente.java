package com.Dario.CocinaFacil.models;

/**
 * The type Ingrediente.
 */
public class Ingrediente {

    private int idIngrediente;
    private String nombreIngrdiente;
    private String cantidad;

    /**
     * Instantiates a new Ingrediente.
     *
     * @param idIngrediente    the id ingrediente
     * @param nombreIngrdiente the nombre ingrdiente
     * @param cantidad         the cantidad
     */
    public Ingrediente(int idIngrediente, String nombreIngrdiente, String cantidad) {
        this.idIngrediente = idIngrediente;
        this.nombreIngrdiente = nombreIngrdiente;
        this.cantidad = cantidad;
    }

    /**
     * Instantiates a new Ingrediente.
     *
     * @param idIngrediente    the id ingrediente
     * @param nombreIngrdiente the nombre ingrdiente
     */
    public Ingrediente(int idIngrediente, String nombreIngrdiente) {
        this.idIngrediente = idIngrediente;
        this.nombreIngrdiente = nombreIngrdiente;
    }

    /**
     * Gets id ingrediente.
     *
     * @return the id ingrediente
     */
    public int getIdIngrediente() {
        return idIngrediente;
    }

    /**
     * Sets id ingrediente.
     *
     * @param idIngrediente the id ingrediente
     */
    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    /**
     * Gets cantidad.
     *
     * @return the cantidad
     */
    public String getCantidad() {
        return cantidad;
    }

    /**
     * Sets cantidad.
     *
     * @param cantidad the cantidad
     */
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Gets nombre ingrdiente.
     *
     * @return the nombre ingrdiente
     */
    public String getNombreIngrdiente() {
        return nombreIngrdiente;
    }

    /**
     * Sets nombre ingrdiente.
     *
     * @param nombreIngrdiente the nombre ingrdiente
     */
    public void setNombreIngrdiente(String nombreIngrdiente) {
        this.nombreIngrdiente = nombreIngrdiente;
    }
}
