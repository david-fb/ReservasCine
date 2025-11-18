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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IconPasswordField extends JPanel {

    private JPasswordField passwordField;
    private JLabel visibilityLabel;

    public IconPasswordField(String fieldIconPath, int iconSize) {
        super(new BorderLayout()); 
        
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        ImageIcon fieldIcon = createScaledIcon(fieldIconPath, iconSize);
        JLabel fieldIconLabel = new JLabel(fieldIcon);
        fieldIconLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        

        ImageIcon eyeIcon = createScaledIcon("src/assets/eye-show-icon.png", iconSize);
        ImageIcon slashEyeIcon = createScaledIcon("src/assets/eye-off-icon.png", iconSize);

        visibilityLabel = new JLabel(slashEyeIcon); // Mostrar el ojo tachado por defecto
        visibilityLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visibilityLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        visibilityLabel.addMouseListener(new MouseAdapter() {
            private boolean isPasswordVisible = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    passwordField.setEchoChar((char) 0);
                    visibilityLabel.setIcon(eyeIcon);
                } else {
                    passwordField.setEchoChar('•'); 
                    visibilityLabel.setIcon(slashEyeIcon);
                }
            }
        });
        
        this.add(fieldIconLabel, BorderLayout.WEST);
        this.add(passwordField, BorderLayout.CENTER);
        this.add(visibilityLabel, BorderLayout.EAST);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(AppStyle.COLOR_PRIMARIO));
    }

    private ImageIcon createScaledIcon(String path, int size) {
        ImageIcon original = new ImageIcon(path);
        Image scaled = original.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }
   
}
