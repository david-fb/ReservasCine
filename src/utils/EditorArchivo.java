package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 *
 * @author estudiante301
 */
public class EditorArchivo {

    private final File carpeta;
    private final String FolderName = "bd";

    // Constructor
    public EditorArchivo() {
        this.carpeta = new File(this.FolderName);
    }

    // Crear un archivo nuevo
    public boolean crearArchivo(String nombreArchivo) {

        if (!this.carpeta.exists()) {
            this.carpeta.mkdirs();
        }

        File archivo = new File(this.carpeta, nombreArchivo);

        if (archivo.exists()) {
            return true;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            System.out.println("✅ Archivo creado: " + nombreArchivo);
            return true;

        } catch (IOException e) {
            System.err.println("⚠️ No se pudo crear el archivo: " + nombreArchivo);
            e.printStackTrace();
            return false;
        }
    }

    //Añadir linea
    public boolean addLinea(String nombreArchivo, String contenido) {
        try {
            File archivo = new File(this.carpeta, nombreArchivo);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
                writer.write(contenido);
                writer.newLine();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //obtener registro
    //@params : "nombre / ruta del archivo", posición en la que voy a buscar, valor de busqueda, separador
    public String getRegistro(
            String nombreArchivo,
            int posicion_busqueda,
            String valor_busqueda,
            String separador) {
        File archivo = new File(this.carpeta, nombreArchivo);
        if (!archivo.exists()) {
            return "";
        }
        String textoEncontrado = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] arrLinea = linea.trim().split(separador);
                if (arrLinea[posicion_busqueda].equals(valor_busqueda)) {
                    textoEncontrado += linea + "\n";
                }
            }
            return textoEncontrado;
        } catch (IOException e) {
            e.printStackTrace();
            return textoEncontrado;
        }
    }

    //Actualizar un registro por id
    public boolean updateRegistro(String nombreArchivo,
            String id,
            String separador,
            String valor_nuevo) {
        File archivo = new File(this.carpeta, nombreArchivo);
        if (!archivo.exists()) {
            return false;
        }
        boolean encontrado = false;
        int numeroLinea = 0;
        List<String> lineas;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            lineas = leerArchivo(archivo);

            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] arrLinea = linea.split(separador);
                if (arrLinea[0].equals(id)) {
                    encontrado = true;
                    break;
                }
                numeroLinea++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (encontrado) {
            lineas.set(numeroLinea, valor_nuevo);
            System.out.println("lineas" + lineas.toString());
            guardarLineas(lineas, archivo);
            return true;
        } else {
            return false;
        }
    }

    public int getUltimoId(String nombreArchivo, String separador) {
        File archivo = new File(this.carpeta, nombreArchivo);
        if (!archivo.exists()) {
            return 0;
        }
        int id;
        List<String> lineas = leerArchivo(archivo);

        if (lineas.isEmpty()) {
            return 0;
        }

        String ultimaLinea;
        ultimaLinea = lineas.get(lineas.size() - 1);
        String[] arrLinea = ultimaLinea.split(separador);
        id = Integer.parseInt(arrLinea[0]);
        return id;
    }

    // Metodo privado retorna lista o arr de las lineas del archivo
    // uso exclusivamente de la clase.
    public List<String> leerArchivo(File archivo) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineas;
    }

    // Sobrescribir el archivo con una lista de líneas nuevas actualizadas.
    public void guardarLineas(List<String> lineas, File archivo) {

        if (!archivo.exists()) {
            return;
        }

        File tempFile = new File(this.carpeta, "temp_" + archivo.getName());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            for (String linea : lineas) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                writer.write(linea.trim());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.move(tempFile.toPath(), archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✅ Archivo actualizado: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ Error reemplazando archivo: " + e.getMessage());
        }
    }

    public boolean eliminarLineaPorId(String nombreArchivo, String separador, int idBuscado) {
        File archivo = new File(this.carpeta, nombreArchivo);
        if (!archivo.exists()) {
            return false;
        }

        List<String> lineas = leerArchivo(archivo);
        List<String> nuevasLineas = new ArrayList<>();
        boolean eliminado = false;

        for (String linea : lineas) {
            if (linea.trim().isEmpty()) {
                continue;
            }

            String[] partes = linea.split(separador);
            try {
                int idActual = Integer.parseInt(partes[0]);
                if (idActual == idBuscado) {
                    eliminado = true;
                    continue; // skip this line
                }
            } catch (NumberFormatException e) {
                // si la línea no tiene ID numérico, la mantenemos igual
            }
            nuevasLineas.add(linea);
        }

        if (eliminado) {
            guardarLineas(nuevasLineas, archivo);
        }

        return eliminado;
    }

    public ArrayList<String> getAll(String nombreArchivo, String separador) {
        File archivo = new File(this.carpeta, nombreArchivo);

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ArrayList<String> lineas = (ArrayList<String>) leerArchivo(archivo);

        return lineas;
    }

}
