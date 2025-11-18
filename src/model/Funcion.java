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
    private int fk_sala;
    private int fk_pelicula;
    private double precioEntrada;
    private Pelicula pelicula;

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
    private Sala sala;

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Funcion(int idFuncion, String fecha, String hora,  int sala, int pelicula, double precioEntrada) {
        this.idFuncion = idFuncion;
        this.fecha = fecha;
        this.hora = hora;
        this.fk_sala = sala;
        this.fk_pelicula = pelicula;
        this.precioEntrada = precioEntrada;
    }

    public int getFk_sala() {
        return fk_sala;
    }

    public void setFk_sala(int fk_sala) {
        this.fk_sala = fk_sala;
    }

    public void mostrarFuncion() {
        System.out.println("Función #" + idFuncion + " | Película: " + fk_pelicula+
                " | Sala: " //+sala.getNombre() + 
                +" | " + fecha + " " + hora +
                " | Precio: $" + precioEntrada);
    }
    @Override
    public String toString() {
    return String.format("%-4d | %-12s | %-5s | Sala %-3d | Película ID: %-3d | $%.2f",
            idFuncion, fecha, hora, fk_sala, fk_pelicula, precioEntrada);
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

    public int getFk_pelicula() {
        return fk_pelicula;
    }

    public void setFk_pelicula(int fk_pelicula) {
        this.fk_pelicula = fk_pelicula;
    }

    public void setPrecioEntrada(double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    // Getters
  
    //public Sala getSala() { return sala; }
    public double getPrecioEntrada() { return precioEntrada; }
}



