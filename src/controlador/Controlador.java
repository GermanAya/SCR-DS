package controlador;

import dao.*;
import vista.*;

public class Controlador {
    private MenuPrincipal menu;
    private ClienteDAO clienteDao;
    private ComidaRapidaDAO comidaDao;
    private VentaDAO ventaDao;

    public Controlador() {
        menu = new MenuPrincipal();
        clienteDao = new ClienteDAOImpl();
        comidaDao = new ComidaRapidaDAOImpl();
        ventaDao = new VentaDAOImpl();

        menu.getBtnClientes().addActionListener(e -> abrirClientes());
        menu.getBtnComidas().addActionListener(e -> abrirComidas());
        menu.getBtnVentas().addActionListener(e -> abrirVentas());
    }

    public void iniciar() {
        menu.setVisible(true);
    }

    public void abrirClientes() {
        new ClienteForm(clienteDao).setVisible(true);
    }

    public void abrirComidas() {
        new ComidaForm(comidaDao).setVisible(true);
    }

    public void abrirVentas() {
        new VentaForm(ventaDao, clienteDao, comidaDao).setVisible(true);
    }

    // 🔹 Aquí está el main que ejecuta todo
    public static void main(String[] args) {
        Controlador app = new Controlador();
        app.iniciar();
    }
}
