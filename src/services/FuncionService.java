package services;

import java.util.ArrayList;
import model.Funcion;
import utils.EditorArchivo;

/**
 *
 * @author Jimmi Calvo
 */
public class FuncionService {
    private final EditorArchivo editor = new EditorArchivo();
    private final String FILENAME = "funciones.txt";
    private final String SEPARADOR = ";";
    
    public ArrayList<Funcion> listarFunciones() {
        ArrayList<String> lineas = editor.getAll(FILENAME, SEPARADOR);
        ArrayList<Funcion> funciones = new ArrayList<>();
        
        for(int i = 0; i < lineas.size(); i++){
            String[] arrLinea = lineas.get(i).split(this.SEPARADOR);
            System.out.println(arrLinea[1]);
            funciones.add(new Funcion(Integer.parseInt(arrLinea[0]), arrLinea[1], arrLinea[2], Integer.parseInt(arrLinea[3]),Integer.parseInt(arrLinea[4]), Double.parseDouble(arrLinea[5])));
        }
        
        return funciones;
    }
    
    public void createFuncion(String fecha, String hora,  int sala, int pelicula, double precioEntrada ) {
        editor.crearArchivo(FILENAME);
        int id = editor.getUltimoId(FILENAME, this.SEPARADOR) + 1;
        String registro = String.format("%d;%s;%s;%d;%d;%ff", id, fecha, hora, sala, pelicula, precioEntrada);
        editor.addLinea(this.FILENAME, registro);
    }
    
    public Funcion getFuncionById(int funcion_id){
        
        String id = String.valueOf(funcion_id);
        String[] fLineas = editor.getRegistro(FILENAME, 0, id, SEPARADOR).split(SEPARADOR);
        
        return new Funcion(Integer.parseInt(fLineas[0]), fLineas[1], fLineas[2], Integer.parseInt(fLineas[3]),Integer.parseInt(fLineas[4]), Double.parseDouble(fLineas[5]));
    }
    
    public Funcion updateFuncion(int idFuncion, String fecha, String hora,  int sala, int pelicula, double precioEntrada){
        
        Funcion funcion = getFuncionById(idFuncion);
        
        if(!fecha.isEmpty()){
            funcion.setFecha(fecha);
        }
        
        if(!hora.isEmpty()){
            funcion.setHora(hora);
        }
        if(sala>0){
            funcion.setFk_sala(sala);
        } else {
        }
        if(pelicula>0){
            funcion.setFk_pelicula(pelicula);
        }
        
        String funcion_updated =String.format("%d;%s;%s;%d;%d;%f", funcion.getIdFuncion(), fecha, hora, sala, pelicula, precioEntrada);
        
        editor.updateRegistro(FILENAME, String.valueOf(idFuncion), SEPARADOR, funcion_updated);
        
        return funcion;
    }
    
    public void deleteFuncion(int IdFuncion){
        editor.eliminarLineaPorId(FILENAME, SEPARADOR, IdFuncion);
    }
}

