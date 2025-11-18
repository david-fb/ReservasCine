package services;

import java.util.ArrayList;
import model.Funcion;
import model.Pelicula;
import model.Reserva;
import model.Sala;
import utils.EditorArchivo;

/**
 *
 * @author Jimmi Calvo
 */
public class FuncionService {
    private final EditorArchivo editor = new EditorArchivo();
    private final String FILENAME = "funciones.txt";
    private final String SEPARADOR = ";";
    PeliculasService peliculasService = new PeliculasService();
    SalaService salaService = new SalaService();
    ReservaService reservaService = new  ReservaService();
    
    public ArrayList<Funcion> listarFunciones() {
        ArrayList<String> lineas = editor.getAll(FILENAME, SEPARADOR);
        ArrayList<Funcion> funciones = new ArrayList<>();
        
        for(int i = 0; i < lineas.size(); i++){
            String[] arrLinea = lineas.get(i).split(this.SEPARADOR);
            System.out.println(arrLinea[1]);
            Funcion funcion = new Funcion(Integer.parseInt(arrLinea[0]), arrLinea[1], arrLinea[2], Integer.parseInt(arrLinea[3]),Integer.parseInt(arrLinea[4]), Double.parseDouble(arrLinea[5]));
            Pelicula pelicula = peliculasService.getPeliculaById(Integer.parseInt(arrLinea[4]));
            funcion.setPelicula(pelicula);
            
            Sala sala = salaService.getSalaById(Integer.parseInt(arrLinea[3]));
            funcion.setSala(sala);
            
            funciones.add(funcion);
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
        
        Pelicula pelicula = peliculasService.getPeliculaById(Integer.parseInt(fLineas[4]));
        
        Funcion funcion = new Funcion(Integer.parseInt(fLineas[0]), fLineas[1], fLineas[2], Integer.parseInt(fLineas[3]),Integer.parseInt(fLineas[4]), Double.parseDouble(fLineas[5]));
        
        funcion.setPelicula(pelicula);
        
        Sala sala = salaService.getSalaById(Integer.parseInt(fLineas[3]));
        
        funcion.setSala(sala);
        
        return funcion;
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
    
    public String[]  getAsientosOcupados(Funcion f){
        ArrayList<Reserva> lista = reservaService.getReservasByFuncion(f.getIdFuncion());
        String[] asientos = new String[lista.size()];
        
        for(int i = 0; i < asientos.length; i++){
            asientos[i] = "" + lista.get(i).getFk_Asiento();
        }
        
        return asientos;
    }

}

