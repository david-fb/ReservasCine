package utils;


/**
 *
 * @author david-fb - David Basto
 */
public class AppInitializer {
    private static final String[] REQUIRED_FILES = {
        "usuarios.txt",
        "sala.txt",
        "Asiento.txt",
    };
    
    private static final EditorArchivo editor = new EditorArchivo();
    
    public static void initialize() {
        
        for (String fileName : REQUIRED_FILES) {
            AppInitializer.editor.crearArchivo(fileName);
        }
    }
}
