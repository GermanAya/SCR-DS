package dao;

import modelo.ClienteVO;
import modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementa la interfaz ClienteDAO, gestionando operaciones CRUD para la tabla cliente
public class ClienteDAOImpl implements ClienteDAO {

    // ===== INSERTAR CLIENTE =====
    @Override
    public String insertarCliente(ClienteVO c) {
        // Sentencia SQL parametrizada para evitar inyecciones SQL
        String sql = "INSERT INTO cliente (cedula, nombre, celular) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Asignación de parámetros en orden
            ps.setString(1, c.getCedula());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getCelular());
            // Ejecuta la sentencia
            ps.executeUpdate();
            return "Cliente registrado";
        } catch (SQLException e) {
            // Retorna el mensaje de error en caso de excepción
            return "Error: " + e.getMessage();
        }
    }

    // ===== LISTAR CLIENTES =====
    @Override
    public List<ClienteVO> listarClientes() {
        List<ClienteVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection con = Conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            // Recorre el resultado y crea objetos ClienteVO para agregarlos a la lista
            while (rs.next()) {
                lista.add(new ClienteVO(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("celular")));
            }
        } catch (SQLException e) {
            // Muestra el error en consola (no se propaga)
            e.printStackTrace();
        }
        return lista; // Devuelve la lista de clientes (puede estar vacía)
    }

    // ===== ACTUALIZAR CLIENTE =====
    @Override
    public String actualizarCliente(ClienteVO c) {
        String sql = "UPDATE cliente SET nombre=?, celular=? WHERE cedula=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Asigna los nuevos valores
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getCelular());
            ps.setString(3, c.getCedula());
            // Ejecuta actualización y verifica si se afectó alguna fila
            int filas = ps.executeUpdate();
            return filas > 0 ? "Cliente actualizado" : "No se encontró el cliente";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    // ===== ELIMINAR CLIENTE =====
    @Override
    public String eliminarCliente(String cedula) {
        String sql = "DELETE FROM cliente WHERE cedula=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Asigna el parámetro de cédula
            ps.setString(1, cedula);
            // Ejecuta eliminación y verifica si se afectó alguna fila
            int filas = ps.executeUpdate();
            return filas > 0 ? "Cliente eliminado" : "No se encontró el cliente";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}
