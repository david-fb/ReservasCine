/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Sofia
 */
public class Comida extends Producto{
    
    private String categoria;

    
    public Comida(String nombre, double precio, int stock, String categoria) {
        super(nombre, precio, stock);
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String getTipo() {
        return "COMIDA";
    }

    
}
