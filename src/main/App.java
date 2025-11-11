package main;

import services.UsuarioService;
import services.PeliculasService;
import utils.AppInitializer;

/**
 *
 * @author david-fb - David Basto
 */
public class App {
    public static void main(String[] args) {
        
        AppInitializer.initialize();
        UsuarioService usuarioService = new UsuarioService();
        PeliculasService peliculasService = new PeliculasService();
        
        usuarioService.createUser("dbasto", "David Basto", "12345");
        
        usuarioService.createUser("jcalvo", "Jimmi Calvo", "12345");
        
        usuarioService.createUser("solave", "Sophia Olave", "12345");
        
        peliculasService.createPelicula("Interstellar", "Cfi", "138 m", "+16");
        peliculasService.createPelicula("Bastardor sin gloria", "Drama", "168 m", "+18");
        peliculasService.createPelicula("Mano de Dios", "Deporte", "98 m", "+13");
        
        System.out.println(usuarioService.listarUsuarios().toString());
        System.out.println(peliculasService.listarpeliculas().toString());
        
        
        usuarioService.deleteUser(3);
        
        
        System.out.println(usuarioService.listarUsuarios().toString());
        
        System.out.println(usuarioService.getUserById(2).toString());
        System.out.println(peliculasService.getPeliculaById(2).toString());
        
        usuarioService.updateUser(1, "David Martinez", "");
        System.out.println(usuarioService.getUserById(1).toString());
        
        peliculasService.updatePelicula(1, "Bastardos sin gloria", "Historia", "190m", "+16");
        System.out.println(peliculasService.getPeliculaById(1).toString());
    }
}