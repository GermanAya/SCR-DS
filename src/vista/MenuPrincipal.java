package vista;

import java.awt.*;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    private JButton btnClientes, btnComidas, btnVentas;

    public MenuPrincipal() {
        setTitle("Gestor Comidas Rápidas");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // ===== Imagen arriba =====
        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        // Cargar imagen desde carpeta src/imagenes
        ImageIcon icon = new ImageIcon("src/imagenes/2921822.png");
        Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(img));

        panel.add(lblImagen, BorderLayout.NORTH);

        // ===== Panel central =====
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        // ===== Título =====
        JLabel lblTitulo = new JLabel("Comidas Rápidas la UD", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(200, 0, 0)); // rojo oscuro
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentro.add(Box.createVerticalStrut(15)); // espacio superior
        panelCentro.add(lblTitulo);
        panelCentro.add(Box.createVerticalStrut(25)); // espacio debajo del título

        // ===== Botones centrados =====
        btnClientes = crearBotonRojo("Clientes");
        btnComidas = crearBotonRojo("Comidas Rápidas");
        btnVentas = crearBotonRojo("Ventas");

        btnClientes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnComidas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVentas.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentro.add(btnClientes);
        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(btnComidas);
        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(btnVentas);

        panel.add(panelCentro, BorderLayout.CENTER);

        add(panel);
    }

    // Método auxiliar para crear botones rojos bonitos
    private JButton crearBotonRojo(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(200, 60));
        btn.setMaximumSize(new Dimension(200, 60)); 
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(Color.RED);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    // Getters
    public JButton getBtnClientes() { return btnClientes; }
    public JButton getBtnComidas() { return btnComidas; }
    public JButton getBtnVentas() { return btnVentas; }
}
