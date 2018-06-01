package Domini;

import Dades.UsuariDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorUsuari {
    private static HashMap<String, Usuari> allUsers = new HashMap<>();
    private static Usuari usuariActiu;

    private ControladorUsuari(){}

    public static Usuari insertarUsuari(String ID, String password){
        if(getUsuari(ID) != null) return null;
        Usuari usuari = new Usuari(ID, password);
        allUsers.put(usuari.getID(), usuari);
        saveUsuariToDisk(usuari);
        usuariActiu = usuari;

        return usuari;
    }

    public static Usuari getUsuari(String ID) {
        if(allUsers.get(ID) == null){
            return loadUsuariDisk(ID);
        }
        return allUsers.get(ID);
    }

    public static boolean login(String ID, String password){
        return getUsuari(ID).checkPassword(password);
    }

    public static HashMap<String, Usuari> getAllUsers(){
        ArrayList<String> usersDisk = loadAllUsersDisk();
        for(String userDisk : usersDisk){
            if(!allUsers.containsKey(userDisk))loadUsuariDisk(userDisk);
        }

        return allUsers;
    }

    private static Usuari loadUsuariDisk(String ID){
        Usuari usuari = null;
        StringBuilder password = new StringBuilder();
        ArrayList<String> partidasID = new ArrayList<>();
        ArrayList<String> mapasID = new ArrayList<>();

        try {
            UsuariDAO.loadUsuari(ID, password, partidasID, mapasID);
        } catch (IOException e) {
            return null;
        }

        try {
            usuari = new Usuari(ID, password.toString(), partidasID, mapasID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        allUsers.put(usuari.getID(), usuari);

        return usuari;
    }

    public static void addMapatoUser(String usuariId, String mapaID){
        Usuari usuari = getUsuari(usuariId);
        usuari.addMapa(ControladorMapa.getMapa(mapaID));
        saveUsuariToDisk(usuari);
    }

    public static void addPartidaToUser(String usuariID, String partidaID){
        Usuari usuari = getUsuari(usuariID);
        usuari.addPartida(ControladorPartida.getPartida(partidaID));
        saveUsuariToDisk(usuari);
    }

    private static void saveUsuariToDisk(Usuari usuari) {
        try {
            UsuariDAO.saveUsuari(usuari.getID(), usuari.getPassword(), usuari.getPartidasID(), usuari.getMapasID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean login(Usuari usuari, String password){
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

    private static ArrayList<String> loadAllUsersDisk(){
        return UsuariDAO.loadAllUsuaris();
    }

    public static void cleanUp() {
        allUsers = new HashMap<>();
        usuariActiu = null;
    }
}
