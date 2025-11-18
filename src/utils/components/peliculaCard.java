package utils.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Funcion;

/**
 *
 * @author david
 */
public class peliculaCard extends JPanel {
    
    public peliculaCard (Funcion f) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(180, 300));
        setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));

        ImageIcon icon = new ImageIcon(f.getPelicula().getRutaImagen());
        Image img = icon.getImage().getScaledInstance(180, 200, Image.SCALE_SMOOTH);
        JLabel lblImagen = new JLabel(new ImageIcon(img));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel(f.getPelicula().getTitulo());
        titulo.setFont(AppStyle.FUENTE_BOTON);
        titulo.setForeground(AppStyle.FONT_COLOR_PRIMARIO);
        info.add(titulo);
        
        JLabel duracion = new JLabel(f.getPelicula().getGenero() + " | " + f.getPelicula().getDuracion());
        duracion.setFont(AppStyle.FUENTE_NORMAL);
        duracion.setForeground(AppStyle.FONT_COLOR_PRIMARIO);
        info.add(duracion);
        
        JLabel clasificacion = new JLabel("Clasificación: " + f.getPelicula().getClasificacion());
        clasificacion.setFont(AppStyle.FUENTE_NORMAL);
        clasificacion.setForeground(AppStyle.FONT_COLOR_PRIMARIO);
        info.add(clasificacion);
        
        
        JLabel horario = new JLabel("Horario: " + f.getFecha() + " - " + f.getHora());
        horario.setFont(AppStyle.FUENTE_NORMAL);
        horario.setForeground(AppStyle.FONT_COLOR_PRIMARIO);
        info.add(horario);
        
        info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        info.setBackground(new Color(248, 250, 252));
        
        setBackground(new Color(248, 250, 252));
        add(lblImagen, BorderLayout.NORTH);
        add(info, BorderLayout.CENTER);
        
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
}

