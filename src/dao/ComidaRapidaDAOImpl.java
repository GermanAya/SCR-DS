package dao;

import modelo.ComidaRapidaVO;
import modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComidaRapidaDAOImpl implements ComidaRapidaDAO {

    @Override
    public String insertarComida(ComidaRapidaVO c) {
        String sql = "INSERT INTO comida_rapida (nombreComida, precio, ingredientesMenu) VALUES (?,?,?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombreComida());
            ps.setDouble(2, c.getPrecio());
            ps.setString(3, c.getIngredientesMenu());
            ps.executeUpdate();
            return "Comida registrada";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public List<ComidaRapidaVO> listarComidas() {
        List<ComidaRapidaVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM comida_rapida";
        try (Connection con = Conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new ComidaRapidaVO(
                        rs.getInt("idComida"),
                        rs.getString("nombreComida"),
                        rs.getDouble("precio"),
                        rs.getString("ingredientesMenu")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public String actualizarComida(ComidaRapidaVO c) {
        String sql = "UPDATE comida_rapida SET nombreComida=?, precio=?, ingredientesMenu=? WHERE idComida=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombreComida());
            ps.setDouble(2, c.getPrecio());
            ps.setString(3, c.getIngredientesMenu());
            ps.setInt(4, c.getIdComida());
            int filas = ps.executeUpdate();
            return filas > 0 ? "Comida actualizada" : "No se encontró la comida";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String eliminarComida(int idComida) {
        String sql = "DELETE FROM comida_rapida WHERE idComida=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idComida);
            int filas = ps.executeUpdate();
            return filas > 0 ? "Comida eliminada" : "No se encontró la comida";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}
