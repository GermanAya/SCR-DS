package modelo;

/*
 * Nombre: ClienteVO
 * Autor: Germán Aya
 * Fecha: 14/09/2025
 */
public class ClienteVO {
    private String cedula;
    private String nombre;
    private String celular;

    public ClienteVO() {}

    public ClienteVO(String cedula, String nombre, String celular) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.celular = celular;
    }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
}
