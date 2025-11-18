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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import static javax.swing.SwingConstants.CENTER;
import model.Funcion;
import model.Usuario;
import services.FuncionService;
import utils.SessionManager;
import utils.components.AppStyle;
import utils.components.PrimaryButton;
import utils.components.RoomPanel;
import utils.components.Seat;
import utils.components.peliculaCard;
import utils.components.SecondaryButton;
import java.util.List;
import model.Pelicula;
import model.Reserva;
import model.Sala;
import services.ReservaService;

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
    private final static String RESERVAS_VIEW = "ReservasCard";

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
        JPanel containerReservas = createReservasPanel();
        cardPanel.add(mainPanel, WELCOME_VIEW);
        cardPanel.add(containerFunciones, FUNCIONES_VIEW);
        cardPanel.add(containerReservas, RESERVAS_VIEW);
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

        btnReservas.addActionListener(e -> {
            cardLayout.show(cardPanel, RESERVAS_VIEW);
        });
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

    private JPanel createWelcomePanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(AppStyle.COLOR_FONDO);

        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de Cine");
        lblBienvenida.setFont(AppStyle.FUENTE_TITULO);
        mainPanel.add(lblBienvenida);

        return mainPanel;
    }

    private JPanel createFuncionesPanel() {
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

    private void createReserva(Funcion f) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppStyle.COLOR_FONDO);
        ReservaService reservaService = new ReservaService();

        JLabel lblTitulo = new JLabel("Reserva: " + f.getPelicula().getTitulo());
        lblTitulo.setFont(AppStyle.FUENTE_TITULO);
        lblTitulo.setForeground(AppStyle.COLOR_PRIMARIO);
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
        duracion.setFont(AppStyle.FUENTE_TITULO);
        duracion.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
        info.add(duracion);

        JLabel clasificacion = new JLabel("Clasificación: " + f.getPelicula().getClasificacion());
        clasificacion.setFont(AppStyle.FUENTE_TITULO);
        clasificacion.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
        info.add(clasificacion);

        JLabel horario = new JLabel("Horario: " + f.getFecha() + " - " + f.getHora());
        horario.setFont(AppStyle.FUENTE_TITULO);
        horario.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
        info.add(horario);

        info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        info.setBackground(new Color(248, 250, 252));

        cardMoviePanel.setBackground(new Color(248, 250, 252));
        cardMoviePanel.add(lblImagen, BorderLayout.WEST);
        cardMoviePanel.add(info, BorderLayout.CENTER);
        cardMoviePanel.add(lblTitulo, BorderLayout.NORTH);

        RoomPanel room = new RoomPanel(f.getSala().getFilas(), f.getSala().getColumnas());
        String[] occupiedSeats = funcionService.getAsientosOcupados(f);
        room.setOccupiedSeats(Arrays.asList(occupiedSeats));

        PrimaryButton continuaBtn = new PrimaryButton("Continuar");

        mainPanel.add(room, BorderLayout.CENTER);
        mainPanel.add(cardMoviePanel, BorderLayout.NORTH);
        mainPanel.add(continuaBtn, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cardPanel.add(mainPanel, "FUNCION_" + f.getIdFuncion());

        cardLayout.show(cardPanel, "FUNCION_" + f.getIdFuncion());

        continuaBtn.addActionListener(e -> {

            List<Seat> selectedAsientos = room.getSelectedSeats();

            if (selectedAsientos.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "ERROR: Debe seleccionar al menos una silla!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Funcion funcion = f;
            Pelicula pelicula = funcion.getPelicula();
            Sala sala = funcion.getSala();

            StringBuilder sb = new StringBuilder();
            for (Seat s : selectedAsientos) {
                sb.append(s.getSeatId()).append(", ");
            }

            String asientosString = sb.substring(0, sb.length() - 2);

            // Precio total
            double precioUnitario = funcion.getPrecioEntrada();
            double precioTotal = precioUnitario * selectedAsientos.size();

            // Texto que va en el diálogo
            String mensaje
                    = "CONFIRMAR RESERVA\n\n"
                    + "Película: " + pelicula.getTitulo() + "\n"
                    + "Función: " + funcion.getFecha() + "  " + funcion.getHora() + "\n"
                    + "Sala: " + sala.getNombre() + "\n"
                    + "Asientos: " + asientosString + "\n"
                    + "Precio Unitario: $" + precioUnitario + "\n"
                    + "Total a pagar: $" + precioTotal + "\n\n"
                    + "¿Desea confirmar la reserva?";

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    mensaje,
                    "Confirmación de reserva",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                String hoy = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                for (Seat seat : selectedAsientos) {
                    reservaService.createReserva(
                            usuario.getUser_id(),
                            funcion.getIdFuncion(),
                            seat.getSeatId(),
                            "ACTIVO",
                            hoy
                    );
                }

                JOptionPane.showMessageDialog(this, "¡Reserva confirmada!");
                cardLayout.show(cardPanel, FUNCIONES_VIEW);
            }

        });

    }

    private JPanel createReservasPanel() {

        ReservaService reservaService = new ReservaService();

        JPanel containerReservas = new JPanel();
        containerReservas.setLayout(new BorderLayout());
        containerReservas.setBackground(AppStyle.COLOR_FONDO);

        JLabel lblReservas = new JLabel("Reservas realizadas!");
        lblReservas.setFont(AppStyle.FUENTE_TITULO);
        lblReservas.setForeground(AppStyle.FONT_COLOR_PRIMARIO);
        lblReservas.setHorizontalAlignment(CENTER);
        lblReservas.setBackground(AppStyle.COLOR_FONDO);
        lblReservas.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        JPanel panelReservas = new JPanel();
        panelReservas.setLayout(new GridLayout(0, 3, 20, 20));

        ArrayList<Reserva> reservas = reservaService.getReservasByUser(usuario.getUser_id());
        for (Reserva r : reservas) {
            JPanel infoReserva = new JPanel();
            infoReserva.setLayout(new BoxLayout(infoReserva, BoxLayout.Y_AXIS));

            Funcion f = funcionService.getFuncionById(r.getFk_Funcion());

            JLabel titulo = new JLabel(f.getPelicula().getTitulo());
            titulo.setFont(AppStyle.FUENTE_NORMAL);
            titulo.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
            infoReserva.add(titulo);

            JLabel duracion = new JLabel(f.getPelicula().getGenero() + " | " + f.getPelicula().getDuracion());
            duracion.setFont(AppStyle.FUENTE_NORMAL);
            duracion.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
            infoReserva.add(duracion);

            JLabel clasificacion = new JLabel("Clasificación: " + f.getPelicula().getClasificacion());
            clasificacion.setFont(AppStyle.FUENTE_NORMAL);
            clasificacion.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
            infoReserva.add(clasificacion);

            JLabel horario = new JLabel("Horario: " + f.getFecha() + " - " + f.getHora());
            horario.setFont(AppStyle.FUENTE_NORMAL);
            horario.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
            infoReserva.add(horario);

            JLabel asiento = new JLabel("Asiento: " + r.getFk_Asiento());
            asiento.setFont(AppStyle.FUENTE_NORMAL);
            asiento.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
            infoReserva.add(asiento);

            panelReservas.add(infoReserva);
        }

        panelReservas.setBackground(AppStyle.COLOR_FONDO);
        panelReservas.revalidate();
        panelReservas.repaint();

        JScrollPane scrollPeliculas = new JScrollPane(panelReservas);
        scrollPeliculas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPeliculas);
        scrollPeliculas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollPeliculas.setBackground(AppStyle.COLOR_FONDO);

        PrimaryButton actualizarBtn = new PrimaryButton("Actualizar");
        actualizarBtn.addActionListener(e -> {
            panelReservas.removeAll();

            ArrayList<Reserva> nuevasReservas = reservaService.getReservasByUser(usuario.getUser_id());

            for (Reserva r : nuevasReservas) {
                JPanel infoReserva = new JPanel();
                infoReserva.setLayout(new BoxLayout(infoReserva, BoxLayout.Y_AXIS));

                Funcion f = funcionService.getFuncionById(r.getFk_Funcion());

                JLabel titulo = new JLabel(f.getPelicula().getTitulo());
                titulo.setFont(AppStyle.FUENTE_NORMAL);
                titulo.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
                infoReserva.add(titulo);

                JLabel duracion = new JLabel(f.getPelicula().getGenero() + " | " + f.getPelicula().getDuracion());
                duracion.setFont(AppStyle.FUENTE_NORMAL);
                duracion.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
                infoReserva.add(duracion);

                JLabel clasificacion = new JLabel("Clasificación: " + f.getPelicula().getClasificacion());
                clasificacion.setFont(AppStyle.FUENTE_NORMAL);
                clasificacion.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
                infoReserva.add(clasificacion);

                JLabel horario = new JLabel("Horario: " + f.getFecha() + " - " + f.getHora());
                horario.setFont(AppStyle.FUENTE_NORMAL);
                horario.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
                infoReserva.add(horario);

                JLabel asiento = new JLabel("Asiento: " + r.getFk_Asiento());
                asiento.setFont(AppStyle.FUENTE_NORMAL);
                asiento.setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
                infoReserva.add(asiento);

                panelReservas.add(infoReserva);
            }

            panelReservas.revalidate();
            panelReservas.repaint();
        });

        containerReservas.add(lblReservas, BorderLayout.NORTH);
        containerReservas.add(scrollPeliculas, BorderLayout.CENTER);
        containerReservas.add(actualizarBtn, BorderLayout.SOUTH);
        containerReservas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return containerReservas;
    }
}
