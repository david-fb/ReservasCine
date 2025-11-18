/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.ArrayList;
import model.Reserva;
import model.Sala;
import utils.EditorArchivo;

/**
 *
 * @author Sofia
 */
public class SalaService {

    private final EditorArchivo editor = new EditorArchivo();
    private final String FILENAME = "sala.txt";
    private final String SEPARADOR = ";";

    
    public ArrayList<Sala> listarSalas() {
        ArrayList<String> lineas = editor.getAll(FILENAME, SEPARADOR);
        ArrayList<Sala> salas = new ArrayList<>();

        for (String linea : lineas) {

            if (linea.trim().isEmpty()) continue; 

            String[] arr = linea.split(SEPARADOR);

            if (arr.length < 4) continue; 

            try {
                int id = Integer.parseInt(arr[0]);
                String nombre = arr[1];
                int capacidad = Integer.parseInt(arr[2]);
                String tipo = arr[3];

                salas.add(new Sala(id, nombre, capacidad, tipo));

            } catch (Exception e) {
                System.out.println("Error al cargar sala: " + linea);
            }
        }

        return salas;
    }

    
    public Sala getSalaById(int idSala) {
        try {
            String registro = editor.getRegistro(FILENAME, 0, String.valueOf(idSala), SEPARADOR);
            String[] arr = registro.split(SEPARADOR);

            return new Sala(
                    Integer.parseInt(arr[0]),
                    arr[1],
                    Integer.parseInt(arr[2]),
                    arr[3]
            );

        } catch (Exception e) {
            System.out.println("No se encontró la sala con ID: " + idSala);
            return null;
        }
    }

    
    public void registrarSala(String nombre, int capacidad, String tipo) {

        editor.crearArchivo(FILENAME);

        nombre = nombre.trim();
        tipo = tipo.trim();

        int id = editor.getUltimoId(FILENAME, SEPARADOR) + 1;

        String registro = String.format(
                "%d;%s;%d;%s",
                id, nombre, capacidad, tipo
        );

        editor.addLinea(FILENAME, registro);
    }

   
    public Sala actualizarSala(int idSala, String nombre, int capacidad, String tipo) {

        Sala sala = getSalaById(idSala);

        if (sala == null) {
            System.out.println("Error: la sala no existe.");
            return null;
        }

        if (!nombre.trim().isEmpty()) {
            sala.setNombre(nombre.trim());
        }

        if (capacidad > 0) {
            sala.setCapacidad(capacidad);
        }

        if (!tipo.trim().isEmpty()) {
            sala.setTipo(tipo.trim());
        }

        String registroActualizado = String.format(
                "%d;%s;%d;%s",
                sala.getIdSala(),
                sala.getNombre(),
                sala.getCapacidad(),
                sala.getTipo()
        );

        editor.updateRegistro(FILENAME, String.valueOf(idSala), SEPARADOR, registroActualizado);
        return sala;
    }

    
    public void deleteSala(int idSala) {
        editor.eliminarLineaPorId(FILENAME, SEPARADOR, idSala);
    }
}