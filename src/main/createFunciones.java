package main;

import java.util.ArrayList;
import java.util.Scanner;
import model.Funcion;
import model.Pelicula;
import services.FuncionService;
import services.PeliculasService;
import utils.AppInitializer;

public class createFunciones {

    public static void main(String[] args) {
        AppInitializer.initialize();
        PeliculasService peliculasService = new PeliculasService();
        FuncionService funcionesService = new FuncionService();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n🎬=== MENÚ DE GESTIÓN DE FUNCIONES ===🎬");
            System.out.println("1. Listar funciones");
            System.out.println("2. Crear nueva función");
            System.out.println("3. Actualizar función");
            System.out.println("4. Eliminar función");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> {
                    System.out.println("\n📋 FUNCIONES REGISTRADAS:");
                    ArrayList<Funcion> lista = funcionesService.listarFunciones();

                    // Encabezado de la tabla
                    System.out.printf("%-4s | %-12s | %-5s | %-6s | %-20s | %-10s%n",
                            "ID", "FECHA", "HORA", "SALA", "PELÍCULA", "PRECIO");
                    System.out.println("---------------------------------------------------------------------");

                    // Recorremos las funciones y mostramos nombre de película
                    for (Funcion f : lista) {
                        Pelicula peli = peliculasService.getPeliculaById(f.getFk_pelicula());
                        String nombrePelicula = (peli != null) ? peli.getTitulo(): "Desconocida";
                        System.out.printf("%-4d | %-12s | %-5s | %-6d | %-20s | $%-10.2f%n",
                                f.getIdFuncion(), f.getFecha(), f.getHora(), f.getFk_sala(), nombrePelicula, f.getPrecioEntrada());
                    }
                }

                case 2 -> {
                    System.out.println("\n🎥 PELÍCULAS DISPONIBLES:");
                    System.out.println(peliculasService.listarpeliculas().toString());

                    System.out.print("\nIngrese el ID de la película: ");
                    int idPelicula = scanner.nextInt();

                    System.out.print("Ingrese el ID de la sala: ");
                    int idSala = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer

                    System.out.print("Ingrese la fecha (dd-mm-aaaa): ");
                    String fecha = scanner.nextLine();

                    System.out.print("Ingrese la hora (hh:mm): ");
                    String hora = scanner.nextLine();

                    System.out.print("Ingrese el precio: ");
                    double precio = scanner.nextDouble();

                    funcionesService.createFuncion(fecha, hora, idSala, idPelicula, precio);
                    System.out.println("✅ Función creada correctamente.");
                }

                case 3 -> {
                    System.out.println("\n🛠 ACTUALIZAR FUNCIÓN");
                    System.out.println(funcionesService.listarFunciones().toString());

                    System.out.print("\nIngrese el ID de la función a actualizar: ");
                    int idFuncion = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer

                    System.out.print("Nueva fecha (dd-mm-aaaa): ");
                    String nuevaFecha = scanner.nextLine();

                    System.out.print("Nueva hora (hh:mm): ");
                    String nuevaHora = scanner.nextLine();

                    System.out.print("Nuevo ID de sala: ");
                    int nuevaSala = scanner.nextInt();

                    System.out.print("Nuevo ID de película: ");
                    int nuevaPelicula = scanner.nextInt();

                    System.out.print("Nuevo precio: ");
                    double nuevoPrecio = scanner.nextDouble();

                    funcionesService.updateFuncion(idFuncion, nuevaFecha, nuevaHora, nuevaSala, nuevaPelicula, nuevoPrecio);
                    System.out.println("✅ Función actualizada correctamente.");
                }

                case 4 -> {
                    System.out.println("\n🗑 ELIMINAR FUNCIÓN");
                    System.out.println(funcionesService.listarFunciones().toString());

                    System.out.print("\nIngrese el ID de la función a eliminar: ");
                    int idEliminar = scanner.nextInt();

                    funcionesService.deleteFuncion(idEliminar);
                    System.out.println("🗑️ Función eliminada correctamente.");
                }

                case 5 -> {
                    System.out.println("\n👋 Saliendo del sistema...");
                    continuar = false;
                }

                default ->
                    System.out.println("⚠️ Opción inválida, intente nuevamente.");
            }
        }

        scanner.close();
        System.out.println("✅ Programa finalizado con éxito.");
    }
}
