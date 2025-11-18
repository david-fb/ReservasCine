package main;

import services.*;
import utils.AppInitializer;
import view.FuncionesGUI; // Importar la nueva clase GUI
import javax.swing.SwingUtilities; // Importar para manejar el hilo de Swing
import services.SalaService;
import view.ReservasGUI;

public class showReservas {

    public static void main(String[] args) {
        AppInitializer.initialize();
        ReservaService reservaService = new ReservaService();
        UsuarioService usuarioService = new UsuarioService();
        FuncionService funcionService = new FuncionService();
        AsientoService asientoService = new AsientoService();
        PeliculasService peliculasService = new PeliculasService();

        
        SwingUtilities.invokeLater(() -> {
            new ReservasGUI(reservaService,usuarioService,funcionService,asientoService,peliculasService);
        });
 
    }
}