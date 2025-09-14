package dao;

import modelo.VentaVO;
import java.util.List;

public interface VentaDAO {
    String insertarVenta(VentaVO v);
    List<VentaVO> listarVentas();
    String actualizarVenta(VentaVO v);
    String eliminarVenta(int idVenta);
}
