/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.io.*;
import java.util.ArrayList;
import model.Bebida;
import model.*;
import utils.EditorArchivo;

/**
 *
 * @author Sofia
 */
public class ProductoService {

    private ArrayList<Producto> productos;
    private final EditorArchivo editor = new EditorArchivo();
    private final String FILENAME = "productos.txt";
    private final String SEPARADOR = ";";

    public ProductoService() {
        productos = new ArrayList<>();
        cargarArchivo();
    }

    // REGISTRO DE LOS PRODUCTOS
    public void registrarComida(String nombre, double precio, int stock, String categoria) {
        Producto c = new Comida(nombre, precio, stock, categoria);
        productos.add(c);
        guardarEnArchivo();
    }

    public void registrarBebida(String nombre, double precio, int stock) {
        Producto b = new Bebida(nombre, precio, stock);
        productos.add(b);
        guardarEnArchivo();
    }

    public void registrarCombo(String nombre, double precio, int stock) {
        Producto cb = new Combo(nombre, precio, stock);
        productos.add(cb);
        guardarEnArchivo();
    }

    // ELIMINAR
    public boolean eliminarProducto(int id) {
        for (Producto p : productos) {
            if (p.getIdProducto() == id) {
                productos.remove(p);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    // COMPRAR
    public boolean comprarProducto(int id, int cantidad) {
        for (Producto p : productos) {
            if (p.getIdProducto() == id) {

                if (p.getStock() < cantidad) {
                    return false; // no hay suficiente stock
                }

                p.setStock(p.getStock() - cantidad);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
    
    // MOSTRAR
    public void mostrarProductos() {
        for (Producto p : productos) {
            System.out.println(
                    p.getIdProducto() + " | " +
                    p.getTipo() + " | " +
                    p.getNombre() + " | $" +
                    p.getPrecio() + " | Stock: " +
                    p.getStock()
            );
        }
    }

    // REGISTRAR
     private void guardarEnArchivo() {
        editor.crearArchivo(FILENAME); // asegura que exista la carpeta y el archivo

        // primero limpiamos el archivo
        ArrayList<String> lineas = new ArrayList<>();

        for (Producto p : productos) {
            String linea;
            if (p instanceof Comida) {
                Comida c = (Comida) p;
                linea = p.getTipo() + SEPARADOR + p.getIdProducto() + SEPARADOR + p.getNombre() + SEPARADOR
                        + p.getPrecio() + SEPARADOR + p.getStock() + SEPARADOR + c.getCategoria();
            } else {
                linea = p.getTipo() + SEPARADOR + p.getIdProducto() + SEPARADOR + p.getNombre() + SEPARADOR
                        + p.getPrecio() + SEPARADOR + p.getStock();
            }
            lineas.add(linea);
        }

        editor.guardarLineas(lineas, new java.io.File("bd/" + FILENAME));
    }

    private void cargarArchivo() {
          ArrayList<String> lineas = editor.getAll(FILENAME, SEPARADOR);
          for (String linea : lineas) {
              String[] datos = linea.split(SEPARADOR);

              if (datos.length < 5) continue;

              String tipo = datos[0];
              int id = Integer.parseInt(datos[1]);
              String nombre = datos[2];
              double precio = Double.parseDouble(datos[3]);
              int stock = Integer.parseInt(datos[4]);

              Producto p = null;

              switch (tipo) {
                  case "COMIDA":
                      if (datos.length < 6) continue;
                      String categoria = datos[5];
                      p = new Comida(nombre, precio, stock, categoria);
                      break;
                  case "BEBIDA":
                      p = new Bebida(nombre, precio, stock);
                      break;
                  case "COMBO":
                      p = new Combo(nombre, precio, stock);
                      break;
              }

              if (p != null) {
                  p.idProducto = id;
                  productos.add(p);
              }
          }
      }
    
    
}