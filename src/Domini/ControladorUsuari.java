package Domini;

import Dades.UsuariDAO;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorUsuari {
    private HashMap<String, Usuari> allUsers = new HashMap<>();
    private UsuariDAO usuariDAO = new UsuariDAO();

    public Usuari insertarUsuari(String ID, String password){
        Usuari usuari = new Usuari(ID, password);
        allUsers.put(usuari.getID(), usuari);
        return usuari;
    }

    public Usuari getUsuari(String ID) {
        if(allUsers.get(ID) == null){
            return loadUsuariDisk(ID);
        }
        return allUsers.get(ID);
    }

    public boolean login(String ID, String password){
        return getUsuari(ID).checkPassword(password);
    }

    public HashMap<String, Usuari> getAllUsers(){
        ArrayList<String> usersDisk = new ArrayList<>();
        for(String userDisk : usersDisk){
            if(!allUsers.containsKey(userDisk))loadUsuariDisk(userDisk);
        }

        return allUsers;
    }

    private Usuari loadUsuariDisk(String ID){
        Usuari usuari = null;
        String password = null;
        ArrayList<String> partidasID = new ArrayList<>();
        ArrayList<String> mapasID = new ArrayList<>();

        try {
            usuariDAO.loadUsuari(ID, password, partidasID, mapasID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            usuari = new Usuari(ID, password, partidasID, mapasID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        allUsers.put(usuari.getID(), usuari);

        return usuari;
    }

    private ArrayList<String> loadAllUsersDisk(){
        return usuariDAO.loadAllUsuaris();
    }
}
