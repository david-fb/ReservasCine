package main;

import services.*;
import utils.AppInitializer;
import view.FuncionesGUI; // Importar la nueva clase GUI
import view.ReservasGUI; // Importar la nueva clase GUI
import javax.swing.SwingUtilities; // Importar para manejar el hilo de Swing
import services.ReservaService;
import services.SalaService;

public class createFunciones {

    public static void main(String[] args) {
        AppInitializer.initialize();
        UsuarioService usuarioService = new UsuarioService();
        FuncionService funcionesService = new FuncionService();
        AsientoService asientoService = new AsientoService();
        PeliculasService peliculasService = new PeliculasService();
        ReservaService reservaService = new ReservaService();
        SalaService salaService = new SalaService();

        
        SwingUtilities.invokeLater(() -> {
            new FuncionesGUI(funcionesService, peliculasService, salaService);
        });
 
    }
}