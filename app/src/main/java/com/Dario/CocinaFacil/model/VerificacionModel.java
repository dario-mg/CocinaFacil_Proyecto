package com.Dario.CocinaFacil.model;

import android.util.Log;

import com.Dario.CocinaFacil.models.Usuario;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Verificacion model.
 */
public class VerificacionModel {
    /**
     * The type Inicio sesion.
     */
// Clase interna para el inicio de sesión del usuario
    public static class InicioSesion {
        private Usuario usuario;

        /**
         * Instantiates a new Inicio sesion.
         *
         * @param usuario the usuario
         */
        public InicioSesion(Usuario usuario) {
            this.usuario = usuario;
        }
    }

    /**
     * The interface Callback usuario comprobado.
     */
// Interfaz para devolver el resultado de la verificación de usuario
    public interface CallbackUsuarioComprobado {
        /**
         * Usuario comprobado.
         *
         * @param comprobado the comprobado
         */
        void usuarioComprobado(Boolean comprobado);
    }

    /**
     * The interface Callback usuario registrado.
     */
// Interfaz para devolver el resultado del registro de usuario
    public interface CallbackUsuarioRegistrado {
        /**
         * Usuario registrado.
         *
         * @param verificado the verificado
         */
        void usuarioRegistrado(Boolean verificado);
    }

    /**
     * Verificar usuario.
     *
     * @param verificandoUsuario the verificando usuario
     * @param callback           the callback
     */
// Método para verificar si el usuario existe en la base de datos
    public void verificarUsuario(InicioSesion verificandoUsuario, CallbackUsuarioComprobado callback) {
        boolean verificado = esVerificado(verificandoUsuario);
        callback.usuarioComprobado(verificado);  // Devolvemos el resultado al callback
    }

    /**
     * Introducir usuario.
     *
     * @param registroUsuario the registro usuario
     * @param callback        the callback
     */
// Método para registrar un nuevo usuario si no existe en la base de datos
    public void introducirUsuario(InicioSesion registroUsuario, CallbackUsuarioRegistrado callback) {
        boolean verificado = esVerificado(registroUsuario);

        if (!verificado) {
            registrarNuevoUsuario(registroUsuario);
            verificado = true;
        }

        callback.usuarioRegistrado(verificado);  // Devolvemos si el usuario fue registrado o ya existía
    }

    // Método para obtener la conexión a la base de datos
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mariadb://db.taxistahosting.com:3306/dariodb";  // URL de MariaDB
        String user = "root";
        String password = "dariopintoresco!24!";

//        String url = "jdbc:mariadb://10.0.2.2:3306/dariodb";  // Para emulador
//        String user = "root";
//        String password = "1234";


        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                Log.d("debugdb", "Conexión exitosa a la base de datos MariaDB.");
            }
            return conn;
        } catch (SQLException e) {
            Log.d("debugdb", "Error de conexión: " + e.getMessage());
            throw e;
        }
    }

    // Método privado para registrar un nuevo usuario en la base de datos
    private void registrarNuevoUsuario(InicioSesion registroUsuario) {
        String sql = "INSERT INTO usuarios (nombre_usuario, contrasena, correo) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(registroUsuario.usuario.getContrasena(), BCrypt.gensalt());
            stmt.setString(1, registroUsuario.usuario.getNombreUsuario());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, registroUsuario.usuario.getCorreo());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                Log.d("debugdb", "Usuario insertado correctamente.");
            }

        } catch (SQLException e) {
            Log.d("debugdb", "Error al insertar el usuario: " + e.getMessage());
        }
    }

    // Método privado para verificar si el usuario ya existe en la base de datos
    private static boolean esVerificado(InicioSesion verificandoUsuario) {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT nombre_usuario, contrasena FROM usuarios";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String nombreUsuario = rs.getString("nombre_usuario");
                String contrasena = rs.getString("contrasena");
                usuarios.add(new Usuario(nombreUsuario, contrasena));
            }

        } catch (SQLException e) {
            Log.e("VerificacionModel", "Error al consultar la base de datos", e);
        }

        // Verificación de que el usuario y la contraseña coinciden
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(verificandoUsuario.usuario.getNombreUsuario()) &&
                    BCrypt.checkpw(verificandoUsuario.usuario.getContrasena(), u.getContrasena())) {
                return true;  // Usuario verificado
            }
        }

        return false;  // Usuario no encontrado o contraseña incorrecta
    }

}
