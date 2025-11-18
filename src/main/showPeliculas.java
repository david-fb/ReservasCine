/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import javax.swing.SwingUtilities;
import services.PeliculasService;
import utils.AppInitializer;
import view.PeliculasGUI;

/**
 *
 * @author WINDOWS
 */
public class showPeliculas {
    public static void main(String[] args) {
        AppInitializer.initialize();
        PeliculasService peliculasService = new PeliculasService();

        
        SwingUtilities.invokeLater(() -> {
            new PeliculasGUI(peliculasService);
        });
}}
