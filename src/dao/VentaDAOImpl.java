package dao;

import modelo.VentaVO;
import modelo.Conexion;
import modelo.ClienteVO;
import modelo.ComidaRapidaVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementa la interfaz VentaDAO, gestionando operaciones CRUD para la tabla venta
public class VentaDAOImpl implements VentaDAO {

    // ===== INSERTAR VENTA =====
    @Override
    public String insertarVenta(VentaVO v) {
        // Sentencia SQL parametrizada para registrar una venta
        String sql = "INSERT INTO venta(fechaHora, cedulaCliente, idComida) VALUES(?,?,?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Asigna los parámetros de la venta
            ps.setString(1, v.getFechaHora());
            ps.setString(2, v.getCliente().getCedula());
            ps.setInt(3, v.getComidaRapida().getIdComida());
            // Ejecuta la inserción
            ps.executeUpdate();
            return "Venta registrada";
        } catch (SQLException e) {
            // Devuelve mensaje de error si falla la inserción
            return "Error: " + e.getMessage();
        }
    }

    // ===== LISTAR VENTAS =====
    @Override
    public List<VentaVO> listarVentas() {
        List<VentaVO> lista = new ArrayList<>();
        // Consulta SQL que une las tablas venta, cliente y comida_rapida
        String sql = "SELECT v.idVenta, v.fechaHora, c.cedula, c.nombre, c.celular, "
                   + "cr.idComida, cr.nombreComida, cr.precio, cr.ingredientesMenu "
                   + "FROM venta v "
                   + "LEFT JOIN cliente c ON v.cedulaCliente = c.cedula "
                   + "LEFT JOIN comida_rapida cr ON v.idComida = cr.idComida "
                   + "ORDER BY v.idVenta";
        try (Connection con = Conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            // Recorre el resultado y crea objetos ClienteVO, ComidaRapidaVO y VentaVO
            while (rs.next()) {
                ClienteVO c = new ClienteVO(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("celular"));
                ComidaRapidaVO cr = new ComidaRapidaVO(
                        rs.getInt("idComida"),
                        rs.getString("nombreComida"),
                        rs.getDouble("precio"),
                        rs.getString("ingredientesMenu"));
                VentaVO v = new VentaVO();
                v.setIdVenta(rs.getInt("idVenta"));
                v.setFechaHora(rs.getString("fechaHora"));
                v.setCliente(c);
                v.setComidaRapida(cr);
                // Agrega la venta a la lista
                lista.add(v);
            }
        } catch (SQLException e) {
            // Imprime la traza de error en consola
            e.printStackTrace();
        }
        return lista; // Retorna la lista de ventas (puede estar vacía)
    }

    // ===== ACTUALIZAR VENTA =====
    @Override
    public String actualizarVenta(VentaVO v) {
        String sql = "UPDATE venta SET fechaHora=?, cedulaCliente=?, idComida=? WHERE idVenta=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Asigna los nuevos valores de la venta
            ps.setString(1, v.getFechaHora());
            ps.setString(2, v.getCliente().getCedula());
            ps.setInt(3, v.getComidaRapida().getIdComida());
            ps.setInt(4, v.getIdVenta());
            // Ejecuta la actualización y verifica si afectó alguna fila
            int filas = ps.executeUpdate();
            return filas > 0 ? "Venta actualizada" : "No se encontró la venta";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    // ===== ELIMINAR VENTA =====
    @Override
    public String eliminarVenta(int idVenta) {
        String sql = "DELETE FROM venta WHERE idVenta=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            // Ejecuta eliminación y verifica si afectó alguna fila
            int filas = ps.executeUpdate();
            return filas > 0 ? "Venta eliminada" : "No se encontró la venta";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}
