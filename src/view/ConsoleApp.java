package view;

/**
 *
 * @author david-fb - David Basto
 */
import java.util.Scanner;
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

        if (usuarioService.login(username, password)) {
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
            System.out.println("1. Peliculas");
            System.out.println("2. Combos");
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
                    showPeliculas();
                    System.out.println("Entra 1");
                    break;
                case 2:
                    showPeliculas();
                    System.out.println("Entra 2");
                    break;
                case 3:
                    System.out.println("Cerrando sesion...");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        }
    }

    public static void showPeliculas() {
        System.out.println("\n--- Cartelera de Peliculas ---");
        System.out.println("1. Avengers: Endgame");
        System.out.println("2. El Rey León");
        System.out.println("3. Spider-Man: No Way Home");
    }
}
