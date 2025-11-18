package view;

import javax.swing.*;
import java.awt.*;

public class RegistroUsuarioView extends JFrame {

    public JTextField txtNombre;
    public JTextField txtCorreo;
    public JPasswordField txtPassword;
    public JComboBox<String> comboRol;
    public JButton btnRegistrar;
    public JButton btnVolver;

    public RegistroUsuarioView() {
        setTitle("Registrar Usuario");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Registro de Nuevo Usuario");
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField();
        panel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        txtCorreo = new JTextField();
        panel.add(txtCorreo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField();
        panel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        comboRol = new JComboBox<>(new String[]{"Cliente", "Administrador"});
        panel.add(comboRol, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        btnRegistrar = new JButton("Registrar");
        panel.add(btnRegistrar, gbc);

        gbc.gridy = 6;
        btnVolver = new JButton("Volver");
        panel.add(btnVolver, gbc);

        add(panel);
    }
}
