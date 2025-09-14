package dao;

import modelo.ClienteVO;
import modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public String insertarCliente(ClienteVO c) {
        String sql = "INSERT INTO cliente (cedula, nombre, celular) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCedula());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getCelular());
            ps.executeUpdate();
            return "Cliente registrado";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public List<ClienteVO> listarClientes() {
        List<ClienteVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection con = Conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new ClienteVO(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("celular")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public String actualizarCliente(ClienteVO c) {
        String sql = "UPDATE cliente SET nombre=?, celular=? WHERE cedula=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getCelular());
            ps.setString(3, c.getCedula());
            int filas = ps.executeUpdate();
            return filas > 0 ? "Cliente actualizado" : "No se encontró el cliente";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String eliminarCliente(String cedula) {
        String sql = "DELETE FROM cliente WHERE cedula=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            int filas = ps.executeUpdate();
            return filas > 0 ? "Cliente eliminado" : "No se encontró el cliente";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}
