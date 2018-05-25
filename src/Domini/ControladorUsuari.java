package Domini;

import java.util.HashMap;

public class ControladorUsuari {
    HashMap<String, Usuari> allUsers = new HashMap<>();

    public HashMap<String, Usuari> getAllUsers(){
        return allUsers;
    }

    public Usuari getUsuari(String ID) {
        if(allUsers.containsKey(ID)) return allUsers.get(ID);
        else {
            Usuari usuari = new Usuari(ID);
            allUsers.put(usuari.getID(), usuari);
            return usuari;
        }
    }
}
