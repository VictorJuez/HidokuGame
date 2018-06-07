////////////////////////////////////////////////////////
////////////// PROGRAMAT PER VÍCTOR JUEZ ///////////////
////////////////////////////////////////////////////////
package Domini;

import Dades.UsuariDAO;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ControladorUsuari {
    private static HashMap<String, Usuari> allUsers = new HashMap<>();
    private static HashMap<String, Integer> GlobalRanking = new HashMap<>();
    private static HashMap<String, Integer> GlobalRecords = new HashMap<>();
    private static Usuari actualRecord;
    private static Usuari usuariActiu;

    private ControladorUsuari(){}

    static{
        getAllUsers();
        HashMap<String, Usuari> hm = ControladorUsuari.getAllUsers();
        ArrayList<String> al = new ArrayList<>(hm.keySet());

        int record = 0;
        Usuari usuariRecord = null;
        for(String userID : al){
            int userRecord = ControladorUsuari.getUsuari(userID).getRecord();
            if(userRecord > record){
                record = userRecord;
                usuariRecord = ControladorUsuari.getUsuari(userID);
            }
        }
        actualRecord = usuariRecord;
    }

    /**
     * Crea un usuari nou
     * @param ID
     * @param password
     * @return retorna l'usuari creat o null en cas que l'ID del usuari ja existís en el sistema
     */
    public static Usuari insertarUsuari(String ID, String password){
        if(getUsuari(ID) != null) {
            System.err.println("L'usuari "+ ID + " ja existeix en el sistema, no es pot tornar a crear");
            return null;
        }
        Usuari usuari = new Usuari(ID, password);
        allUsers.put(usuari.getID(), usuari);
        saveUsuariToDisk(usuari);
        usuariActiu = usuari;

        return usuari;
    }

    /**
     * Obte l'usuari amb l'id ID del sistema
     * @param ID
     * @return l'usuari corresponent o null en cas de no existir l'usuari
     */
    public static Usuari getUsuari(String ID) {
        if(allUsers.get(ID) == null){
            return loadUsuariDisk(ID);
        }
        return allUsers.get(ID);
    }

    /**
     * Logout de l'usuari en el sistema, es posa usuariActiu a null
     */
    public static void logout () { usuariActiu = null; }

    public static HashMap<String, Usuari> getAllUsers(){
        ArrayList<String> usersDisk = loadAllUsersDisk();
        for(String userDisk : usersDisk){
            if(!allUsers.containsKey(userDisk))loadUsuariDisk(userDisk);
        }

        return allUsers;
    }

    /**
     * Carregar un usuari amb id ID del disc.
     * @param ID
     * @return l'Usuari corresponent o null en cas de no existir.
     */
    private static Usuari loadUsuariDisk(String ID){
        Usuari usuari = null;
        StringBuilder password = new StringBuilder();
        StringBuilder puntuacio = new StringBuilder();
        StringBuilder record = new StringBuilder();
        ArrayList<String> partidasID = new ArrayList<>();
        ArrayList<String> mapasID = new ArrayList<>();

        try {
            UsuariDAO.loadUsuari(ID, password, puntuacio, record, partidasID);
        } catch (IOException e) {
            return null;
        }

        try {
            usuari = new Usuari(ID, password.toString(), Integer.valueOf(puntuacio.toString()), Integer.valueOf(String.valueOf(record)), partidasID, mapasID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        allUsers.put(usuari.getID(), usuari);
        GlobalRanking.put(usuari.getID(), usuari.getPuntuacio());

        return usuari;
    }

    /**
     * Assignar una partida a un usuari.
     * @param usuariID
     * @param partidaID
     */
    public static void addPartidaToUser(String usuariID, String partidaID){
        Usuari usuari = getUsuari(usuariID);
        usuari.addPartida(ControladorPartida.getPartida(partidaID));
        saveUsuariToDisk(getUsuari(usuariID));
    }

    public static void removePartidaToUser(String usuariID, String partidaID){
        Usuari usuari = getUsuari(usuariID);
        usuari.popPartida(ControladorPartida.getPartida(partidaID));
        saveUsuariToDisk(getUsuari(usuariID));
    }

    /**
     * Guardar un usuari al disc.
     * @param usuari
     */
    public static void saveUsuariToDisk(Usuari usuari) {
        try {
            UsuariDAO.saveUsuari(usuari.getID(), usuari.getPassword(), usuari.getPuntuacio(), usuari.getRecord(), usuari.getPartidasID());
        } catch (IOException e) {
            System.err.println("Couldn't save to disk user: "+ usuari.getID());
            return;
        }
    }


    /**
     * Login d'un usuari al sistema
     * @param usuari
     * @param password
     * @return true si la contrasenya correspon al usuari, false si no.
     */
    public static boolean login(Usuari usuari, String password){
        if(usuari.checkPassword(password)) {
            usuariActiu = usuari;
            return true;
        }
        return false;
    }

    /**
     * Obte l'usuari loguejat actualment
     * @return l'ID del usuari loguejat actualment o "nobody" si no n'hi ha cap de loguejat.
     */
    public static String getUsuariActiu() {
        if(usuariActiu!= null) return usuariActiu.getID();
        else return "nobody";
    }

    public static Usuari getActualRecord() {
        return actualRecord;
    }

    /**
     * Carregar tots els usuaris del disc
     * @return un ArrayList amb tots els IDs dels usuaris del disc.
     */
    private static ArrayList<String> loadAllUsersDisk(){
        return UsuariDAO.loadAllUsuaris();
    }

    //ranking functions
    public static boolean insertarResultat(Usuari usuari, int puntuacio){
        boolean result = false;
        usuari.setPuntuacio(usuari.getPuntuacio() + puntuacio);
        GlobalRanking.put(usuari.getID(), usuari.getPuntuacio());
        if(puntuacio > usuari.getRecord()){
            usuari.setRecord(puntuacio);
            saveUsuariToDisk(usuari);
            result = true;
        }

        if(actualRecord == null || puntuacio > actualRecord.getRecord()) actualRecord = usuari;

        saveUsuariToDisk(usuari);
        return false;
    }

    public static ArrayList<Pair<String, Integer>> getGlobalRanking() {
        ArrayList<Pair<String, Integer>> al = new ArrayList<>();
        ArrayList<String> usersID = new ArrayList<>(GlobalRanking.keySet());

        for(String userID : usersID){
            int puntuacio = GlobalRanking.get(userID);
            Pair p = new Pair(userID, puntuacio);
            al.add(p);
        }

        Collections.sort(al, new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(final Pair<String, Integer> o1, final Pair<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return al;
    }

    public static HashMap<String, Integer> getGlobalRecords() {
        return GlobalRecords;
    }

    /**
     * Buida tots els usuaris del Controlador
     */
    public static void cleanUp() {
        allUsers = new HashMap<>();
        usuariActiu = null;
    }
}
