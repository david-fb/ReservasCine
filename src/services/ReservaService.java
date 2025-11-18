package services;

import java.util.ArrayList;
import model.Reserva;
import utils.EditorArchivo;

/**
 *
 * @author Jimmi Calvo
 */
public class ReservaService {
    private final EditorArchivo editor = new EditorArchivo();
    private final String FILENAME = "reserva.txt";
    private final String SEPARADOR = ";";
    
    public ArrayList<Reserva> listarReservas() {
        ArrayList<String> lineas = editor.getAll(FILENAME, SEPARADOR);
        ArrayList<Reserva> reserva = new ArrayList<>();
        
        for(int i = 0; i < lineas.size(); i++){
            String[] arrLinea = lineas.get(i).split(this.SEPARADOR);
            reserva.add(new Reserva(Integer.parseInt(arrLinea[0]), Integer.parseInt(arrLinea[1]), Integer.parseInt(arrLinea[2]), arrLinea[3],(arrLinea[4]), (arrLinea[5])));
        }
        
        return reserva;
    }
    
    public void createReserva(int fk_Usuario, int fk_Funcion, String fk_Asiento, String estado, String fecha_Reserva ) {
        editor.crearArchivo(FILENAME);
        int id = editor.getUltimoId(FILENAME, this.SEPARADOR) + 1;
        String registro = String.format("%d;%d;%d;%s;%s;%s", id, fk_Usuario, fk_Funcion, fk_Asiento, estado, fecha_Reserva);
        editor.addLinea(this.FILENAME, registro);
    }
    
    public Reserva getReservaById(int reserva_id){
        
        String id = String.valueOf(reserva_id);
        String[] fLinea = editor.getRegistro(FILENAME, 0, id, SEPARADOR).split(SEPARADOR);
        
        return new Reserva(Integer.parseInt(fLinea[0]), Integer.parseInt(fLinea[1]), Integer.parseInt(fLinea[2]), fLinea[3],(fLinea[4]), (fLinea[5]));
    }
    
    public Reserva updateReserva(int idReserva, int fk_Usuario, int fk_Funcion, String fk_Asiento, String estado, String fecha_Reserva){
        
        Reserva reserva = getReservaById(idReserva);
        

            reserva.setFk_Usuario(fk_Usuario);
            reserva.setFk_Funcion(fk_Funcion);
            reserva.setFk_Asiento(fk_Asiento);
            if(!estado.isEmpty()){
            reserva.setEstado(estado);
            }
            if(!fecha_Reserva.isEmpty()){
            reserva.setFecha_Reserva(fecha_Reserva);
            }
        
        
        String reserva_updated =String.format("%d;%d;%d;%s;%s;%s", reserva.getIdReserva(), fk_Usuario, fk_Funcion, fk_Asiento, estado, fecha_Reserva);
        
        editor.updateRegistro(FILENAME, String.valueOf(idReserva), SEPARADOR, reserva_updated);
        
        return reserva;
    }
    
    public void deleteReserva(int idReserva ){
        editor.eliminarLineaPorId(FILENAME, SEPARADOR, idReserva);
    }
    
    public ArrayList<Reserva> getReservasByFuncion(int funcion_id){
        
        String id = String.valueOf(funcion_id);
        String fLinea = editor.getRegistro(FILENAME, 2, id, SEPARADOR);
        String[] arrLineas = {};
        ArrayList<Reserva> reservas = new ArrayList<>();
        
        if(!fLinea.isEmpty()){
            arrLineas = fLinea.split("\n");
        }
        
        if(arrLineas.length == 0){
            return reservas;
        }
        
        for(String linea : arrLineas){
            String[] lineaArr = linea.split(SEPARADOR);
            reservas.add(new Reserva(Integer.parseInt(lineaArr[0]), Integer.parseInt(lineaArr[1]), Integer.parseInt(lineaArr[2]), lineaArr[3],(lineaArr[4]), (lineaArr[5])));
        }
        return reservas;
    }
    
    public ArrayList<Reserva> getReservasByUser(int user_id){
        
        String id = String.valueOf(user_id);
        String fLinea = editor.getRegistro(FILENAME, 1, id, SEPARADOR);
        String[] arrLineas = {};
        ArrayList<Reserva> reservas = new ArrayList<>();
        
        if(!fLinea.isEmpty()){
            arrLineas = fLinea.split("\n");
        }
        
        if(arrLineas.length == 0){
            return reservas;
        }
        
        for(String linea : arrLineas){
            String[] lineaArr = linea.split(SEPARADOR);
            reservas.add(new Reserva(Integer.parseInt(lineaArr[0]), Integer.parseInt(lineaArr[1]), Integer.parseInt(lineaArr[2]), lineaArr[3],(lineaArr[4]), (lineaArr[5])));
        }
        return reservas;
    }
}
