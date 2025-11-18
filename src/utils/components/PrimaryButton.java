package utils.components;

/**
 *
 * @author david
 */
import javax.swing.JButton;
import java.awt.Color;

public class PrimaryButton extends JButton {

    public PrimaryButton(String texto) {
        super(texto); 
        
        setBackground(AppStyle.COLOR_PRIMARIO);
        setForeground(Color.WHITE);
        setFont(AppStyle.FUENTE_BOTON);
        setFocusPainted(false);
        setBorderPainted(true);
        setOpaque(true);
    }

}
