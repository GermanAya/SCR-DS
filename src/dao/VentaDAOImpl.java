package dao;

import modelo.VentaVO;
import modelo.Conexion;
import modelo.ClienteVO;
import modelo.ComidaRapidaVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOImpl implements VentaDAO {

    @Override
    public String insertarVenta(VentaVO v) {
        String sql = "INSERT INTO venta(fechaHora, cedulaCliente, idComida) VALUES(?,?,?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getFechaHora());
            ps.setString(2, v.getCliente().getCedula());
            ps.setInt(3, v.getComidaRapida().getIdComida());
            ps.executeUpdate();
            return "Venta registrada";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public List<VentaVO> listarVentas() {
        List<VentaVO> lista = new ArrayList<>();
        String sql = "SELECT v.idVenta, v.fechaHora, c.cedula, c.nombre, c.celular, "
                   + "cr.idComida, cr.nombreComida, cr.precio, cr.ingredientesMenu "
                   + "FROM venta v "
                   + "LEFT JOIN cliente c ON v.cedulaCliente = c.cedula "
                   + "LEFT JOIN comida_rapida cr ON v.idComida = cr.idComida "
                   + "ORDER BY v.idVenta";
        try (Connection con = Conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
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
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public String actualizarVenta(VentaVO v) {
        String sql = "UPDATE venta SET fechaHora=?, cedulaCliente=?, idComida=? WHERE idVenta=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getFechaHora());
            ps.setString(2, v.getCliente().getCedula());
            ps.setInt(3, v.getComidaRapida().getIdComida());
            ps.setInt(4, v.getIdVenta());
            int filas = ps.executeUpdate();
            return filas > 0 ? "Venta actualizada" : "No se encontró la venta";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String eliminarVenta(int idVenta) {
        String sql = "DELETE FROM venta WHERE idVenta=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            int filas = ps.executeUpdate();
            return filas > 0 ? "Venta eliminada" : "No se encontró la venta";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}
