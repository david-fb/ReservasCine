package utils.components;

import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author david
 */
public class SecondaryButton extends JButton {
    public SecondaryButton(String text){
        super(text);
        setFocusPainted(false);
        setPreferredSize(new Dimension(200, 40));
        setBackground(AppStyle.COLOR_FONDO);
        setForeground(AppStyle.FONT_COLOR_SECUNDARIO);
        setFont(AppStyle.FUENTE_BOTON);
    }
}
