package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static javax.swing.SwingConstants.CENTER;
import model.Usuario;
import services.AsientoService;
import services.FuncionService;
import services.PeliculasService;
import services.ReservaService;
import services.SalaService;
import services.UsuarioService;
import utils.SessionManager;
import utils.components.AppStyle;
import utils.components.IconPasswordField;
import utils.components.IconTextField;
import utils.components.PrimaryButton;

public class LoginView extends JFrame {

    public JButton btnIngresar;
    public JLabel linkRegistro;

    public JTextField txtNombreRegistro;
    public JTextField txtCorreoRegistro;
    public JTextField txtContrasenaRegistro;
    public JComboBox<String> comboRolRegistro;
    public JButton btnRegistrar;
    public JButton btnVolver;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel;
    private final static String LOGIN_VIEW = "LoginCard";
    private final static String REGISTRO_VIEW = "RegistroCard";

    private final UsuarioService usuarioService = new UsuarioService();

    public LoginView() {

        setTitle("Inicio de Sesión - Cinex");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // PANEL IZQUIERDO - IMAGEN
        ImageIcon iconoOriginal = new ImageIcon("src/assets/login.png");
        Image img = iconoOriginal.getImage();

        int newHeight = 450;

        int imgWidth = img.getWidth(null);
        int imgHeight = img.getHeight(null);
        float ratio = (float) imgWidth / imgHeight;
        int newWidth = (int) (newHeight * ratio);

        Image imgEscalada = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel panelIzquierdo = new JLabel(new ImageIcon(imgEscalada));
        panelIzquierdo.setPreferredSize(new Dimension(350, 450));

        add(panelIzquierdo, BorderLayout.WEST);
        cardPanel = new JPanel(cardLayout);
        JPanel loginPanel = createLoginPanel();
        JPanel registroPanel = createRegistroPanel();
        cardPanel.add(loginPanel, LOGIN_VIEW);
        cardPanel.add(registroPanel, REGISTRO_VIEW);
        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createLoginPanel() {
        JPanel panelDerechoLogin = new JPanel(new GridBagLayout());
        panelDerechoLogin.setBackground(AppStyle.COLOR_FONDO);
        panelDerechoLogin.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        String textRegistro = "¿No estás registrado? Crea una cuenta";

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setFont(AppStyle.FUENTE_TITULO);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        lblTitulo.setForeground(AppStyle.FONT_COLOR_PRIMARIO);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelDerechoLogin.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
        panelDerechoLogin.add(lblUsuario, gbc);

        gbc.gridx = 1;
        IconTextField txtUsuario = new IconTextField("src/assets/user-icon.png", 14);
        panelDerechoLogin.add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
        panelDerechoLogin.add(lblContrasena, gbc);

        gbc.gridx = 1;
        IconPasswordField txtContrasena = new IconPasswordField("src/assets/lock-icon.png", 14);
        panelDerechoLogin.add(txtContrasena, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        btnIngresar = new PrimaryButton("Ingresar");
        btnIngresar.setPreferredSize(new Dimension(150, 35));
        panelDerechoLogin.add(btnIngresar, gbc);

        gbc.gridy = 4;
        linkRegistro = new JLabel(textRegistro);
        linkRegistro.setForeground(AppStyle.COLOR_PRIMARIO);
        linkRegistro.setHorizontalAlignment(CENTER);
        linkRegistro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        linkRegistro.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, REGISTRO_VIEW);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                linkRegistro.setText(String.format("<HTML><U>%s</U></HTML>", textRegistro));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                linkRegistro.setText(textRegistro);
            }

        });

        panelDerechoLogin.add(linkRegistro, gbc);

        btnIngresar.addActionListener(e -> {
            String textUsuario = txtUsuario.getText();
            char[] charPassword = txtContrasena.getPassword();
            String password = new String(charPassword);

            Usuario usuario = usuarioService.login(textUsuario, password);

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Usuario y/o Contrasena incorrectos");
            } else {
                SessionManager.getInstance().iniciarSesion(usuario);
                
                if(SessionManager.getInstance().isAdmin()){
                    SalaService salaService = new SalaService();
                    PeliculasService  peliculasService = new PeliculasService();
                    FuncionService funcionService = new FuncionService();  
                    ReservaService reservaService = new ReservaService();
                    UsuarioService usuarioService = new UsuarioService();
                    AsientoService asientoService = new AsientoService();
                    
                    AdminMenuGUI menu = new AdminMenuGUI(peliculasService, funcionService, salaService, reservaService,usuarioService, asientoService);
                    this.dispose();
                } else {
                    MenuView menuView = new MenuView();
                    menuView.setVisible(true);
                    this.dispose();
                }
                
            }
        });

        return panelDerechoLogin;
    }

    private JPanel createRegistroPanel() {
        JPanel panelDerechoRegistro = new JPanel(new GridBagLayout());
        panelDerechoRegistro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelDerechoRegistro.setBackground(AppStyle.COLOR_FONDO);
        GridBagConstraints gbcRegistro = new GridBagConstraints();
        gbcRegistro.insets = new Insets(8, 8, 8, 8);
        gbcRegistro.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTituloRegistro = new JLabel("Registro de Nuevo Usuario");
        lblTituloRegistro.setHorizontalAlignment(JLabel.CENTER);
        lblTituloRegistro.setFont(AppStyle.FUENTE_TITULO);
        lblTituloRegistro.setForeground(AppStyle.FONT_COLOR_PRIMARIO);

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 0;
        gbcRegistro.gridwidth = 2;
        panelDerechoRegistro.add(lblTituloRegistro, gbcRegistro);

        gbcRegistro.gridwidth = 1;

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 1;

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
        panelDerechoRegistro.add(lblNombre, gbcRegistro);
        gbcRegistro.gridx = 1;
        txtNombreRegistro = new JTextField();
        panelDerechoRegistro.add(txtNombreRegistro, gbcRegistro);

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 2;
        panelDerechoRegistro.add(new JLabel("Usuario:"), gbcRegistro);
        gbcRegistro.gridx = 1;
        JTextField txtUsuario = new JTextField();
        panelDerechoRegistro.add(txtUsuario, gbcRegistro);

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 3;
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
        panelDerechoRegistro.add(lblContrasena, gbcRegistro);
        gbcRegistro.gridx = 1;
        txtContrasenaRegistro = new JTextField();
        panelDerechoRegistro.add(txtContrasenaRegistro, gbcRegistro);

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 4;
        gbcRegistro.gridwidth = 2;
        btnRegistrar = new PrimaryButton("Registrar");
        btnRegistrar.addActionListener(e -> {
            String rol = "CLIENTE";

            try {
                usuarioService.createUser(txtUsuario.getText(), txtNombreRegistro.getText(), txtContrasenaRegistro.getText(), rol);
               JOptionPane.showMessageDialog(this, "Registro exitoso para: " + txtUsuario.getText());
               
               txtUsuario.setText("");
               txtNombreRegistro.setText("");
               txtContrasenaRegistro.setText("");
               cardLayout.show(cardPanel, LOGIN_VIEW);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelDerechoRegistro.add(btnRegistrar, gbcRegistro);

        gbcRegistro.gridy = 5;
        btnVolver = new JButton("Volver");
        ImageIcon backIcon = new ImageIcon("src/assets/back-arrow-icon.png");
        Image backImg = backIcon.getImage();
        Image backImgEscalada = backImg.getScaledInstance(8, 8, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(backImgEscalada);
        btnVolver.setIcon(iconoEscalado);
        btnVolver.setBackground(null);
        btnVolver.setBorder(null);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);

        panelDerechoRegistro.add(btnVolver, gbcRegistro);

        btnVolver.addActionListener(e -> cardLayout.show(cardPanel, LOGIN_VIEW));

        return panelDerechoRegistro;
    }
}
