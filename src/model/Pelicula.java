/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Jimmi Calvo
 */

public class Pelicula {
    private int idPelicula;
    private String titulo;
    private String genero;
    private String duracion;
    private String clasificacion;
    private String rutaImagen;

    public Pelicula(int idPelicula, String titulo, String genero, String duracion, String clasificacion) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
    }

    public Pelicula(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    public void setDuracion(String duracion) {
        this.clasificacion = duracion;
    }

    public void mostrarPelicula() {
        System.out.println("🎞️ " + titulo + " (" + genero + ", " + duracion + " min, " + clasificacion + ")");
    }
    
    @Override
     public String toString() {
         return String.format("%s,%s,%s,%s", idPelicula, titulo, genero, duracion, clasificacion);
      }

    public String getGenero() {
        return genero;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public String getTitulo() { return titulo; }

    public String getRutaImagen() {
       return "src/assets/peliculas/bastardos-sin-gloria-poster.jpg";
    }
}
