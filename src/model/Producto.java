/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Sofia
 */
public class Producto {
    
    private int idProducto;
    private String nombre;
    private double precio;
    private String tacks;
    private int stock;
    
    
    public Producto(){
    
    }
    
    public Producto(int idProducto, String nombre, double precio, String tacks, int stock){
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.tacks = tacks;
        this.stock = stock;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTacks() {
        return tacks;
    }

    public void setTacks(String tacks) {
        this.tacks = tacks;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void adicionarProducto(){
    
    }
    
    public void eliminarProducto(){
    
    }
    
    public void comprarProducto(){
    
    }
    
}
