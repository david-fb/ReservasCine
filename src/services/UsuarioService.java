package services;

import java.util.ArrayList;
import model.Usuario;
import utils.EditorArchivo;

/**
 *
 * @author david-fb - David Basto
 */
public class UsuarioService {
    
    private final EditorArchivo editor = new EditorArchivo();
    private final String FILENAME = "usuarios.txt";
    private final String SEPARADOR = ";";
    
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<String> lineas = editor.getAll(FILENAME, SEPARADOR);
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        for(int i = 0; i < lineas.size(); i++){
            String[] arrLinea = lineas.get(i).split(this.SEPARADOR);
            usuarios.add(new Usuario(Integer.parseInt(arrLinea[0]), arrLinea[1], arrLinea[2], arrLinea[3]));
        }
        
        return usuarios;
    }
    
    public void createUser(String user_login, String user_name, String password) {
        editor.crearArchivo(FILENAME);
        int id = editor.getUltimoId(FILENAME, this.SEPARADOR) + 1;
        String registro = String.format("%d;%s;%s;%s", id, user_login, user_name, password);

        editor.addLinea(this.FILENAME, registro);
    }
    
    public Usuario getUserById(int user_id){
        
        String id = String.valueOf(user_id);
        String[] userLineas = editor.getRegistro(FILENAME, 0, id, SEPARADOR).split(SEPARADOR);
        
        return new Usuario(Integer.parseInt(userLineas[0]), userLineas[1], userLineas[2], userLineas[3]);
    }
    
    public Usuario updateUser(int user_id, String user_name, String password){
        
        Usuario usuario = getUserById(user_id);
        
        if(!user_name.isEmpty()){
            usuario.setUser_name(user_name);
        }
        
        if(!password.isEmpty()){
            usuario.setPassword(password);
        }
        
        String user_updated = String.format("%s;%s;%s;%s", usuario.getUser_id(), usuario.getUser_login(), usuario.getUser_name(), usuario.getPassword());
        
        editor.updateRegistro(FILENAME, String.valueOf(user_id), SEPARADOR, user_updated);
        
        return usuario;
    }
    
    public void deleteUser(int user_id){
        editor.eliminarLineaPorId(FILENAME, SEPARADOR, user_id);
    }
}
