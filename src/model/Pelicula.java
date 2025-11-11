/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WINDOWS
 */

public class Pelicula {
    private String idPelicula;
    private String titulo;
    private String genero;
    private String duracion;
    private String clasificacion;

    public Pelicula(String idPelicula, String titulo, String genero, String duracion, String clasificacion) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
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

    public String getTitulo() { return titulo; }
}
