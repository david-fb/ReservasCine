/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.Asiento;
import utils.EditorArchivo;

/**
 *
 * @author Sofia
 */
public class AsientoService {
    
    private final EditorArchivo editor = new EditorArchivo();
    private final String FILENAME = "Asiento.txt";
    private final String SEPARADOR = ";";
    
    
    // SOLO ADMIN
    public void CrearAsiento(String fila, int numero, String estado, String sala) {
        editor.crearArchivo(FILENAME);
        int id = editor.getUltimoId(FILENAME, this.SEPARADOR) + 1;
        String registro = String.format("%d;%s;%d;%s;%s", id, fila, numero, estado, sala);

        editor.addLinea(this.FILENAME, registro);
    }
    
    public Asiento getAsiento(int idAsiento){
        
        String id = String.valueOf(idAsiento);
        String[] AsientoLineas = editor.getRegistro(FILENAME, 0, id, SEPARADOR).split(SEPARADOR);
        
        return new Asiento(Integer.parseInt(AsientoLineas[0]), AsientoLineas[1], Integer.parseInt(AsientoLineas[2]), AsientoLineas[3], AsientoLineas[4]);
    }
    
    
    public Asiento reservarAsiento(int idAsiento, String fila, int numero, String estado, String sala){
        
        Asiento asiento = getAsiento(idAsiento);
        
        if(asiento.getEstado().equalsIgnoreCase("Ocupado")){
            System.out.println("El asiento ya esta reservado");
            return asiento;
        }
        
        //SE DESCOMENTA CUANDO SE VALIDE USUARIO ADMINISTRADOR, PORQUE ES EL UNICO QUE PUEDE MODIFICAR FILAS, SALAS, ASIENTOS Y ESTADOS
        /*if(!fila.isEmpty()){
            asiento.setFila(fila);
        }
        if(numero>0){
            asiento.setNumero(numero);
        }
        if(!sala.isEmpty()){
            asiento.setSala(sala);
        }*/
        
        if(!estado.isEmpty()){
            asiento.setEstado(estado);
        }
        
        String asiento_updated = String.format("%d;%s;%d;%s;%s", asiento.getIdAsiento(), asiento.getFila(), asiento.getNumero(), asiento.getEstado(), asiento.getSala());
        
        editor.updateRegistro(FILENAME, String.valueOf(idAsiento), SEPARADOR, asiento_updated);
        
        return asiento;
    }
   
    
    
    public void liberarAsiento(int idAsiento){

        Asiento asiento = getAsiento(idAsiento);
        
        if(asiento != null){
            if (asiento.getEstado().equalsIgnoreCase("Ocupado")) {
                
                asiento.setEstado("Disponible");
                
                String asiento_liberar = String.format("%d;%s;%d;%s;%s", asiento.getIdAsiento(), asiento.getFila(), asiento.getNumero(), asiento.getEstado(), asiento.getSala());
                editor.updateRegistro(FILENAME, String.valueOf(idAsiento), SEPARADOR, asiento_liberar);
                
                System.out.println("Asiento liberado exitosamente!! # "+asiento.getIdAsiento());
            
            }else{
                System.out.println("El asiento ya esta liberado!! ");
            }
        }else{
            System.out.println("El asiento no existe!!");
        }
    }
    
    
    public List<Asiento> getTodosLosAsientos() {
       
        List<Asiento> listaAsientos = new ArrayList<>();
        List<String> lineas = editor.getAll(FILENAME, SEPARADOR);

        for (String linea : lineas) {
            if (linea.trim().isEmpty()) continue;

            String[] datos = linea.split(SEPARADOR);
            if (datos.length < 5) continue;

            Asiento asiento = new Asiento(Integer.parseInt(datos[0]), datos[1], Integer.parseInt(datos[2]), datos[3], datos[4]);
            listaAsientos.add(asiento);

        }

        return listaAsientos;
    }
  
}