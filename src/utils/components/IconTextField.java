/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.components;

/**
 *
 * @author david
 */
import javax.swing.*;
import java.awt.*;

public class IconTextField extends JPanel {

    private final JTextField textField;

    public IconTextField(String iconPath, int iconSize) {
        super(new BorderLayout()); 
        
        ImageIcon originalIcon = new ImageIcon(iconPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(
            iconSize, iconSize, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
        
        this.add(iconLabel, BorderLayout.WEST); 
        
        this.add(textField, BorderLayout.CENTER); 
        
        this.setBorder(BorderFactory.createLineBorder(AppStyle.COLOR_PRIMARIO));
        this.setBackground(Color.WHITE);
    }

    public String getText() {
        return textField.getText();
    }
}
