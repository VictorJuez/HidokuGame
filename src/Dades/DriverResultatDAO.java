package Dades;

import Domini.*;
import Domini.Mapa.Mapa;
import javafx.util.Pair;

import java.io.IOException;
import java.util.*;

public class DriverResultatDAO {
    private static Scanner myScanner;
    public static void main(String[] args) throws IOException {
        String introduction = "Introduce qué operación desea ejecutar:\n"+
                "\t1) loadResult(UserID, mapaID)\n"+
                "\t2) saveResult(Resultat)\n"+
                "\t3) loadAllResults()\n"+
                "\tx) Para salir del juego\n";
        System.out.println(introduction);
        String op = "";
        myScanner = new Scanner(System.in);
        op = myScanner.next();

        boolean active = true;
        while(active) {
            switch (op){
                case "1":
                    loadResult();
                    break;
                case "2":
                    saveResult();
                    break;
                case "3":
                    loadAllResults();
                    break;
                case "x":
                    System.out.println("exiting game...");
                    active = false;
                    break;
                default:
                    System.out.println("this operation does not exist");
                    break;
            }
            if (active) {
                System.out.println("---------------------------------------\n\n"
                        +introduction);
                op = myScanner.next();
            }
        }
    }

    private static void loadAllResults() {
        HashMap<String, Integer> resultatsDisk = null;
        try {
            resultatsDisk = ResultatDAO.loadAllResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator it = resultatsDisk.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry resultatPair = (Map.Entry)it.next();
            String filename = (String) resultatPair.getKey();
            String parts[] = filename.split("_");
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
            Resultat resultat = new Resultat(usuari, mapa, (Integer) resultatPair.getValue());
            printResultat(resultat);
        }
    }

    private static void saveResult() {
        String userID = myScanner.next();
        String mapaID = myScanner.next();
        int puntuacio = myScanner.nextInt();
        Usuari usuari = null;
        try {
            usuari = ControladorUsuari.getUsuari(userID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Mapa mapa = null;
        try {
            mapa = ControladorMapa.getMapa(mapaID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultatDAO.saveResultat(ControladorResultat.insertarResultat(usuari, mapa, puntuacio));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadResult() {
        String userID = myScanner.next();
        String mapaID = myScanner.next();
        HashMap<String, String > resultatMap= null;
        try {
            resultatMap = ResultatDAO.loadResultat(userID, mapaID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mapa map = null;
        try {
            map = ControladorMapa.getMapa(resultatMap.get("mapa"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Usuari usuari = null;
        try {
            usuari = ControladorUsuari.getUsuari(resultatMap.get("usuari"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int puntuacio = Integer.parseInt(resultatMap.get("puntuacio"));
        printResultat(new Resultat(usuari, map, puntuacio));
    }

    private static void printResultat(Resultat r) {
        System.out.println("Mapa: "+ r.getMapa().getID());
        System.out.println("Usuari: "+ r.getUsuari().getID());
        System.out.println("Puntuacio: "+ r.getResultat());
        System.out.println();
    }
}
