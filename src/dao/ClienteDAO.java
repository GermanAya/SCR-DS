package dao;

import modelo.ClienteVO;
import java.util.List;

public interface ClienteDAO {
    String insertarCliente(ClienteVO c);
    List<ClienteVO> listarClientes();
    String actualizarCliente(ClienteVO c);
    String eliminarCliente(String cedula);
}
