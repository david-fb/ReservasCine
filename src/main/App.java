package main;

import utils.AppInitializer;
import view.LoginView;

/**
 *
 * @author david-fb - David Basto
 */
public class App {

    public static void main(String[] args) {

        AppInitializer.initialize();
        LoginView vista = new LoginView();
        vista.setVisible(true);
    }
}
