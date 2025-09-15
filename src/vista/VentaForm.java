package vista;

import dao.ClienteDAO;
import dao.ComidaRapidaDAO;
import dao.VentaDAO;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.ClienteVO;
import modelo.ComidaRapidaVO;
import modelo.VentaVO;

public class VentaForm extends JFrame {
    private VentaDAO ventaDao;
    private ClienteDAO clienteDao;
    private ComidaRapidaDAO comidaDao;

    private JTextField txtId, txtCedulaCliente, txtIdComida;
    private JButton btnCrear, btnListar, btnActualizar, btnEliminar;

    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;

    public VentaForm(VentaDAO ventaDao, ClienteDAO clienteDao, ComidaRapidaDAO comidaDao) {
        this.ventaDao = ventaDao;
        this.clienteDao = clienteDao;
        this.comidaDao = comidaDao;

        setTitle("Gestión de Ventas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== PANEL SUPERIOR: FORMULARIO =====
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("ID Venta:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Cédula Cliente:"));
        txtCedulaCliente = new JTextField();
        panelForm.add(txtCedulaCliente);

        panelForm.add(new JLabel("ID Comida:"));
        txtIdComida = new JTextField();
        panelForm.add(txtIdComida);

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
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Fecha", "Cliente", "Comida"}, 0);
        tablaVentas = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaVentas);

        // ===== PANEL CENTRAL =====
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelBotones, BorderLayout.NORTH);
        panelCentro.add(scroll, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // ===== EVENTOS =====
        btnCrear.addActionListener(e -> crearVenta());
        btnListar.addActionListener(e -> listarVentas());
        btnActualizar.addActionListener(e -> actualizarVenta());
        btnEliminar.addActionListener(e -> eliminarVenta());
    }

    // ===== MÉTODOS CRUD =====
    private void crearVenta() {
        try {
            VentaVO v = new VentaVO();

            // Generar fecha actual automáticamente
            String fechaActual = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            v.setFechaHora(fechaActual);

            ClienteVO c = new ClienteVO();
            c.setCedula(txtCedulaCliente.getText());
            v.setCliente(c);

            ComidaRapidaVO cr = new ComidaRapidaVO();
            cr.setIdComida(Integer.parseInt(txtIdComida.getText()));
            v.setComidaRapida(cr);

            String mensaje = ventaDao.insertarVenta(v);
            JOptionPane.showMessageDialog(this, mensaje);

            limpiarCampos(); // limpiar campos tras crear
            listarVentas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear: " + e.getMessage());
        }
    }

    private void listarVentas() {
        modeloTabla.setRowCount(0);
        List<VentaVO> lista = ventaDao.listarVentas();
        for (VentaVO v : lista) {
            modeloTabla.addRow(new Object[]{
                    v.getIdVenta(),
                    v.getFechaHora(),
                    v.getCliente() != null ? v.getCliente().getNombre() : "N/A",
                    v.getComidaRapida() != null ? v.getComidaRapida().getNombreComida() : "N/A"
            });
        }
    }

    private void actualizarVenta() {
        try {
            VentaVO v = new VentaVO();
            v.setIdVenta(Integer.parseInt(txtId.getText()));

            // Si quieres que al actualizar también se cambie la fecha a la actual:
            String fechaActual = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            v.setFechaHora(fechaActual);

            ClienteVO c = new ClienteVO();
            c.setCedula(txtCedulaCliente.getText());
            v.setCliente(c);

            ComidaRapidaVO cr = new ComidaRapidaVO();
            cr.setIdComida(Integer.parseInt(txtIdComida.getText()));
            v.setComidaRapida(cr);

            String mensaje = ventaDao.actualizarVenta(v);
            JOptionPane.showMessageDialog(this, mensaje);

            limpiarCampos();
            listarVentas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarVenta() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String mensaje = ventaDao.eliminarVenta(id);
            JOptionPane.showMessageDialog(this, mensaje);

            limpiarCampos();
            listarVentas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
    }

    // ===== LIMPIAR CAMPOS =====
    private void limpiarCampos() {
        txtId.setText("");
        txtCedulaCliente.setText("");
        txtIdComida.setText("");
    }
}
