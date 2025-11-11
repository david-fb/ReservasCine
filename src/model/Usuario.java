package model;

/**
 *
 * @author david-fb - David Basto
 */
public final class Usuario {

    private final int user_id;
    private String user_login;
    private String user_name;
    private String password;
    private String user_role;

    public Usuario(int user_id, String user_login, String user_name, String password, String user_role) {
        this.user_id = user_id;
        this.user_login = user_login;
        this.user_name = user_name;
        this.password = password;
        this.user_role = user_role;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String toFileString() {
        return user_id + ";" + user_login + ";" + user_name + ";" + password;
    }

    @Override
    public String toString() {
        return "Usuario{id=" + user_id + ", login='" + user_login + "', nombre='" + user_name + "'}";
    }

}
