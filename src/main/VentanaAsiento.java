package main;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Asiento;
import services.AsientoService;
/**
 *
 * @author Sofia
 */
public class VentanaAsiento extends JFrame{
    
    private JSplitPane splitPane;
    private AsientoService asientoService = new AsientoService();

   public VentanaAsiento() {

        setTitle("Gestión de Asientos - Cine");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --------------------------
        // PANEL IZQUIERDO (MENÚ)
        // --------------------------
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(4, 1, 10, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnMapa = crearBoton("Ver Mapa de Asientos");
        JButton btnListar = crearBoton("Listar Asientos");
        JButton btnCrear = crearBoton("Crear Asiento");
        JButton btnVolver = crearBoton("Volver");

        panelMenu.add(btnMapa);
        panelMenu.add(btnListar);
        panelMenu.add(btnCrear);
        panelMenu.add(btnVolver);

        // --------------------------
        // PANEL CENTRAL INICIAL
        // --------------------------
        JPanel panelInicio = new JPanel(new GridBagLayout());
        JLabel titulo = new JLabel("Gestión de Asientos");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        panelInicio.add(titulo);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelMenu, panelInicio);
        splitPane.setDividerLocation(250);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);

        // EVENTOS
        btnMapa.addActionListener(e -> splitPane.setRightComponent(new PanelAsientosCine()));
        btnListar.addActionListener(e -> mostrarTablaAsientos());
        btnCrear.addActionListener(e -> crearAsiento());
        btnVolver.addActionListener(e -> splitPane.setRightComponent(panelInicio));
    }

    // -------------------------------------------------------------
    // BOTÓN BONITO
    // -------------------------------------------------------------
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    // -------------------------------------------------------------
    // TABLA DE ASIENTOS
    // -------------------------------------------------------------
    private void mostrarTablaAsientos() {

        List<Asiento> lista = asientoService.getTodosLosAsientos();

        String[] columnas = {"ID", "Fila", "Número", "Estado", "Sala"};
        Object[][] datos = new Object[lista.size()][5];

        for (int i = 0; i < lista.size(); i++) {
            Asiento a = lista.get(i);
            datos[i][0] = a.getIdAsiento();
            datos[i][1] = a.getFila();
            datos[i][2] = a.getNumero();
            datos[i][3] = a.getEstado();
            datos[i][4] = a.getSala();
        }

        JTable tabla = new JTable(datos, columnas);
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        splitPane.setRightComponent(scroll);
    }

    // -------------------------------------------------------------
    // CREAR ASIENTO
    // -------------------------------------------------------------
    private void crearAsiento() {

        JTextField txtFila = new JTextField();
        JTextField txtNumero = new JTextField();
        JTextField txtSala = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Fila:"));
        panel.add(txtFila);
        panel.add(new JLabel("Número:"));
        panel.add(txtNumero);
        panel.add(new JLabel("Sala:"));
        panel.add(txtSala);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Registrar Asiento",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                asientoService.CrearAsiento(
                        txtFila.getText(),
                        Integer.parseInt(txtNumero.getText()),
                        "Disponible",
                        txtSala.getText()
                );

                JOptionPane.showMessageDialog(this, "Asiento registrado correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    // -------------------------------------------------------------
    // PANEL DE ASIENTOS TIPO CINE
    // -------------------------------------------------------------
    class PanelAsientosCine extends JPanel {

        public PanelAsientosCine() {
            setLayout(new BorderLayout());

            JLabel titulo = new JLabel("Mapa de Asientos", JLabel.CENTER);
            titulo.setFont(new Font("Arial", Font.BOLD, 28));
            titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

            add(titulo, BorderLayout.NORTH);

            JPanel grid = new JPanel();
            grid.setLayout(new GridLayout(0, 10, 8, 8));
            grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            List<Asiento> lista = asientoService.getTodosLosAsientos();

            for (Asiento a : lista) {

                JButton btn = new JButton(a.getFila() + a.getNumero());
                btn.setPreferredSize(new Dimension(60, 40));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
                btn.setOpaque(true);
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (a.getEstado().equalsIgnoreCase("Disponible")) {
                    btn.setBackground(new Color(0, 180, 0));
                } else {
                    btn.setBackground(new Color(200, 0, 0));
                }

                btn.addActionListener(e -> manejarReserva(a, btn));

                grid.add(btn);
            }

            JScrollPane scroll = new JScrollPane(grid);
            add(scroll, BorderLayout.CENTER);
        }

        private void manejarReserva(Asiento asiento, JButton btn) {

            if (asiento.getEstado().equalsIgnoreCase("Ocupado")) {

                int r = JOptionPane.showConfirmDialog(
                        null,
                        "¿Deseas liberar este asiento?",
                        "Asiento Ocupado",
                        JOptionPane.YES_NO_OPTION
                );

                if (r == JOptionPane.YES_OPTION) {
                    asientoService.liberarAsiento(asiento.getIdAsiento());
                    btn.setBackground(new Color(0, 180, 0));
                }

            } else {

                int r = JOptionPane.showConfirmDialog(
                        null,
                        "¿Reservar este asiento?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

                if (r == JOptionPane.YES_OPTION) {
                    asientoService.reservarAsiento(asiento.getIdAsiento(), "", 0, "Ocupado", "");
                    btn.setBackground(new Color(200, 0, 0));
                }
            }
        }
    }

    // -------------------------------------------------------------
    // MAIN EJECUTABLE
    // -------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaAsiento().setVisible(true));
    }
}