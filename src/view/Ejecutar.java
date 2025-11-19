/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package view;

import java.util.List;
import java.util.Scanner;
import model.Asiento;
import services.AsientoService;
import services.SalaService;

/**
 *
 * @author Sofia
 */
public class Ejecutar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner leer = new Scanner(System.in);
        
        System.out.println("Ingrese la opcion que desea realizar:"
                + "\n 1. Crear sala"
                + "\n 2. Crear asiento"
                + "\n 3. Consultar asientos");
        int num = leer.nextInt();
        leer.nextLine();
        
        
        switch (num) {
            case 1:
                System.out.println("Ingrese nombre de la sala");
                String nombre = leer.nextLine();
                
                System.out.println("Ingrese el tipo de la sala");
                String tipo = leer.nextLine();
               
                System.out.println("Ingrese la capacidad de la sala");
                int capacidad = leer.nextInt();
                leer.nextLine();
                
                SalaService sala = new SalaService();
                //sala.registrarSala(nombre, capacidad, tipo);
                
                System.out.println("Se registro la sala con exito!! ");
                
            break;
            case 2:
                System.out.println("Ingrese la fila");
                String fila = leer.nextLine();
                
                System.out.println("Ingrese el numero");
                int numero = leer.nextInt();
                leer.nextLine();
                
                System.out.println("Ingrese el estado");
                String estado = leer.nextLine();
                
                System.out.println("Ingrese la sala");
                String Asala = leer.nextLine();
                
                AsientoService asiento = new AsientoService();
                asiento.CrearAsiento(fila, numero, estado, Asala);
                
                System.out.println("Se registro el asiento con exito!! ");
                
                
            break;
            case 3:
                AsientoService service = new AsientoService();
                List<Asiento> asientos = service.getTodosLosAsientos();

                if (asientos.isEmpty()) {
                    System.out.println("No hay asientos.");
                } else {
                    System.out.println("Asientos:");

                    for (int i = 0; i < asientos.size(); i++) {
                        Asiento a = asientos.get(i);

                        System.out.println("Id: " + a.getIdAsiento() +
                               " | Fila: " + a.getFila() +
                               " | Numero: " + a.getNumero() +
                               " | Estado: " + a.getEstado() +
                               " | Sala: " + a.getSala());
                    }
                }
            break;
            default:
                throw new AssertionError();
        }

      

        
        

    }
    
}
