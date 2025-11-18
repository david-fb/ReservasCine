/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author david
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import static javax.swing.SwingConstants.CENTER;
import model.Funcion;
import model.Usuario;
import services.FuncionService;
import utils.SessionManager;
import utils.components.AppStyle;
import utils.components.RoomPanel;
import utils.components.peliculaCard;
import utils.components.SecondaryButton;

public class MenuView extends JFrame {

    public JLabel lblUsuario;
    public JLabel lblRol;

    // Botones del menú
    public SecondaryButton btnFunciones;
    public SecondaryButton btnReservas;
    public SecondaryButton btnCerrarSesion;
    Usuario usuario = SessionManager.getInstance().getUsuarioActual();
    FuncionService funcionService = new FuncionService();
    
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel;
    private final static String WELCOME_VIEW = "WelcomeCard";
    private final static String FUNCIONES_VIEW = "FuncionesCard";
    private final static String RESERVA_VIEW = "ReservaCard";

    public MenuView() {

        setTitle("Cine App - Menú Principal");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // ----------- PANEL PRINCIPAL -----------
        cardPanel = new JPanel(cardLayout);
        JPanel mainPanel = createWelcomePanel();
        JPanel containerFunciones = createFuncionesPanel();
        cardPanel.add(mainPanel, WELCOME_VIEW);
        cardPanel.add(containerFunciones, FUNCIONES_VIEW);
        add(cardPanel, BorderLayout.CENTER);

        // ----------- PANEL LATERAL -----------
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(240, getHeight()));
        sidebar.setLayout(new GridBagLayout());
        sidebar.setBackground(AppStyle.COLOR_PRIMARIO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        lblUsuario = new JLabel(this.usuario.getUser_name());
        lblUsuario.setForeground(Color.WHITE);

        lblRol = new JLabel(this.usuario.getUser_role());
        lblRol.setForeground(Color.LIGHT_GRAY);

        gbc.gridy = 0;
        sidebar.add(lblUsuario, gbc);

        gbc.gridy = 1;
        sidebar.add(lblRol, gbc);

        // ----------- BOTONES -----------

        btnFunciones = new SecondaryButton("Funciones");
        gbc.gridy = 3;
        
        btnFunciones.addActionListener(e -> {
            cardLayout.show(cardPanel, FUNCIONES_VIEW);
        });
        sidebar.add(btnFunciones, gbc);

        btnReservas = new SecondaryButton("Reservas");
        gbc.gridy = 5;
        sidebar.add(btnReservas, gbc);

        btnCerrarSesion = new SecondaryButton("Cerrar Sesión");
        gbc.gridy = 6;
        sidebar.add(btnCerrarSesion, gbc);
        
        btnCerrarSesion.addActionListener(e -> {
            SessionManager.getInstance().cerrarSesion();
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
            
            dispose();
        });

        add(sidebar, BorderLayout.WEST);
    }
    
    private JPanel createWelcomePanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(AppStyle.COLOR_FONDO);

        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de Cine");
        lblBienvenida.setFont(AppStyle.FUENTE_TITULO);
        mainPanel.add(lblBienvenida);
        
        return mainPanel;
    }
    
    private JPanel createFuncionesPanel(){
        JPanel containerFunciones = new JPanel();
        containerFunciones.setLayout(new BorderLayout());
        containerFunciones.setBackground(AppStyle.COLOR_FONDO);
        
        JLabel lblFunciones = new JLabel("Funciones disponibles!");
        lblFunciones.setFont(AppStyle.FUENTE_TITULO);
        lblFunciones.setForeground(AppStyle.FONT_COLOR_PRIMARIO);
        lblFunciones.setHorizontalAlignment(CENTER);
        lblFunciones.setBackground(AppStyle.COLOR_FONDO);
        lblFunciones.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        
        JPanel panelFunciones = new JPanel();
        panelFunciones.setLayout(new GridLayout(2, 3, 20, 20));
        
        ArrayList<Funcion> funciones = funcionService.listarFunciones();
        for (Funcion p : funciones) {
            peliculaCard PeliculaCard = new peliculaCard(p);
            PeliculaCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createReserva(p);
                }
            });
            panelFunciones.add(PeliculaCard);
        }
        
        panelFunciones.setBackground(AppStyle.COLOR_FONDO);
        panelFunciones.revalidate();
        panelFunciones.repaint();
        
        JScrollPane scrollPeliculas = new JScrollPane(panelFunciones);
        scrollPeliculas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPeliculas);
        scrollPeliculas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollPeliculas.setBackground(AppStyle.COLOR_FONDO);
        
        containerFunciones.add(lblFunciones, BorderLayout.NORTH);
        containerFunciones.add(scrollPeliculas, BorderLayout.CENTER);
        
        return containerFunciones;
    }

    private void createReserva(Funcion f){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppStyle.COLOR_FONDO);

        JLabel lblTitulo = new JLabel("Reserva: " + f.getPelicula().getTitulo());
        lblTitulo.setFont(AppStyle.FUENTE_TITULO);
        mainPanel.add(lblTitulo);
        
        JPanel cardMoviePanel = new JPanel();
        cardMoviePanel.setLayout(new BorderLayout());
        cardMoviePanel.setPreferredSize(new Dimension(180, 300));
        cardMoviePanel.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));

        ImageIcon icon = new ImageIcon(f.getPelicula().getRutaImagen());
        Image img = icon.getImage().getScaledInstance(180, 200, Image.SCALE_SMOOTH);
        JLabel lblImagen = new JLabel(new ImageIcon(img));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        
        JLabel duracion = new JLabel(f.getPelicula().getGenero() + " | " + f.getPelicula().getDuracion());
        duracion.setFont(AppStyle.FUENTE_NORMAL);
        duracion.setForeground(AppStyle.FONT_COLOR_PRIMARIO);
        info.add(duracion);
        
        JLabel clasificacion = new JLabel("Clasificación: " + f.getPelicula().getClasificacion());
        clasificacion.setFont(AppStyle.FUENTE_NORMAL);
        clasificacion.setForeground(AppStyle.FONT_COLOR_PRIMARIO);
        info.add(clasificacion);
        
        
        JLabel horario = new JLabel("Horario: " + f.getFecha() + " - " + f.getHora());
        horario.setFont(AppStyle.FUENTE_NORMAL);
        horario.setForeground(AppStyle.FONT_COLOR_PRIMARIO);
        info.add(horario);
        
        info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        info.setBackground(new Color(248, 250, 252));
        
        cardMoviePanel.setBackground(new Color(248, 250, 252));
        cardMoviePanel.add(lblImagen, BorderLayout.WEST);
        cardMoviePanel.add(info, BorderLayout.CENTER);
        cardMoviePanel.add(lblTitulo, BorderLayout.NORTH);
        
        RoomPanel room = new RoomPanel(8, 10);
        room.setOccupiedSeats(Arrays.asList("A1", "A2", "B5", "C3"));
        mainPanel.add(room, BorderLayout.CENTER);
        mainPanel.add(cardMoviePanel, BorderLayout.NORTH);
        
        
        cardPanel.add(mainPanel, "FUNCION_" + f.getIdFuncion());
        
        cardLayout.show(cardPanel, "FUNCION_" + f.getIdFuncion());
    }
}
