package view;

import javax.swing.*;

public class MenuPrincipalView extends JFrame {

    public JButton btnPeliculas;
    public JButton btnFunciones;
    public JButton btnSalas;
    public JButton btnReservas;
    public JButton btnCerrar;

    public MenuPrincipalView() {
        setTitle("Menú Principal - Cine App");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lbl = new JLabel("Menú Principal");
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        lbl.setFont(lbl.getFont().deriveFont(20f));
        panel.add(lbl);

        panel.add(Box.createVerticalStrut(15));

        btnPeliculas = new JButton("Gestionar Películas");
        btnFunciones = new JButton("Gestionar Funciones");
        btnSalas = new JButton("Gestionar Salas");
        btnReservas = new JButton("Consultar Reservas");
        btnCerrar = new JButton("Cerrar Sesión");

        btnPeliculas.setAlignmentX(CENTER_ALIGNMENT);
        btnFunciones.setAlignmentX(CENTER_ALIGNMENT);
        btnSalas.setAlignmentX(CENTER_ALIGNMENT);
        btnReservas.setAlignmentX(CENTER_ALIGNMENT);
        btnCerrar.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(btnPeliculas);
        panel.add(btnFunciones);
        panel.add(btnSalas);
        panel.add(btnReservas);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnCerrar);

        add(panel);
    }
}
