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
        ArrayList<Sala> sala = new ArrayList<>();

        for (int i = 0; i < lineas.size(); i++) {
            String[] arrLinea = lineas.get(i).split(this.SEPARADOR);
            System.out.println(arrLinea[1]);
            sala.add(new Sala(Integer.parseInt(arrLinea[0]), arrLinea[1], Integer.parseInt(arrLinea[2]), arrLinea[3]));
        }

        return sala;
    }

    public Sala getSalaById(int reserva_id) {

        String id = String.valueOf(reserva_id);
        String[] fLineas = editor.getRegistro(FILENAME, 0, id, SEPARADOR).split(SEPARADOR);

        return new Sala(Integer.parseInt(fLineas[0]), fLineas[1], Integer.parseInt(fLineas[2]), fLineas[3]);
    }

    public void registrarSala(String nombre, int capacidad, String tipo) {

        editor.crearArchivo(FILENAME);
        int id = editor.getUltimoId(FILENAME, this.SEPARADOR) + 1;
        String registro = String.format("%d;%s;%s;%s", id, nombre, capacidad, tipo);

        editor.addLinea(this.FILENAME, registro);
    }

    public Sala getSala(int idSala) {

        String id = String.valueOf(idSala);
        String[] salaLineas = editor.getRegistro(FILENAME, 0, id, SEPARADOR).split(SEPARADOR);

        return new Sala(Integer.parseInt(salaLineas[0]), salaLineas[1], Integer.parseInt(salaLineas[2]), salaLineas[3]);
    }

    public Sala actualizarSala(int idSala, String nombre, int capacidad, String tipo) {

        Sala sala = getSala(idSala);

        if (!nombre.isEmpty()) {
            sala.setNombre(nombre);
        }

        if (capacidad > 0) {
            sala.setCapacidad(capacidad);
        }

        if (!tipo.isEmpty()) {
            sala.setTipo(tipo);
        }

        String sala_updated = String.format("%d;%s;%s;%s", sala.getIdSala(), sala.getNombre(), sala.getCapacidad(), sala.getTipo());
        editor.updateRegistro(FILENAME, String.valueOf(idSala), SEPARADOR, sala_updated);
        return sala;
    }
    public void deleteSala(int id_sala){
        editor.eliminarLineaPorId(FILENAME, SEPARADOR, id_sala);
    }

}
