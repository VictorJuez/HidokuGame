package Domini;

import Dades.UsuariDAO;
import Exceptions.UsuariException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorUsuari {
    private static HashMap<String, Usuari> allUsers = new HashMap<>();
    private static Usuari usuariActiu;

    private ControladorUsuari(){}

    public static Usuari insertarUsuari(String ID, String password){
        try {
            if(getUsuari(ID) != null) {
                System.err.println("User: \""+ ID + "\", Already exists on system, returned null");
                return null;
            }
        } catch (UsuariException e) {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        Usuari usuari = new Usuari(ID, password);
        allUsers.put(usuari.getID(), usuari);
        saveUsuariToDisk(usuari);
        usuariActiu = usuari;

        return usuari;
    }

    public static Usuari getUsuari(String ID) throws Exception {
        if(allUsers.get(ID) == null){
            if(loadUsuariDisk(ID) == null) {
                throw new UsuariException("User: "+ ID + ", does not exists, returned null");
            }
        }
        return allUsers.get(ID);
    }

    public static boolean login(String ID, String password) throws Exception {
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
        } catch (FileNotFoundException e){
            System.err.println("User: "+ ID + ", does not exists on disk, returned null");
            return null;
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

    public static void addMapatoUser(String usuariId, String mapaID){
        Usuari usuari = null;
        try {
            usuari = getUsuari(usuariId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        try {
            usuari.addMapa(ControladorMapa.getMapa(mapaID));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
