package view;

/**
 *
 * @author david-fb - David Basto
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Asiento;
import model.Funcion;
import model.Pelicula;
import model.Usuario;
import services.AsientoService;
import services.FuncionService;
import services.PeliculasService;
import services.SalaService;
import services.UsuarioService;

public class ConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        UsuarioService usuarioService = new UsuarioService();

        while (running) {
            System.out.println("\n--- Bienvenido a la Aplicacion ---");
            System.out.println("1. Iniciar Sesion");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Por favor, ingrese su opcion (1-3): ");

            int choice = -1;

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, ingrese un numero.");
                scanner.next();
                continue;
            }

            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleLogin(scanner, usuarioService);
                    break;
                case 2:
                    handleRegister(scanner, usuarioService);
                    break;
                case 3:
                    running = false;
                    System.out.println("Saliendo... ¡Adios!");
                    break;
                default:
                    System.out.println("Opcion invalida. Por favor, seleccione 1, 2, o 3.");
            }
        }

        scanner.close();
    }

    public static void handleLogin(Scanner scanner, UsuarioService usuarioService) {
        System.out.println("\n--- Iniciar Sesion ---");
        System.out.print("Ingrese nombre de usuario: ");
        String username = scanner.nextLine();

        System.out.print("Ingrese contrasena: ");
        String password = scanner.nextLine();

        Usuario usuario = usuarioService.login(username, password);
        
        if (usuario != null) {
            System.out.println("Inicio de Sesion exitoso");
            showUserMenu(scanner, username);
        } else {
            System.out.println("Usuario y/o contrasena incorrecta");
        }
    }

    public static void handleRegister(Scanner scanner, UsuarioService usuarioService) {
        System.out.println("\n--- Registrarse ---");

        System.out.print("Ingrese el nombre de usuario deseado: ");
        String username = scanner.nextLine();

        System.out.print("Ingrese su nombre completo: ");
        String nombreCompleto = scanner.nextLine();

        System.out.print("Ingrese la contrasena deseada: ");
        String password = scanner.nextLine();

        String rol = "CLIENTE";

        try {
            usuarioService.createUser(username, nombreCompleto, password, rol);
            System.out.println("Registro exitoso para: " + username);
        } catch (Exception e) {
            System.out.println("No se pudo registrar el usuario: " + e.getMessage());
        }
    }

    public static void showUserMenu(Scanner scanner, String username) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("Usuario: " + username);
            System.out.println("1. Funciones");
            System.out.println("2. Asientos");
            System.out.println("3. Perfil");
            System.out.println("4. Cerrar Sesion");
            System.out.print("Seleccione una opcion (1-4): ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, ingrese un numero.");
                scanner.next();
                continue;
            }

            scanner.nextLine();

            switch (choice) {
                case 1:
                    showFunciones(scanner);
                    break;
                case 2:
                    asientos();
                    break;
                case 3:
                    showFunciones(scanner);
                    loggedIn = false;
                    break;
                case 4:
                    System.out.println("Cerrando sesion...");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        }
    }

    public static void showFunciones(Scanner scanner) {

        PeliculasService peliculasService = new PeliculasService();
        FuncionService funcionesService = new FuncionService();
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== MENU DE GESTION DE FUNCIONES ===");
            System.out.println("1. Listar funciones");
            System.out.println("2. Crear nueva funcion");
            System.out.println("3. Actualizar funcion");
            System.out.println("4. Eliminar funcion");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {

                case 1 -> {
                    mostrarFunciones(funcionesService, peliculasService);
                }

                case 2 -> {
                    System.out.println("\nPELICULAS DISPONIBLES:");
                    ArrayList<Pelicula> peliculas = peliculasService.listarpeliculas();

                    if (peliculas.isEmpty()) {
                        System.out.println("⚠️ No hay películas registradas.");
                        break;
                    }

                    System.out.printf("%-4s | %-25s | %-15s | %-15s | %-20s%n",
                            "ID", "TÍTULO", "GÉNERO", "DURACIÓN", "CLASIFICACIÓN");
                    System.out.println("-----------------------------------------------------------------------------------------");

                    for (Pelicula p : peliculas) {
                        System.out.printf("%-4d | %-25s | %-15s | %-15s | %-20s%n",
                                p.getIdPelicula(),
                                p.getTitulo(),
                                p.getGenero(),
                                p.getDuracion(),
                                p.getClasificacion());
                    }

                    System.out.print("\nIngrese el ID de la pelicula: ");
                    int idPelicula = scanner.nextInt();

                    System.out.print("Ingrese el ID de la sala: ");
                    int idSala = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Ingrese la fecha (dd-mm-aaaa): ");
                    String fecha = scanner.nextLine();

                    System.out.print("Ingrese la hora (hh:mm): ");
                    String hora = scanner.nextLine();

                    System.out.print("Ingrese el precio: ");
                    double precio = scanner.nextDouble();

                    funcionesService.createFuncion(fecha, hora, idSala, idPelicula, precio);
                    System.out.println("\nFuncion creada correctamente.");

                    mostrarFunciones(funcionesService, peliculasService);
                }

                case 3 -> {
                    System.out.println("\nACTUALIZAR FUNCION");
                    mostrarFunciones(funcionesService, peliculasService);

                    System.out.print("\nIngrese el ID de la funcion a actualizar: ");
                    int idFuncion = scanner.nextInt();
                    scanner.nextLine();

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
                    System.out.println("\n✅ Funcion actualizada correctamente.");

                    mostrarFunciones(funcionesService, peliculasService);
                }

                case 4 -> {
                    System.out.println("\n🗑 ELIMINAR FUNCION");
                    mostrarFunciones(funcionesService, peliculasService);

                    System.out.print("\nIngrese el ID de la funcion a eliminar: ");
                    int idEliminar = scanner.nextInt();

                    funcionesService.deleteFuncion(idEliminar);
                    System.out.println("\nFuncion eliminada correctamente.");

                    mostrarFunciones(funcionesService, peliculasService);
                }

                case 5 -> {
                    System.out.println("\n");
                    continuar = false;
                }

                default ->
                    System.out.println("Opcion invalida, intente nuevamente.");
            }
        }
    }

    private static void mostrarFunciones(FuncionService funcionesService, PeliculasService peliculasService) {
        ArrayList<Funcion> lista = funcionesService.listarFunciones();

        if (lista.isEmpty()) {
            System.out.println("\nNo hay funciones registradas.");
            return;
        }

        System.out.println("\nFUNCIONES REGISTRADAS:");
        System.out.printf("%-4s | %-12s | %-8s | %-6s | %-25s | %-10s%n",
                "ID", "FECHA", "HORA", "SALA", "PELICULA", "PRECIO");
        System.out.println("--------------------------------------------------------------------------------");

        for (Funcion f : lista) {
            Pelicula peli = peliculasService.getPeliculaById(f.getFk_pelicula());
            String nombrePelicula = (peli != null) ? peli.getTitulo() : "Desconocida";

            System.out.printf("%-4d | %-12s | %-8s | %-6d | %-25s | $%-10.2f%n",
                    f.getIdFuncion(),
                    f.getFecha(),
                    f.getHora(),
                    f.getFk_sala(),
                    nombrePelicula,
                    f.getPrecioEntrada());
        }
    }

    private static void asientos() {
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

                        System.out.println("Id: " + a.getIdAsiento()
                                + " | Fila: " + a.getFila()
                                + " | Numero: " + a.getNumero()
                                + " | Estado: " + a.getEstado()
                                + " | Sala: " + a.getSala());
                    }
                }
                break;
            default:
                throw new AssertionError();
        }
    }
}
