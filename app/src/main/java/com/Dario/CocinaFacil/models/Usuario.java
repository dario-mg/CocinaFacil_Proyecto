package com.Dario.CocinaFacil.models;

/**
 * The type Usuario.
 */
public class Usuario {
    private int id;
    private String nombreUsuario;
    private String contrasena;
    private String correo;

    /**
     * Instantiates a new Usuario.
     *
     * @param nombreUsuario the nombre usuario
     * @param contrasena    the contrasena
     */
    public Usuario(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    /**
     * Instantiates a new Usuario.
     *
     * @param nombreUsuario the nombre usuario
     * @param contrasena    the contrasena
     * @param correo        the correo
     */
    public Usuario(String nombreUsuario, String contrasena, String correo) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correo = correo;
    }

    /**
     * Instantiates a new Usuario.
     *
     * @param id            the id
     * @param nombreUsuario the nombre usuario
     * @param contrasena    the contrasena
     * @param correo        the correo
     */
    public Usuario(int id, String nombreUsuario, String contrasena, String correo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correo = correo;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets nombre usuario.
     *
     * @return the nombre usuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Sets nombre usuario.
     *
     * @param nombreUsuario the nombre usuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Gets contrasena.
     *
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Sets contrasena.
     *
     * @param contrasena the contrasena
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Gets correo.
     *
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Sets correo.
     *
     * @param correo the correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
