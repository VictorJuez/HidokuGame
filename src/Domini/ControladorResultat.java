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
    private final HashMap<String, Usuari> users;
    private final ControladorUsuari ctUsuari = new ControladorUsuari();
    private ArrayList<Resultat> resultatList = new ArrayList<>();
    private ResultatDAO resultatDAO = new ResultatDAO();
    private HashMap<String, Integer> GlobalRanking = new HashMap<>();
    private ControladorMapa controladorMapa = new ControladorMapa();
    private ControladorUsuari controladorUsuari = new ControladorUsuari();

    public ControladorResultat() {
        users = ctUsuari.getAllUsers();
        initializeGlobalRanking();
    }

    public Resultat insertarResultat(Usuari user, Mapa mapa, int resultat){
        Resultat r = new Resultat(user, mapa, resultat);
        if(resultatList.contains(r)) {
            r = findResultat(r);
            r.afegirPuntuacio(resultat);
            GlobalRanking.put(user.getID(), r.getResultat());
        }

        else {
            Integer actualResult = GlobalRanking.get(user.getID());
            if (actualResult == null) actualResult = 0;
            GlobalRanking.put(user.getID(), r.getResultat() + actualResult);
            resultatList.add(r);
        }
        try {
            resultatDAO.saveResultat(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    private Resultat findResultat(Resultat r) {
        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.equals(r)) return result;
        }
        return null;
    }

    private void initializeGlobalRanking() {
        for(int i=0; i<users.size(); ++i) GlobalRanking.put(users.get(i).getID(),0);
    }

    public ArrayList<Resultat> getAllResultats() throws IOException {
        return resultatList;
    }

    private Resultat loadResultDisk(String userID, String mapaID) throws IOException {
        return resultatDAO.loadResultat(userID, mapaID);
    }

    public void loadAllResultsDisk() throws IOException {
        HashMap<String, Integer> resultatsDisk = resultatDAO.loadAllResults();

        Iterator it = resultatsDisk.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry resultatPair = (Map.Entry)it.next();
            String resultatString = (String) resultatPair.getKey();
            String parts[] = resultatString.split("_");
            Usuari usuari = controladorUsuari.getUsuari(parts[0]);
            Mapa mapa = controladorMapa.getMapa(parts[1]);
            insertarResultat(usuari, mapa, (Integer) resultatPair.getValue());
        }
    }

    public HashMap<String, Integer> getGlobalRanking() {
        return (HashMap<String, Integer>) GlobalRanking.clone();
    }

    public HashMap<Usuari, Integer> getMapRanking(Mapa mapa){
        HashMap<Usuari, Integer> mapRanking = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getMapa().equals(mapa)) mapRanking.put(result.getUsuari(), result.getResultat());
        }

        return mapRanking;
    }

    public int getUserGlobalResult(Usuari usuari){
        return GlobalRanking.get(usuari.getID());
    }

    public int getUserMapResult(Usuari usuari, Mapa mapa){
        int total = 0;

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getMapa().equals(mapa) && result.getUsuari().equals(usuari)) total+= result.getResultat();
        }

        return total;
    }

    public HashMap<Mapa, Integer> getUserAllResults(Usuari usuari){
        HashMap<Mapa, Integer> mapResults = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getUsuari().equals(usuari)) mapResults.put(result.getMapa(), result.getResultat());
        }

        return mapResults;
    }

    public HashMap<Usuari, Integer> getMapAllResults(Mapa mapa){
        HashMap<Usuari, Integer> mapResults = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getMapa() == mapa) mapResults.put(result.getUsuari(), result.getResultat());
        }

        return mapResults;
    }

}
