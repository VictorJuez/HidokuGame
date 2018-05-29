package Domini;

import Dades.UsuariDAO;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorUsuari {
    private HashMap<String, Usuari> allUsers = new HashMap<>();
    private UsuariDAO usuariDAO = new UsuariDAO();
    private ControladorMapa controladorMapa = new ControladorMapa();
    private static Usuari usuariActiu;

    public Usuari insertarUsuari(String ID, String password){
        Usuari usuari = new Usuari(ID, password);
        allUsers.put(usuari.getID(), usuari);
        saveUsuariToDisk(usuari);
        usuariActiu = usuari;

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
        ArrayList<String> usersDisk = loadAllUsersDisk();
        for(String userDisk : usersDisk){
            if(!allUsers.containsKey(userDisk))loadUsuariDisk(userDisk);
        }

        return allUsers;
    }

    private Usuari loadUsuariDisk(String ID){
        Usuari usuari = null;
        StringBuilder password = new StringBuilder();
        ArrayList<String> partidasID = new ArrayList<>();
        ArrayList<String> mapasID = new ArrayList<>();

        try {
            usuariDAO.loadUsuari(ID, password, partidasID, mapasID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            usuari = new Usuari(ID, password.toString(), partidasID, mapasID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        allUsers.put(usuari.getID(), usuari);

        return usuari;
    }

    public void addMapatoUser(String usuariId, String mapaID){
        Usuari usuari = getUsuari(usuariId);
        usuari.addMapa(controladorMapa.getMapa(mapaID));
        saveUsuariToDisk(usuari);
    }

    private void saveUsuariToDisk(Usuari usuari) {
        try {
            usuariDAO.saveUsuari(usuari.getID(), usuari.getPassword(), usuari.getPartidasID(), usuari.getMapasID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login(Usuari usuari, String password){
        if(usuari.checkPassword(password)) {
            usuariActiu = usuari;
            return true;
        }
        return false;
    }

    public static String getUsuariActiu() {
        if(usuariActiu!= null) return usuariActiu.getID();
        else return "nobody";
    }

    private ArrayList<String> loadAllUsersDisk(){
        return usuariDAO.loadAllUsuaris();
    }
}
