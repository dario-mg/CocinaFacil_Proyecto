package com.Dario.CocinaFacil.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.Dario.CocinaFacil.models.Ingrediente;
import com.Dario.CocinaFacil.models.Receta;
import com.Dario.CocinaFacil.models.Usuario;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Aplicacion model.
 */
public class AplicacionModel {

    /**
     * The interface Callback recetas usuario.
     */
    public interface CallbackRecetasUsuario {
        /**
         * Recetas usuario.
         *
         * @param recetas the recetas
         */
        void recetasUsuario(List<Receta> recetas);
    }

    /**
     * The interface Callback introducir receta.
     */
    public interface CallbackIntroducirReceta {
        /**
         * Receta introducida.
         *
         * @param introducido the introducido
         */
        void recetaIntroducida(Boolean introducido);
    }

    /**
     * The interface Callback datos de la receta.
     */
    public interface CallbackDatosDeLaReceta {
        /**
         * Datos de la receta.
         *
         * @param receta the receta
         */
        void datosDeLaReceta(Receta receta);

        /**
         * Datos de los ingrdientes.
         *
         * @param ingredietes the ingredietes
         */
        void datosDeLosIngrdientes(List<Ingrediente> ingredietes);
    }

    /**
     * The interface Callback sesion usuario.
     */
    public interface CallbackSesionUsuario {
        /**
         * Sesion usuario.
         *
         * @param usuario the usuario
         */
        void sesionUsuario(Usuario usuario);
    }

    /**
     * The interface Callback todas las recetas.
     */
    public interface CallbackTodasLasRecetas {
        /**
         * Todas las recetas.
         *
         * @param recetas the recetas
         */
        void todasLasRecetas(List<Receta> recetas);
    }

    /**
     * The interface Callback todos los ingrdientes.
     */
    public interface CallbackTodosLosIngrdientes {
        /**
         * Todos los ingredientes.
         *
         * @param listaIngrdientes the lista ingrdientes
         */
        void todosLosIngredientes(List<Ingrediente> listaIngrdientes);
    }

    /**
     * The interface Callback lista ingredietes anadir.
     */
    public interface CallbackListaIngredietesAnadir {
        /**
         * Lista ingredientes anadir.
         *
         * @param listaIngedientesAnadir the lista ingedientes anadir
         */
        void listaIngredientesAnadir(List<Ingrediente> listaIngedientesAnadir);
    }

    /**
     * Usuario sesion.
     *
     * @param nombre   the nombre
     * @param callback the callback
     */
    public void usuarioSesion(String nombre, CallbackSesionUsuario callback) {
        Usuario usuario = getUsuario(nombre);
        callback.sesionUsuario(usuario);
    }


    /**
     * Recetas usuario.
     *
     * @param usuario  the usuario
     * @param callback the callback
     */
    public void recetasUsuario(Usuario usuario, CallbackRecetasUsuario callback) {
        List<Receta> recetas = new ArrayList<>();
        String query = "SELECT id_receta, nombre_receta, descripcion, instrucciones, id_usuario, imagen_receta " +
                "FROM recetas WHERE id_usuario = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Receta receta = new Receta(
                        rs.getInt("id_receta"),
                        rs.getString("nombre_receta"),
                        rs.getString("descripcion"),
                        rs.getString("instrucciones"),
                        rs.getInt("id_usuario"),
                        bytesAImagenes(rs.getBytes("imagen_receta"))
                );
                recetas.add(receta);
            }
        } catch (SQLException e) {
            Log.e("debugdb", "Error al obtener recetas: " + e.getMessage());
        }

        callback.recetasUsuario(recetas);
    }

    /**
     * Todas las recetas.
     *
     * @param callback the callback
     */
    public void todasLasRecetas(CallbackTodasLasRecetas callback) {
        List<Receta> recetas = new ArrayList<>();
        String query = "SELECT id_receta, nombre_receta, descripcion, instrucciones, id_usuario, imagen_receta " +
                "FROM recetas";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Receta receta = new Receta(
                        rs.getInt("id_receta"),
                        rs.getString("nombre_receta"),
                        rs.getString("descripcion"),
                        rs.getString("instrucciones"),
                        rs.getInt("id_usuario"),
                        bytesAImagenes(rs.getBytes("imagen_receta"))
                );
                recetas.add(receta);
            }
        } catch (SQLException e) {
            Log.e("debugdb", "Error al obtener recetas: " + e.getMessage());
        }

        callback.todasLasRecetas(recetas);
    }

    /**
     * Todos los ingredientes.
     *
     * @param callback the callback
     */
    public void todosLosIngredientes(CallbackTodosLosIngrdientes callback) {

        List<Ingrediente> ingredientes = new ArrayList<>();

        // Consulta SQL
        String sql = "SELECT id_ingrediente, nombre_ingrediente FROM ingredientes";

        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            // Recorrer los resultados
            while (resultSet.next()) {
                int id = resultSet.getInt("id_ingrediente");
                String nombre = resultSet.getString("nombre_ingrediente");

                // Crear el objeto Ingrediente y agregarlo a la lista
                Ingrediente ingrediente = new Ingrediente(id, nombre);
                ingredientes.add(ingrediente);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        callback.todosLosIngredientes(ingredientes);
    }

    private Usuario getUsuario(String nombre) {
        Usuario usuario = null;

        String query = "SELECT id_usuario, nombre_usuario, contrasena, correo " +
                "FROM usuarios WHERE nombre_usuario = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rs.getString("correo")
                );
            }
        } catch (SQLException e) {
            Log.e("debugdb", "Error al obtener el usuario: " + e.getMessage());
        }

        return usuario;
    }

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
            Log.d("debugdb", "Error al conectar a la base de datos: " + e.toString());
            throw e;
        }
    }

    /**
     * Datos de las recetas.
     *
     * @param receta   the receta
     * @param callback the callback
     */
    public void datosDeLasRecetas(Receta receta, CallbackDatosDeLaReceta callback) {
        List<Ingrediente> listaIngredientes = new ArrayList<>();

        // Consulta SQL para obtener los ingredientes de una receta con la cantidad correcta
        String sql = "SELECT i.id_ingrediente, i.nombre_ingrediente, ri.cantidad " +
                "FROM ingredientes i " +
                "JOIN recetas_ingredientes ri ON i.id_ingrediente = ri.id_ingrediente " +
                "WHERE ri.id_receta = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, receta.getIdReceta());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listaIngredientes.add(
                            new Ingrediente(
                                    rs.getInt("id_ingrediente"),
                                    rs.getString("nombre_ingrediente"),
                                    rs.getString("cantidad")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        callback.datosDeLaReceta(receta);
        callback.datosDeLosIngrdientes(listaIngredientes);
    }

    /**
     * Lista ingredientes anadir.
     *
     * @param ingrdientes the ingrdientes
     * @param callback    the callback
     */
    public void listaIngredientesAnadir(List<Ingrediente> ingrdientes, CallbackListaIngredietesAnadir callback) {
        callback.listaIngredientesAnadir(ingrdientes);
    }

    //Convertir de imagen a bytes
    private static Bitmap bytesAImagenes(byte[] imageBytes) {
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    private byte[] imagenesABytes(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e("Consulta", "El Bitmap es null, no se puede convertir a bytes");
            return new byte[0];  // Retorna un array vacío en caso de que el bitmap sea null
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Comprime el Bitmap en formato PNG
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    //Convertir de bytes a iamgenes

    /**
     * Introducir receta.
     *
     * @param receta       the receta
     * @param ingredientes the ingredientes
     * @param callback     the callback
     */
    public void introducirReceta(Receta receta, List<Ingrediente> ingredientes, CallbackIntroducirReceta callback) {

        String sqlReceta = "INSERT INTO recetas (nombre_receta, descripcion, instrucciones, id_usuario, imagen_receta) "
                + "VALUES (?, ?, ?, ?, ?)";
        int filasAfectadas = 0;
        Boolean insertado = false;
        int idReceta = -1; // Inicializamos la variable idReceta

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlReceta, PreparedStatement.RETURN_GENERATED_KEYS);
            // Establecer los parámetros para la receta
            preparedStatement.setString(1, receta.getNombreReceta());
            preparedStatement.setString(2, receta.getDescripcion());
            preparedStatement.setString(3, receta.getInstrucciones()); // Asegúrate de usar el campo correcto
            preparedStatement.setInt(4, receta.getIdUsuario());
            preparedStatement.setBytes(5, imagenesABytes(receta.getImagenReceta()));

            // Ejecutar la consulta de inserción de receta
            filasAfectadas = preparedStatement.executeUpdate();

            // Si se insertó correctamente, obtenemos el ID generado
            if (filasAfectadas > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idReceta = generatedKeys.getInt(1); // El primer valor es el ID de la receta recién insertada
                }
            }
        } catch (Exception e) {
            Log.d("Consulta", "" + e);
        }

        if (filasAfectadas > 0) {
            insertado = true;
        }

        // Ahora que tenemos el idReceta, insertamos los ingredientes en la tabla recetas_ingredientes
        if (insertado && idReceta != -1) {
            String sqlIngredientes = "INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) "
                    + "VALUES (?, ?, ?)";

            try {
                // Iniciar la transacción
                getConnection().setAutoCommit(false);  // Comenzamos una transacción

                // Preparar el statement para insertar los ingredientes
                PreparedStatement psIngredientes = getConnection().prepareStatement(sqlIngredientes);

                for (Ingrediente ingrediente : ingredientes) {
                    // Establecer los parámetros para cada ingrediente
                    psIngredientes.setInt(1, idReceta);  // id_receta (la receta que acabamos de insertar)
                    psIngredientes.setInt(2, ingrediente.getIdIngrediente());  // id_ingrediente
                    psIngredientes.setString(3, ingrediente.getCantidad());  // cantidad
                    psIngredientes.addBatch();  // Añadimos la consulta al batch
                }

                // Ejecutar el batch para insertar todos los ingredientes
                int[] filasInsertadas = psIngredientes.executeBatch();

                // Confirmamos la transacción
                getConnection().commit();
                getConnection().setAutoCommit(true);  // Restauramos el modo de commit automático

                // Si se insertaron todas las filas correctamente, devolvemos true
                Log.d("Consulta", "Ingredientes insertados correctamente: " + filasInsertadas.length);
            } catch (Exception e) {
                Log.d("Consulta", "Error al insertar ingredientes: " + e);
                try {
                    // Si ocurre un error, revertimos la transacción
                    getConnection().rollback();
                } catch (SQLException rollbackEx) {
                    Log.d("Consulta", "Error al hacer rollback: " + rollbackEx);
                }
            }
        }

        // Pasamos el idReceta al callback
        callback.recetaIntroducida(insertado); // Pasamos el idReceta al callback

    }






}
