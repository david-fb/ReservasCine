package main;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Sala;
import services.SalaService;

/**
 *
 * @author Sofia
 */
public class VentanaSalas extends JFrame {

    private SalaService salaService = new SalaService();

    public VentanaSalas() {

        setTitle("Gestion de Salas");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // PANEL IZQUIERDO (MENÚ)
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1, 10, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        

        JButton btnRegistrar = crearBoton("Registrar Sala");
        JButton btnListar = crearBoton("Listar Salas");
        JButton btnActualizar = crearBoton("Actualizar Sala");
        JButton btnEliminar = crearBoton("Eliminar Sala");
        JButton btnVolver = crearBoton("Volver al Menú");

        panelMenu.add(btnRegistrar);
        panelMenu.add(btnListar);
        panelMenu.add(btnActualizar);
        panelMenu.add(btnEliminar);
        panelMenu.add(btnVolver);

        add(panelMenu, BorderLayout.WEST);
        
        

        // PANEL CENTRAL (FIJO)
        /*JPanel panelCentro = new JPanel(new GridBagLayout());
        JLabel imagen_titulo = new JLabel();
        ImageIcon icon = new ImageIcon("src/assets/sala.jpeg");
        Image imgOriginal = icon.getImage();
        imagen_titulo.setIcon(new ImageIcon(imgOriginal));
        panelCentro.add(imagen_titulo);
        
        add(panelCentro, BorderLayout.CENTER);*/
        
        JPanel panelCentro = new JPanel() {

            ImageIcon icon = new ImageIcon("src/assets/sala.jpeg");
            Image imgOriginal = icon.getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                // Tamaño original
                int imgWidth = imgOriginal.getWidth(null);
                int imgHeight = imgOriginal.getHeight(null);

                double escPanel = (double) panelWidth / panelHeight;
                double escImg = (double) imgWidth / imgHeight;

                int newWidth, newHeight;

                if (escPanel > escImg) {
                    newHeight = panelHeight;
                    newWidth = (int) (newHeight * escImg);
                } else {
                    newWidth = panelWidth;
                    newHeight = (int) (newWidth / escImg);
                }

                // Centrar la imagen
                int x = (panelWidth - newWidth) / 2;
                int y = (panelHeight - newHeight) / 2;

                g.drawImage(imgOriginal, x, y, newWidth, newHeight, this);
            }
        };

        add(panelCentro, BorderLayout.CENTER);
        

        // EVENTOS
        btnRegistrar.addActionListener(e -> registrarSala());
        btnListar.addActionListener(e -> listarSalas());
        btnActualizar.addActionListener(e -> actualizarSala());
        btnEliminar.addActionListener(e -> eliminarSala());
        btnVolver.addActionListener(e -> dispose());
    }

    private JButton crearBoton(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBackground(new Color(70, 130, 180));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    // REGISTRAR
    private void registrarSala() {

        JTextField txtNombre = new JTextField();
        JTextField txtCapacidad = new JTextField();
        JTextField txtTipo = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Capacidad:"));
        panel.add(txtCapacidad);

        panel.add(new JLabel("Tipo:"));
        panel.add(txtTipo);

        int op = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Registrar Sala",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (op == JOptionPane.OK_OPTION) {
            try {
//                salaService.registrarSala(
//                        txtNombre.getText(),
//                        Integer.parseInt(txtCapacidad.getText()),
//                        txtTipo.getText()
//                );

                JOptionPane.showMessageDialog(this, "Sala registrada correctamente");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    // LISTADO DE LAS SALAS
    private void listarSalas() {

        List<Sala> lista = salaService.listarSalas();

        String[] columnas = {"ID", "Nombre", "Capacidad", "Tipo"};
        Object[][] datos = new Object[lista.size()][4];

        for (int i = 0; i < lista.size(); i++) {
            Sala s = lista.get(i);
            datos[i][0] = s.getIdSala();
            datos[i][1] = s.getNombre();
            datos[i][2] = s.getCapacidad();
            datos[i][3] = s.getTipo();
        }

        JTable tabla = new JTable(datos, columnas);
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(600, 300));

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Listado de Salas",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // ACTUALIZAR LA SALA PARA EL ADMIN
    private void actualizarSala() {

        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCapacidad = new JTextField();
        JTextField txtTipo = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("ID Sala:"));
        panel.add(txtId);

        panel.add(new JLabel("Nuevo Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Nueva Capacidad:"));
        panel.add(txtCapacidad);

        panel.add(new JLabel("Nuevo Tipo:"));
        panel.add(txtTipo);

        int op = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Actualizar Sala",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (op == JOptionPane.OK_OPTION) {
            try {

//                salaService.actualizarSala(
//                        Integer.parseInt(txtId.getText()),
//                        txtNombre.getText(),
//                        txtCapacidad.getText().isEmpty() ? -1 : Integer.parseInt(txtCapacidad.getText()),
//                        txtTipo.getText()
//                );

                JOptionPane.showMessageDialog(this, "Sala actualizada correctamente");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    // ELIMINAR SALA
    private void eliminarSala() {

        JTextField txtId = new JTextField();

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.add(new JLabel("ID de la Sala:"));
        panel.add(txtId);

        int op = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Eliminar Sala",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (op == JOptionPane.OK_OPTION) {
            try {
                salaService.deleteSala(Integer.parseInt(txtId.getText()));
                JOptionPane.showMessageDialog(this, "Sala eliminada correctamente");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaSalas().setVisible(true));
    } 
}