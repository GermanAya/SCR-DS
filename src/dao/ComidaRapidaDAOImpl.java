package dao;

import modelo.ComidaRapidaVO;
import modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementa la interfaz ComidaRapidaDAO, gestionando operaciones CRUD para la tabla comida_rapida
public class ComidaRapidaDAOImpl implements ComidaRapidaDAO {

    // ===== INSERTAR COMIDA =====
    @Override
    public String insertarComida(ComidaRapidaVO c) {
        // Sentencia SQL parametrizada para insertar una nueva comida
        String sql = "INSERT INTO comida_rapida (nombreComida, precio, ingredientesMenu) VALUES (?,?,?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Asigna los parámetros de la comida
            ps.setString(1, c.getNombreComida());
            ps.setDouble(2, c.getPrecio());
            ps.setString(3, c.getIngredientesMenu());
            // Ejecuta la inserción
            ps.executeUpdate();
            return "Comida registrada";
        } catch (SQLException e) {
            // Devuelve mensaje de error si falla la inserción
            return "Error: " + e.getMessage();
        }
    }

    // ===== LISTAR COMIDAS =====
    @Override
    public List<ComidaRapidaVO> listarComidas() {
        List<ComidaRapidaVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM comida_rapida";
        try (Connection con = Conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            // Recorre el resultado y agrega objetos ComidaRapidaVO a la lista
            while (rs.next()) {
                lista.add(new ComidaRapidaVO(
                        rs.getInt("idComida"),
                        rs.getString("nombreComida"),
                        rs.getDouble("precio"),
                        rs.getString("ingredientesMenu")));
            }
        } catch (SQLException e) {
            // Imprime la traza de error en consola
            e.printStackTrace();
        }
        return lista; // Retorna la lista de comidas (puede estar vacía)
    }

    // ===== ACTUALIZAR COMIDA =====
    @Override
    public String actualizarComida(ComidaRapidaVO c) {
        String sql = "UPDATE comida_rapida SET nombreComida=?, precio=?, ingredientesMenu=? WHERE idComida=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Asigna los nuevos valores
            ps.setString(1, c.getNombreComida());
            ps.setDouble(2, c.getPrecio());
            ps.setString(3, c.getIngredientesMenu());
            ps.setInt(4, c.getIdComida());
            // Ejecuta actualización y verifica si afectó alguna fila
            int filas = ps.executeUpdate();
            return filas > 0 ? "Comida actualizada" : "No se encontró la comida";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    // ===== ELIMINAR COMIDA =====
    @Override
    public String eliminarComida(int idComida) {
        String sql = "DELETE FROM comida_rapida WHERE idComida=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idComida);
            // Ejecuta eliminación y verifica si afectó alguna fila
            int filas = ps.executeUpdate();
            return filas > 0 ? "Comida eliminada" : "No se encontró la comida";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}
