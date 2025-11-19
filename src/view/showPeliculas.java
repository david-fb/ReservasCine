/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.SwingUtilities;
import services.*;
import utils.AppInitializer;
import view.AdminMenuGUI;
import view.PeliculasGUI;

/**
 *
 * @author WINDOWS
 */
public class showPeliculas {

    public static void main(String[] args) {

        SalaService salaService = new SalaService();
        PeliculasService  peliculasService = new PeliculasService();
        FuncionService funcionService = new FuncionService();  
        ReservaService reservaService = new ReservaService();
        UsuarioService usuarioService = new UsuarioService();
        AsientoService asientoService = new AsientoService();
       

// Inicializar el Menú (pasando todos los servicios que necesita)
        AdminMenuGUI menu = new AdminMenuGUI(peliculasService, funcionService, salaService, reservaService,usuarioService, asientoService);
    }
}
