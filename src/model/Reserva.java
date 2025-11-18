/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WINDOWS
 */
public class Reserva {
    private int idReserva;
    private int fk_Usuario;
    private int fk_Funcion;
    private int fk_Asiento;
    private String estado;
    private String fecha_Reserva;

    public Reserva(int idReserva, int fk_Usuario, int fk_Funcion, int fk_Asiento, String estado, String fecha_Reserva) {
        this.idReserva = idReserva;
        this.fk_Usuario = fk_Usuario;
        this.fk_Funcion = fk_Funcion;
        this.fk_Asiento = fk_Asiento;
        this.estado = estado;
        this.fecha_Reserva = fecha_Reserva;
    }

   
    
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getFk_Usuario() {
        return fk_Usuario;
    }

    public void setFk_Usuario(int fk_Usuario) {
        this.fk_Usuario = fk_Usuario;
    }

    public int getFk_Funcion() {
        return fk_Funcion;
    }

    public void setFk_Funcion(int fk_Funcion) {
        this.fk_Funcion = fk_Funcion;
    }

    public int getFk_Asiento() {
        return fk_Asiento;
    }

    public void setFk_Asiento(int fk_Asiento) {
        this.fk_Asiento = fk_Asiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_Reserva() {
        return fecha_Reserva;
    }

    public void setFecha_Reserva(String fecha_Reserva) {
        this.fecha_Reserva = fecha_Reserva;
    }
     @Override
    public String toString() {
        return "reserva{" + "idReserva=" + idReserva + ", fk_Usuario=" + fk_Usuario + ", fk_Funcion=" + fk_Funcion + ", fk_Asiento=" + fk_Asiento + ", estado=" + estado + ", fecha_Reserva=" + fecha_Reserva + '}';
    }

}
