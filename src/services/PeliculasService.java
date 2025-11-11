/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.ArrayList;
import model.Pelicula;
import utils.EditorArchivo;

/**
 *
 * @author WINDOWS
 */
public class PeliculasService {
    private final EditorArchivo editor = new EditorArchivo();
    private final String FILENAME = "peliculas.txt";
    private final String SEPARADOR = ";";
    
    public ArrayList<Pelicula> listarpeliculas() {
        ArrayList<String> lineas = editor.getAll(FILENAME, SEPARADOR);
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        
        for(int i = 0; i < lineas.size(); i++){
            String[] arrLinea = lineas.get(i).split(this.SEPARADOR);
            
            peliculas.add(new Pelicula(Integer.parseInt(arrLinea[0]), arrLinea[1], arrLinea[2], arrLinea[3],arrLinea[4]));
        }
        
        return peliculas;
    }
    
    public void createPelicula(String titulo, String genero, String duracion, String clasificacion ) {
        editor.crearArchivo(FILENAME);
        int id = editor.getUltimoId(FILENAME, this.SEPARADOR) + 1;
        String registro = String.format("%d;%s;%s;%s;%s", id, titulo, genero, duracion, clasificacion);
        editor.addLinea(this.FILENAME, registro);
    }
    
    public Pelicula getPeliculaById(int user_id){
        
        String id = String.valueOf(user_id);
        String[] pLineas = editor.getRegistro(FILENAME, 0, id, SEPARADOR).split(SEPARADOR);
        
        return new Pelicula(Integer.parseInt(pLineas[0]), pLineas[1], pLineas[2], pLineas[3],pLineas[4]);
    }
    
    public Pelicula updatePelicula(int idPelicula, String titulo, String genero, String duracion, String clasificacion){
        
        Pelicula pelicula = getPeliculaById(idPelicula);
        
        if(!titulo.isEmpty()){
            pelicula.setTitulo(titulo);
        }
        
        if(!genero.isEmpty()){
            pelicula.setGenero(genero);
        }
        if(!duracion.isEmpty()){
            pelicula.setDuracion(duracion);
        }
        if(!clasificacion.isEmpty()){
            pelicula.setClasificacion(clasificacion);
        }
        
        String pelicula_updated = String.format("%s;%s;%s;%s;%s", pelicula.getIdPelicula(), pelicula.getTitulo(), pelicula.getGenero(), pelicula.getDuracion(), pelicula.getClasificacion());
        
        editor.updateRegistro(FILENAME, String.valueOf(idPelicula), SEPARADOR, pelicula_updated);
        
        return pelicula;
    }
    
    public void deletePelicula(int IdPelicula){
        editor.eliminarLineaPorId(FILENAME, SEPARADOR, IdPelicula);
    }
}

