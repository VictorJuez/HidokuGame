package Domini;

import java.util.HashMap;

public class ControladorUsuari {
    private HashMap<String, Usuari> allUsers = new HashMap<>();

    public Usuari insertarUsuari(String ID, String password){
        Usuari usuari = new Usuari(ID, password);
        return usuari;
    }

    public Usuari getUsuari(String ID) {
        return allUsers.get(ID);
    }

    public boolean login(String ID, String password){
        return getUsuari(ID).checkPassword(password);
    }

    public HashMap<String, Usuari> getAllUsers(){
        return allUsers;
    }
}
