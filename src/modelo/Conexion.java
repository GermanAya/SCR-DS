package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Conexion {
    private static final String URL = "jdbc:sqlite:db/tablas.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Crea tablas si no existen
    public static void inicializar() {
        String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente ("
                + "cedula TEXT PRIMARY KEY, "
                + "nombre TEXT NOT NULL, "
                + "celular TEXT);";

        String sqlComida = "CREATE TABLE IF NOT EXISTS comida_rapida ("
                + "idComida INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombreComida TEXT NOT NULL, "
                + "precio REAL, "
                + "ingredientesMenu TEXT);";

        String sqlVenta = "CREATE TABLE IF NOT EXISTS venta ("
                + "idVenta INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "fechaHora TEXT, "
                + "cedulaCliente TEXT, "
                + "idComida INTEGER, "
                + "FOREIGN KEY (cedulaCliente) REFERENCES cliente(cedula), "
                + "FOREIGN KEY (idComida) REFERENCES comida_rapida(idComida));";

        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.execute(sqlCliente);
            st.execute(sqlComida);
            st.execute(sqlVenta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
