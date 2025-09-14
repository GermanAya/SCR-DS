package controlador;

import dao.*;
import modelo.*;
import vista.Vista;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controlador {

    public static void main(String[] args) {
        // Crea las tablas en la base de datos si aún no existen
        crearTablasSiNoExisten();

        // Instancia la vista y los DAO para manejar clientes, comidas y ventas
        Vista vista = new Vista();
        ClienteDAO clienteDao = new ClienteDAOImpl();
        ComidaRapidaDAO comidaDao = new ComidaRapidaDAOImpl();
        VentaDAO ventaDao = new VentaDAOImpl();

        // Formato de fecha y hora para registrar ventas
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        int opcion;
        do {
            // Muestra el menú principal de opciones
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("------ AGREGAR ------");
            System.out.println(" 1. Agregar cliente");
            System.out.println(" 2. Agregar comida");
            System.out.println(" 3. Registrar venta");
            System.out.println("------ LISTAR -------");
            System.out.println(" 4. Listar clientes");
            System.out.println(" 5. Listar comidas");
            System.out.println(" 6. Listar ventas");
            System.out.println("------ EDITAR -------");
            System.out.println(" 7. Editar cliente");
            System.out.println(" 8. Editar comida");
            System.out.println(" 9. Editar venta");
            System.out.println("------ ELIMINAR -----");
            System.out.println("10. Eliminar cliente");
            System.out.println("11. Eliminar comida");
            System.out.println("12. Eliminar venta");
            System.out.println("---------------------");
            System.out.println(" 0. Salir");
            System.out.print("Seleccione una opción: ");

            // Lee la opción ingresada por el usuario y maneja errores de entrada
            try {
                opcion = Integer.parseInt(new java.util.Scanner(System.in).nextLine());
            } catch (Exception e) {
                opcion = -1; // opción inválida
            }

            // Switch principal que gestiona todas las operaciones
            switch (opcion) {
                // ===== AGREGAR =====
                case 1 -> vista.mostrarMensaje(clienteDao.insertarCliente(vista.leerCliente()));
                case 2 -> vista.mostrarMensaje(comidaDao.insertarComida(vista.leerComida()));
                case 3 -> { // Registrar venta
                    String ced = vista.pedirCedulaParaVenta();
                    int idCom = vista.pedirIdComidaParaVenta();
                    // Busca el cliente y la comida en las listas
                    ClienteVO cliente = buscarClientePorCedula(clienteDao.listarClientes(), ced);
                    ComidaRapidaVO comida = buscarComidaPorId(comidaDao.listarComidas(), idCom);
                    if (cliente == null || comida == null) {
                        vista.mostrarMensaje("Cliente o comida no encontrados.");
                        break;
                    }
                    // Crea un objeto VentaVO y lo inserta en la base de datos
                    VentaVO v = new VentaVO();
                    v.setFechaHora(LocalDateTime.now().format(fmt));
                    v.setCliente(cliente);
                    v.setComidaRapida(comida);
                    vista.mostrarMensaje(ventaDao.insertarVenta(v));
                }

                // ===== LISTAR =====
                case 4 -> vista.mostrarClientes(clienteDao.listarClientes());
                case 5 -> vista.mostrarComidas(comidaDao.listarComidas());
                case 6 -> vista.mostrarVentas(ventaDao.listarVentas());

                // ===== EDITAR =====
                case 7 -> { // Editar cliente
                    String ced = vista.pedirCedulaParaEditarCliente();
                    ClienteVO edit = vista.leerClienteEditado();
                    edit.setCedula(ced);
                    vista.mostrarMensaje(clienteDao.actualizarCliente(edit));
                }
                case 8 -> { // Editar comida
                    int id = vista.pedirIdComidaParaEditar();
                    ComidaRapidaVO edit = vista.leerComidaEditada();
                    edit.setIdComida(id);
                    vista.mostrarMensaje(comidaDao.actualizarComida(edit));
                }
                case 9 -> { // Editar venta
                    int idVenta = vista.pedirIdVentaParaEditar();
                    VentaVO vEdit = vista.leerVentaEditada();
                    vEdit.setIdVenta(idVenta);
                    vista.mostrarMensaje(ventaDao.actualizarVenta(vEdit));
                }

                // ===== ELIMINAR =====
                case 10 -> vista.mostrarMensaje(clienteDao.eliminarCliente(vista.pedirCedulaParaEliminar()));
                case 11 -> vista.mostrarMensaje(comidaDao.eliminarComida(vista.pedirIdComidaParaEliminar()));
                case 12 -> vista.mostrarMensaje(ventaDao.eliminarVenta(vista.pedirIdVentaParaEliminar()));

                case 0 -> vista.mostrarMensaje("Saliendo...");
                default -> vista.mostrarMensaje("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // Métodos auxiliares para buscar cliente y comida en listas
    private static ClienteVO buscarClientePorCedula(List<ClienteVO> lista, String cedula) {
        for (ClienteVO c : lista) if (c.getCedula().equals(cedula)) return c;
        return null;
    }

    private static ComidaRapidaVO buscarComidaPorId(List<ComidaRapidaVO> lista, int id) {
        for (ComidaRapidaVO cr : lista) if (cr.getIdComida() == id) return cr;
        return null;
    }

    // Crea las tablas en la base de datos SQLite si no existen
    private static void crearTablasSiNoExisten() {
        String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente (" +
                "cedula TEXT PRIMARY KEY, nombre TEXT, celular TEXT);";
        String sqlComida = "CREATE TABLE IF NOT EXISTS comida_rapida (" +
                "idComida INTEGER PRIMARY KEY AUTOINCREMENT, nombreComida TEXT, precio REAL, ingredientesMenu TEXT);";
        String sqlVenta = "CREATE TABLE IF NOT EXISTS venta (" +
                "idVenta INTEGER PRIMARY KEY AUTOINCREMENT, fechaHora TEXT, cedulaCliente TEXT, idComida INTEGER," +
                "FOREIGN KEY (cedulaCliente) REFERENCES cliente(cedula)," +
                "FOREIGN KEY (idComida) REFERENCES comida_rapida(idComida));";
        try (Connection c = modelo.Conexion.getConnection();
             Statement st = c.createStatement()) {
            st.execute(sqlCliente);
            st.execute(sqlComida);
            st.execute(sqlVenta);
        } catch (Exception e) {
            System.err.println("Error creando tablas: " + e.getMessage());
        }
    }
}
