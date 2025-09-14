package modelo;

/*
 * Nombre: VentaVO
 * Autor: Germán Aya
 * Fecha: 14/09/2025
 */
public class VentaVO {
    private int idVenta;
    private String fechaHora;
    private ClienteVO cliente;
    private ComidaRapidaVO comidaRapida;

    public VentaVO() {}

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public ClienteVO getCliente() { return cliente; }
    public void setCliente(ClienteVO cliente) { this.cliente = cliente; }

    public ComidaRapidaVO getComidaRapida() { return comidaRapida; }
    public void setComidaRapida(ComidaRapidaVO comidaRapida) { this.comidaRapida = comidaRapida; }
}
