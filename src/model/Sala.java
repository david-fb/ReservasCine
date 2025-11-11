/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Sofia
 */
public class Sala {
    
    private int idSala;
    private String nombre;
    private int capacidad;
    private String tipo;
    
    
    public Sala(){
    }
    
    public Sala(int idSala, String nombre, int capacidad, String tipo){
    this.idSala = idSala;
    this.nombre = nombre;
    this.capacidad = capacidad;
    this.tipo = tipo;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    //Registrar los datos
    public void registrarSala(){
    
    }
    
    //Modificar los datos
    public void actualizarSala(){
    
    }
    
    
}
