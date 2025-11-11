/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Jimmi Calvo
 */
public class Funcion {
    private int idFuncion;
    private String fecha;
    private String hora;
    //private Sala sala;
    private int fk_pelicula;
    private double precioEntrada;

    public Funcion(int idFuncion, String fecha, String hora,  int pelicula, double precioEntrada) {
        this.idFuncion = idFuncion;
        this.fecha = fecha;
        this.hora = hora;
        //this.sala = sala;
        this.fk_pelicula = pelicula;
        this.precioEntrada = precioEntrada;
    }

    public void mostrarFuncion() {
        System.out.println("Función #" + idFuncion + " | Película: " + fk_pelicula+
                " | Sala: " //+sala.getNombre() + 
                +" | " + fecha + " " + hora +
                " | Precio: $" + precioEntrada);
    }

    public int getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    // Getters
   // public Pelicula getPelicula() { return fk_pelicula; }
    //public Sala getSala() { return sala; }
    public double getPrecioEntrada() { return precioEntrada; }
}


