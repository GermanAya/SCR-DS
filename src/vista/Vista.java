package vista;

import java.util.List;
import java.util.Scanner;
import modelo.ClienteVO;
import modelo.ComidaRapidaVO;
import modelo.VentaVO;

public class Vista {
    private Scanner sc = new Scanner(System.in); // Scanner para entrada de datos

    // ===== MENÚ PRINCIPAL =====
    public int menu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Agregar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Editar cliente");
        System.out.println("4. Eliminar cliente");
        System.out.println("5. Agregar comida");
        System.out.println("6. Listar comidas");
        System.out.println("7. Editar comida");
        System.out.println("8. Eliminar comida");
        System.out.println("9. Registrar venta");
        System.out.println("10. Listar ventas");
        System.out.println("11. Editar venta");
        System.out.println("12. Eliminar venta");
        System.out.println("0. Salir");
        System.out.print("Ingrese opción: ");
        try {
            return Integer.parseInt(sc.nextLine()); // Lee opción del usuario
        } catch (Exception e) {
            return -1; // Retorna -1 si la entrada no es válida
        }
    }

    // ===== CLIENTE =====
    public ClienteVO leerCliente() {
        // Lee datos de un nuevo cliente
        ClienteVO c = new ClienteVO();
        System.out.print("Cédula: ");
        c.setCedula(sc.nextLine());
        System.out.print("Nombre: ");
        c.setNombre(sc.nextLine());
        System.out.print("Celular: ");
        c.setCelular(sc.nextLine());
        return c;
    }

    public String pedirCedulaParaEliminar() {
        System.out.print("Cédula del cliente a eliminar: ");
        return sc.nextLine();
    }

    public String pedirCedulaParaEditarCliente() {
        System.out.print("Cédula del cliente a editar: ");
        return sc.nextLine();
    }

    public ClienteVO leerClienteEditado() {
        // Lee los nuevos datos de un cliente para actualizar
        ClienteVO c = new ClienteVO();
        System.out.print("Nuevo nombre: ");
        c.setNombre(sc.nextLine());
        System.out.print("Nuevo celular: ");
        c.setCelular(sc.nextLine());
        return c;
    }

    // ===== COMIDA =====
    public ComidaRapidaVO leerComida() {
        // Lee datos de una nueva comida
        ComidaRapidaVO cr = new ComidaRapidaVO();
        System.out.print("Nombre comida: ");
        cr.setNombreComida(sc.nextLine());
        System.out.print("Precio: ");
        try {
            cr.setPrecio(Double.parseDouble(sc.nextLine()));
        } catch (Exception e) {
            cr.setPrecio(0.0); // Valor por defecto si la entrada no es válida
        }
        System.out.print("Ingredientes (separados por coma): ");
        cr.setIngredientesMenu(sc.nextLine());
        return cr;
    }

    public int pedirIdComidaParaEliminar() {
        System.out.print("ID de la comida a eliminar: ");
        try { return Integer.parseInt(sc.nextLine()); } catch (Exception e) { return -1; }
    }

    public int pedirIdComidaParaEditar() {
        System.out.print("ID de la comida a editar: ");
        try { return Integer.parseInt(sc.nextLine()); } catch (Exception e) { return -1; }
    }

    public ComidaRapidaVO leerComidaEditada() {
        // Lee los nuevos datos de una comida para actualizar
        ComidaRapidaVO cr = new ComidaRapidaVO();
        System.out.print("Nuevo nombre comida: ");
        cr.setNombreComida(sc.nextLine());
        System.out.print("Nuevo precio: ");
        try { cr.setPrecio(Double.parseDouble(sc.nextLine())); } catch (Exception e) { cr.setPrecio(0.0); }
        System.out.print("Nuevo ingredientes (separados por coma): ");
        cr.setIngredientesMenu(sc.nextLine());
        return cr;
    }

    // ===== VENTA =====
    public String pedirCedulaParaVenta() {
        System.out.print("Cédula del cliente: ");
        return sc.nextLine();
    }

    public int pedirIdComidaParaVenta() {
        System.out.print("ID de la comida: ");
        try { return Integer.parseInt(sc.nextLine()); } catch (Exception e) { return -1; }
    }

    public int pedirIdVentaParaEliminar() {
        System.out.print("ID de la venta a eliminar: ");
        try { return Integer.parseInt(sc.nextLine()); } catch (Exception e) { return -1; }
    }

    public int pedirIdVentaParaEditar() {
        System.out.print("ID de la venta a editar: ");
        try { return Integer.parseInt(sc.nextLine()); } catch (Exception e) { return -1; }
    }

    public VentaVO leerVentaEditada() {
        // Lee datos nuevos para actualizar una venta
        VentaVO v = new VentaVO();
        System.out.print("Nueva fecha y hora (yyyy-MM-dd HH:mm:ss): ");
        v.setFechaHora(sc.nextLine());
        System.out.print("Nueva cédula del cliente: ");
        ClienteVO c = new ClienteVO();
        c.setCedula(sc.nextLine());
        v.setCliente(c);
        System.out.print("Nuevo ID de comida: ");
        ComidaRapidaVO cr = new ComidaRapidaVO();
        try { cr.setIdComida(Integer.parseInt(sc.nextLine())); } catch (Exception e) { cr.setIdComida(-1); }
        v.setComidaRapida(cr);
        return v;
    }

    // ===== MENSAJES / MOSTRAR =====
    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    public void mostrarClientes(List<ClienteVO> lista) {
        System.out.println("--- CLIENTES ---");
        if (lista.isEmpty()) System.out.println("No hay clientes.");
        for (ClienteVO c : lista) {
            System.out.println(c.getCedula() + " | " + c.getNombre() + " | " + c.getCelular());
        }
    }

    public void mostrarComidas(List<ComidaRapidaVO> lista) {
        System.out.println("--- COMIDAS ---");
        if (lista.isEmpty()) System.out.println("No hay comidas.");
        for (ComidaRapidaVO cr : lista) {
            System.out.println(cr.getIdComida() + " | " + cr.getNombreComida() + " | "
                + cr.getPrecio() + " | " + cr.getIngredientesMenu());
        }
    }

    public void mostrarVentas(List<VentaVO> lista) {
        System.out.println("--- VENTAS ---");
        if (lista.isEmpty()) System.out.println("No hay ventas.");
        for (VentaVO v : lista) {
            System.out.println("ID:" + v.getIdVenta() + " | Fecha:" + v.getFechaHora() +
                    " | Cliente:" + (v.getCliente() != null ? v.getCliente().getNombre() : "N/A") +
                    " | Comida:" + (v.getComidaRapida() != null ? v.getComidaRapida().getNombreComida() : "N/A"));
        }
    }
}
