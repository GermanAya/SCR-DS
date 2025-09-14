package modelo;

/*
 * Nombre: ComidaRapidaVO
 * Autor: Germán Aya
 * Fecha: 14/09/2025
 */
public class ComidaRapidaVO {
    private int idComida;
    private String nombreComida;
    private double precio;
    private String ingredientesMenu; // coma-separados

    public ComidaRapidaVO() {}

    public ComidaRapidaVO(int idComida, String nombreComida, double precio, String ingredientesMenu) {
        this.idComida = idComida;
        this.nombreComida = nombreComida;
        this.precio = precio;
        this.ingredientesMenu = ingredientesMenu;
    }

    public int getIdComida() { return idComida; }
    public void setIdComida(int idComida) { this.idComida = idComida; }

    public String getNombreComida() { return nombreComida; }
    public void setNombreComida(String nombreComida) { this.nombreComida = nombreComida; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getIngredientesMenu() { return ingredientesMenu; }
    public void setIngredientesMenu(String ingredientesMenu) { this.ingredientesMenu = ingredientesMenu; }
}
