package utils.components;

/**
 *
 * @author david
 */
import java.awt.Color;
import java.awt.Font;

public final class AppStyle {
    
    public static final Color COLOR_PRIMARIO = new Color(15, 23, 42);
    public static final Color COLOR_FONDO = new Color(250, 250, 250);
    public static final Color COLOR_TEXTO_ERROR = Color.RED;
    
    public static final Color FONT_COLOR_PRIMARIO = new Color(15, 23, 42);
    public static final Color FONT_COLOR_SECUNDARIO = new Color(51, 65, 85);

    public static final Font FUENTE_TITULO = new Font("SansSerif", Font.BOLD, 26);
    public static final Font FUENTE_BOTON = new Font("Arial", Font.BOLD, 14);
    public static final Font FUENTE_NORMAL = new Font("Arial", Font.PLAIN, 12);
    
    private AppStyle() {
        //
    }
}
