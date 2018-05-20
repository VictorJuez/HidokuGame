package Domini;

import java.util.ArrayList;

public class ControladorUsuari {
    ArrayList<Usuari> allUsers = new ArrayList<>();

    public ArrayList<Usuari> getAllUsers(){
        return allUsers;
    }

    public Usuari getUsuari(String ID) {
        Usuari usuari = new Usuari(ID);
        allUsers.add(usuari);
        return usuari;
    }
}
