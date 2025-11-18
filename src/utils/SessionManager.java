package utils;

import model.Usuario;

/**
 *
 * @author david
 */
public class SessionManager {

    private static SessionManager instance;
    private Usuario usuarioActual;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

    public boolean haySesionActiva() {
        return usuarioActual != null;
    }
    
    public boolean isAdmin() {
        return usuarioActual.getUser_role().equalsIgnoreCase("ADMIN");
    }
}

