package Domini;

import Dades.ResultatDAO;
import Domini.Mapa.Mapa;

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
        loadAllResultsDisk();
        initializeGlobalRanking();
    }

    public static Resultat insertarResultat(Usuari user, Mapa mapa, int puntuacio){
        Resultat resultat;
        if(mapa == null) resultat = new Resultat(user, puntuacio);
        else resultat = new Resultat(user, mapa, puntuacio);

        if(resultatList.contains(resultat)) {
            resultat = getResultatExistent(resultat);
            resultat.afegirPuntuacio(puntuacio);
        }

        else {
            if(mapa == null || mapa.getName()!= null) resultatList.add(resultat);
        }

        if(mapa == null)GlobalRanking.put(resultat.getUsuari().getID(), resultat.getPuntuacio());
        else {
            System.err.println(puntuacio + " // " + getUserGlobalResult(user));
            GlobalRanking.put(resultat.getUsuari().getID(), puntuacio + getUserGlobalResult(user));
        }

        //si es un resultat global el guardem
        if(mapa == null) saveResultat(resultat);

        //si es un resultat d'un mapa amb nom (creat per un usuari) el guardem i guardem tambe el global.
        else {
            if (mapa.getName() != null) {
                saveResultat(resultat);
                Resultat aux = getResultatExistent(new Resultat(resultat.getUsuari(), 0));
                aux.afegirPuntuacio(resultat.getPuntuacio());
                saveResultat(aux);
            }
        }

        return resultat;
    }

    private static void saveResultat(Resultat resultat){
        try {
            if(resultat.getMapa() != null) ResultatDAO.saveResultat(resultat.getUsuari().getID(), resultat.getMapa().getID(), String.valueOf(resultat.getPuntuacio()));
            else ResultatDAO.saveResultat(resultat.getUsuari().getID(), "global", String.valueOf(resultat.getPuntuacio()));
        } catch (IOException e) {
            System.err.println("Couldn't save the result of user: "+ resultat.getUsuari().getID() + " to disk");
        }
    }

    private static Resultat getResultatExistent(Resultat r) {
        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.equals(r)) return result;
        }
        return null;
    }

    private static void initializeGlobalRanking() {
        ArrayList<String> usersID = new ArrayList<String>(ControladorUsuari.getAllUsers().keySet());
        for(int i=0; i<usersID.size(); ++i) {
            if(!GlobalRanking.containsKey(usersID.get(i))) GlobalRanking.put(usersID.get(i),0);
        }
    }

    public static ArrayList<Resultat> getAllResultats() throws IOException {
        return resultatList;
    }

    private static Resultat loadResultDisk(String userID, String mapaID) throws IOException {
        HashMap<String, String> resultMap = ResultatDAO.loadResultat(userID, mapaID);
        Mapa mapa = ControladorMapa.getMapa(resultMap.get("mapa"));
        Usuari usuari = ControladorUsuari.getUsuari(resultMap.get("usuari"));
        int puntuacio = Integer.parseInt(resultMap.get("puntuacio"));
        return insertarResultat(usuari, mapa, puntuacio);
    }

    private static void loadAllResultsDisk() {
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
            Usuari usuari = ControladorUsuari.getUsuari(parts[0]);
            if(parts[1].equals("global")){
                GlobalRanking.put(usuari.getID(), (Integer) resultatPair.getValue());
                resultatList.add(new Resultat(usuari, (Integer) resultatPair.getValue()));
            }
            else {
                Mapa mapa = ControladorMapa.getMapa(parts[1]);
                resultatList.add(new Resultat(usuari,mapa, (Integer) resultatPair.getValue()));
            }
        }
    }

    public static HashMap<String, Integer> getGlobalRanking() {
        return (HashMap<String, Integer>) GlobalRanking.clone();
    }

    public static HashMap<Usuari, Integer> getMapRanking(Mapa mapa){
        HashMap<Usuari, Integer> mapRanking = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getMapa().equals(mapa)) mapRanking.put(result.getUsuari(), result.getPuntuacio());
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
            if(result.getMapa().equals(mapa) && result.getUsuari().equals(usuari)) total+= result.getPuntuacio();
        }

        return total;
    }

    public static HashMap<Mapa, Integer> getUserAllResults(Usuari usuari){
        HashMap<Mapa, Integer> mapResults = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getUsuari().equals(usuari)) mapResults.put(result.getMapa(), result.getPuntuacio());
        }

        return mapResults;
    }

    public static HashMap<Usuari, Integer> getMapAllResults(Mapa mapa){
        HashMap<Usuari, Integer> mapResults = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getMapa() == mapa) mapResults.put(result.getUsuari(), result.getPuntuacio());
        }

        return mapResults;
    }

}
