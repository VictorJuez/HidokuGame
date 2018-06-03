package Domini;

import Dades.ResultatDAO;
import Domini.Mapa.Mapa;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ControladorResultat {
    private static final HashMap<String, Usuari> users;
    private static ArrayList<Resultat> resultatList = new ArrayList<>();
    private static HashMap<String, Integer> GlobalRanking = new HashMap<>();

    private ControladorResultat() {}

    static{
        users = ControladorUsuari.getAllUsers();
        initializeGlobalRanking();
    }

    public static Resultat insertarResultat(Usuari user, Mapa mapa, int resultat){
        Resultat r = new Resultat(user, mapa, resultat);
        if(resultatList.contains(r)) {
            r = findResultat(r);
            r.afegirPuntuacio(resultat);
            GlobalRanking.put(user.getID(), r.getResultat());
        }

        else {
            Integer actualResult = 0;
            if(GlobalRanking.containsKey(user.getID())) actualResult = GlobalRanking.get(user.getID());
            GlobalRanking.put(user.getID(), r.getResultat() + actualResult);
            resultatList.add(r);
        }
        try {
            ResultatDAO.saveResultat(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    private static Resultat findResultat(Resultat r) {
        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.equals(r)) return result;
        }
        return null;
    }

    private static void initializeGlobalRanking() {
        ArrayList<String> usersID = new ArrayList<String>(ControladorUsuari.getAllUsers().keySet());
        for(int i=0; i<usersID.size(); ++i) {
            GlobalRanking.put(usersID.get(i),0);
        }
    }

    public static ArrayList<Resultat> getAllResultats() throws IOException {
        return resultatList;
    }

    private static Resultat loadResultDisk(String userID, String mapaID) throws IOException {
        HashMap<String, String> resultMap = ResultatDAO.loadResultat(userID, mapaID);
        Mapa mapa = null;
        try {
            mapa = ControladorMapa.getMapa(resultMap.get("mapa"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Usuari usuari = null;
        try {
            usuari = ControladorUsuari.getUsuari(resultMap.get("usuari"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int puntuacio = Integer.parseInt(resultMap.get("puntuacio"));
        return insertarResultat(usuari, mapa, puntuacio);
    }

    public static void loadAllResultsDisk() {
        HashMap<String, Integer> resultatsDisk = null;
        try {
            resultatsDisk = ResultatDAO.loadAllResults();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator it = resultatsDisk.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry resultatPair = (Map.Entry)it.next();
            String resultatString = (String) resultatPair.getKey();
            String parts[] = resultatString.split("_");
            Usuari usuari = null;
            try {
                usuari = ControladorUsuari.getUsuari(parts[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Mapa mapa = null;
            try {
                mapa = ControladorMapa.getMapa(parts[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            insertarResultat(usuari, mapa, (Integer) resultatPair.getValue());
        }
    }

    public static HashMap<String, Integer> getGlobalRanking() {
        return (HashMap<String, Integer>) GlobalRanking.clone();
    }

    public static HashMap<Usuari, Integer> getMapRanking(Mapa mapa){
        HashMap<Usuari, Integer> mapRanking = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getMapa().equals(mapa)) mapRanking.put(result.getUsuari(), result.getResultat());
        }

        return mapRanking;
    }

    public static int getUserGlobalResult(Usuari usuari){
        return GlobalRanking.get(usuari.getID());
    }

    public static int getUserMapResult(Usuari usuari, Mapa mapa){
        int total = 0;

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getMapa().equals(mapa) && result.getUsuari().equals(usuari)) total+= result.getResultat();
        }

        return total;
    }

    public static HashMap<Mapa, Integer> getUserAllResults(Usuari usuari){
        HashMap<Mapa, Integer> mapResults = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getUsuari().equals(usuari)) mapResults.put(result.getMapa(), result.getResultat());
        }

        return mapResults;
    }

    public static HashMap<Usuari, Integer> getMapAllResults(Mapa mapa){
        HashMap<Usuari, Integer> mapResults = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getMapa() == mapa) mapResults.put(result.getUsuari(), result.getResultat());
        }

        return mapResults;
    }

}
