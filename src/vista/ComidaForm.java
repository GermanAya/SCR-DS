package vista;

import dao.ComidaRapidaDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.ComidaRapidaVO;

public class ComidaForm extends JFrame {
    private ComidaRapidaDAO comidaDao;

    private JTextField txtId, txtNombre, txtPrecio, txtIngredientes;
    private JButton btnCrear, btnListar, btnActualizar, btnEliminar;

    private JTable tablaComidas;
    private DefaultTableModel modeloTabla;

    public ComidaForm(ComidaRapidaDAO comidaDao) {
        this.comidaDao = comidaDao;

        setTitle("Gestión de Comidas Rápidas");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== PANEL SUPERIOR: FORMULARIO =====
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelForm.add(txtPrecio);

        panelForm.add(new JLabel("Ingredientes:"));
        txtIngredientes = new JTextField();
        panelForm.add(txtIngredientes);

        add(panelForm, BorderLayout.NORTH);

        // ===== PANEL BOTONES =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnCrear = new JButton("Crear");
        btnListar = new JButton("Listar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnCrear);
        panelBotones.add(btnListar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        // ===== TABLA =====
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Ingredientes"}, 0);
        tablaComidas = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaComidas);

        // ===== PANEL CENTRAL =====
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelBotones, BorderLayout.NORTH);
        panelCentro.add(scroll, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // ===== EVENTOS =====
        btnCrear.addActionListener(e -> crearComida());
        btnListar.addActionListener(e -> listarComidas());
        btnActualizar.addActionListener(e -> actualizarComida());
        btnEliminar.addActionListener(e -> eliminarComida());
    }

    // ===== MÉTODOS CRUD =====
    private void crearComida() {
        try {
            ComidaRapidaVO cr = new ComidaRapidaVO();
            cr.setNombreComida(txtNombre.getText());
            cr.setPrecio(Double.parseDouble(txtPrecio.getText()));
            cr.setIngredientesMenu(txtIngredientes.getText());

            String mensaje = comidaDao.insertarComida(cr);
            JOptionPane.showMessageDialog(this, mensaje);
            listarComidas();
            vaciarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear: " + e.getMessage());
        }
    }

    private void listarComidas() {
        modeloTabla.setRowCount(0);
        List<ComidaRapidaVO> lista = comidaDao.listarComidas();
        for (ComidaRapidaVO cr : lista) {
            modeloTabla.addRow(new Object[]{
                    cr.getIdComida(), cr.getNombreComida(), cr.getPrecio(), cr.getIngredientesMenu()
            });
        }
    }

    private void actualizarComida() {
        try {
            ComidaRapidaVO cr = new ComidaRapidaVO();
            cr.setIdComida(Integer.parseInt(txtId.getText()));
            cr.setNombreComida(txtNombre.getText());
            cr.setPrecio(Double.parseDouble(txtPrecio.getText()));
            cr.setIngredientesMenu(txtIngredientes.getText());

            String mensaje = comidaDao.actualizarComida(cr);
            JOptionPane.showMessageDialog(this, mensaje);
            listarComidas();
            vaciarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarComida() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String mensaje = comidaDao.eliminarComida(id);
            JOptionPane.showMessageDialog(this, mensaje);
            listarComidas();
            vaciarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
    }

    // ===== MÉTODO PARA VACIAR CAMPOS =====
    private void vaciarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtIngredientes.setText("");
    }
}
