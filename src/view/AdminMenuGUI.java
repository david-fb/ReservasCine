package view;

import services.PeliculasService;
import services.FuncionService;
import services.SalaService;
import services.ReservaService;

import javax.swing.*;
import java.awt.*;
import main.VentanaProductos;
import services.AsientoService;
import services.UsuarioService;

public class AdminMenuGUI extends JFrame {

    // Instancias de los servicios necesarios para lanzar las vistas
    private final PeliculasService peliculasService;
    private final FuncionService funcionService;
    private final SalaService salaService;
    private final ReservaService reservaService;
    private final UsuarioService usuarioService; 
    private final AsientoService asientoService;

    public AdminMenuGUI(PeliculasService pService, FuncionService fService, SalaService sService, 
                        ReservaService rService, UsuarioService uService, AsientoService aService) {
        
        this.peliculasService = pService;
        this.funcionService = fService;
        this.salaService = sService;
        this.reservaService = rService;
        this.usuarioService = uService;
        this.asientoService = aService;

        setTitle("💻 Menú Principal - Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        
        // Usamos un GridLayout para organizar los botones en una cuadrícula
        JPanel panel = new JPanel(new GridLayout(4, 1, 15, 15)); // 4 filas, 1 columna, con separación

        panel.add(crearBoton("🎥 Gestión de Películas", e -> runPeliculasGUI()));
        panel.add(crearBoton("🎬 Gestión de Funciones", e -> runFuncionesGUI()));
        panel.add(crearBoton("🛋️ Gestión de Salas", e -> runSalasGUI()));
        panel.add(crearBoton("🎟️ Gestión de Reservas", e -> runReservasGUI()));
        panel.add(crearBoton("🎟️Productos", e -> runProductosGUI()));
        panel.add(crearBoton("🎟️ Cerrar Sesión", e -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
            dispose();
        }));

        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        add(panel);
        setVisible(true);
    }
    
    // Método auxiliar para crear botones con el ActionListener
    private JButton crearBoton(String texto, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.addActionListener(listener);
        return btn;
    }


    private void runPeliculasGUI() {
        new PeliculasGUI(peliculasService); 
    }

    private void runFuncionesGUI() {
        new FuncionesGUI(funcionService, peliculasService, salaService); 
    }

    private void runSalasGUI() {
        new SalasGUI(salaService);
    }

    private void runReservasGUI() {
        new ReservasGUI(reservaService, usuarioService, funcionService, asientoService, peliculasService); 
    }
    
    private void runProductosGUI() {
        VentanaProductos ventanaProductos = new VentanaProductos();
        ventanaProductos.setVisible(true);
    }
}