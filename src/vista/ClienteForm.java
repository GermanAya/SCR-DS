package vista;

import dao.ClienteDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.ClienteVO;

public class ClienteForm extends JFrame {
    private ClienteDAO clienteDao;

    private JTextField txtCedula, txtNombre, txtCelular;
    private JButton btnCrear, btnListar, btnActualizar, btnEliminar;

    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public ClienteForm(ClienteDAO clienteDao) {
        this.clienteDao = clienteDao;

        setTitle("Gestión de Clientes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== PANEL SUPERIOR: FORMULARIO =====
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelForm.add(txtCedula);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Celular:"));
        txtCelular = new JTextField();
        panelForm.add(txtCelular);

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
        modeloTabla = new DefaultTableModel(new String[]{"Cédula", "Nombre", "Celular"}, 0);
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaClientes);

        // ===== PANEL CENTRAL (BOTONES + TABLA) =====
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelBotones, BorderLayout.NORTH);
        panelCentro.add(scroll, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // ===== EVENTOS =====
        btnCrear.addActionListener(e -> crearCliente());
        btnListar.addActionListener(e -> listarClientes());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
    }

    // ===== MÉTODOS CRUD =====
    private void crearCliente() {
        ClienteVO c = new ClienteVO(txtCedula.getText(), txtNombre.getText(), txtCelular.getText());
        String mensaje = clienteDao.insertarCliente(c);
        JOptionPane.showMessageDialog(this, mensaje);
        listarClientes(); 
        vaciarCampos();
    }

    private void listarClientes() {
        modeloTabla.setRowCount(0); 
        List<ClienteVO> lista = clienteDao.listarClientes();
        for (ClienteVO c : lista) {
            modeloTabla.addRow(new Object[]{c.getCedula(), c.getNombre(), c.getCelular()});
        }
    }

    private void actualizarCliente() {
        ClienteVO c = new ClienteVO(txtCedula.getText(), txtNombre.getText(), txtCelular.getText());
        String mensaje = clienteDao.actualizarCliente(c);
        JOptionPane.showMessageDialog(this, mensaje);
        listarClientes();
        vaciarCampos();
    }

    private void eliminarCliente() {
        String cedula = txtCedula.getText();
        String mensaje = clienteDao.eliminarCliente(cedula);
        JOptionPane.showMessageDialog(this, mensaje);
        listarClientes();
        vaciarCampos();
    }

    // ===== MÉTODO PARA VACIAR CAMPOS =====
    private void vaciarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtCelular.setText("");
    }
}
