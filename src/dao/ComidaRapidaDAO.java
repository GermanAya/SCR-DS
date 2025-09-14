package dao;

import modelo.ComidaRapidaVO;
import java.util.List;

public interface ComidaRapidaDAO {
    String insertarComida(ComidaRapidaVO c);
    List<ComidaRapidaVO> listarComidas();
    String actualizarComida(ComidaRapidaVO c);
    String eliminarComida(int idComida);
}
