/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WINDOWS
 */
public class Funcion {
    private int idFuncion;
    private String fecha;
    private String hora;
    //private Sala sala;
    private Pelicula pelicula;
    private double precioEntrada;

    public Funcion(int idFuncion, String fecha, String hora,  Pelicula pelicula, double precioEntrada) {
        this.idFuncion = idFuncion;
        this.fecha = fecha;
        this.hora = hora;
        //this.sala = sala;
        this.pelicula = pelicula;
        this.precioEntrada = precioEntrada;
    }

    public void mostrarFuncion() {
        System.out.println("Función #" + idFuncion + " | Película: " + pelicula.getTitulo() +
                " | Sala: " //+sala.getNombre() + 
                +" | " + fecha + " " + hora +
                " | Precio: $" + precioEntrada);
    }

    // Getters
    public Pelicula getPelicula() { return pelicula; }
    //public Sala getSala() { return sala; }
    public double getPrecioEntrada() { return precioEntrada; }
}


