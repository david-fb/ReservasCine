package main;

import services.*;
import utils.AppInitializer;
import view.FuncionesGUI; // Importar la nueva clase GUI
import javax.swing.SwingUtilities; // Importar para manejar el hilo de Swing
import services.SalaService;

public class showFunciones {

    public static void main(String[] args) {
        AppInitializer.initialize();
        FuncionService funcionesService = new FuncionService();
        PeliculasService peliculasService = new PeliculasService();
        SalaService salaService = new SalaService();

        
        SwingUtilities.invokeLater(() -> {
            new FuncionesGUI(funcionesService, peliculasService, salaService);
        });
 
    }
}