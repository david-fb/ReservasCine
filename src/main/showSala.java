/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import javax.swing.SwingUtilities;
import services.SalaService;
import utils.AppInitializer;
import view.SalasGUI;

/**
 *
 * @author Jimmi Calvo
 */
public class showSala {
    public static void main(String[] args) {
        AppInitializer.initialize();
        SalaService salaService = new SalaService();

        
        SwingUtilities.invokeLater(() -> {
            new SalasGUI(salaService);
        });
}
}
