package Domini;

import java.util.ArrayList;
import java.util.HashMap;

public class ControladorResultat {
    private final ArrayList<Usuari> users;
    private final ControladorUsuari ctUsuari = new ControladorUsuari();
    private ArrayList<Resultat> resultatList = new ArrayList<>();
    private HashMap<Usuari, Integer> GlobalRanking = new HashMap<>();

    public ControladorResultat() {
        users = ctUsuari.getAllUsers();
        initializeGlobalRanking();
    }

    public Resultat insertarResultat(Usuari user, Mapa mapa, int resultat){
        Resultat r = new Resultat(user, mapa, resultat);
        GlobalRanking.put(user, r.getResultat());
        resultatList.add(r);
        return r;
    }

    private void initializeGlobalRanking() {
        for(int i=0; i<users.size(); ++i) GlobalRanking.put(users.get(i),0);
    }

    public HashMap<Usuari, Integer> getGlobalRanking() {
        return GlobalRanking;
    }

    public HashMap<Usuari, Integer> getMapRanking(Mapa mapa){
        HashMap<Usuari, Integer> mapRanking = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getMapa() == mapa) mapRanking.put(result.getUsuari(), result.getResultat());
        }

        return mapRanking;
    }

    public int getUserGlobalResult(Usuari usuari){
        return GlobalRanking.get(usuari);
    }

    public int getUserMapResult(Usuari usuari, Mapa mapa){
        int total = 0;

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getMapa() == mapa && result.getUsuari() == usuari) total+= result.getResultat();
        }

        return total;
    }

    public HashMap<Mapa, Integer> getUserAllResults(Usuari usuari){
        HashMap<Mapa, Integer> mapResults = new HashMap<>();

        for(int i=0; i<resultatList.size(); ++i){
            Resultat result = resultatList.get(i);
            if(result.getUsuari() == usuari) mapResults.put(result.getMapa(), result.getResultat());
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
