package Dades;

import Domini.*;
import Domini.Mapa.Mapa;
import javafx.util.Pair;

import java.io.IOException;
import java.util.*;

public class DriverResultatDAO {
    private static Scanner myScanner;
    private static ResultatDAO resultatDAO = new ResultatDAO();
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
                    String userID = myScanner.next();
                    String mapaID = myScanner.next();
                    HashMap<String, String > resultatMap= resultatDAO.loadResultat(userID, mapaID);
                    Mapa map = ControladorMapa.getMapa(resultatMap.get("mapa"));
                    Usuari usuari = ControladorUsuari.getUsuari(resultatMap.get("usuari"));
                    int puntuacio = Integer.parseInt(resultatMap.get("puntuacio"));
                    printResultat(new Resultat(usuari, map, puntuacio));
                    break;
                case "2":
                    userID = myScanner.next();
                    mapaID = myScanner.next();
                    puntuacio = myScanner.nextInt();
                    usuari = ControladorUsuari.getUsuari(userID);
                    Mapa mapa = ControladorMapa.getMapa(mapaID);
                    resultatDAO.saveResultat(ControladorResultat.insertarResultat(usuari, mapa, puntuacio));
                    break;
                case "3":
                    HashMap<String, Integer> resultatsDisk = resultatDAO.loadAllResults();
                    Iterator it = resultatsDisk.entrySet().iterator();
                    while(it.hasNext()) {
                        Map.Entry resultatPair = (Map.Entry)it.next();
                        String filename = (String) resultatPair.getKey();
                        String parts[] = filename.split("_");
                        usuari = ControladorUsuari.getUsuari(parts[0]);
                        mapa = ControladorMapa.getMapa(parts[1]);
                        Resultat resultat = new Resultat(usuari, mapa, (Integer) resultatPair.getValue());
                        printResultat(resultat);
                    }
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

    private static void printResultat(Resultat r) {
        System.out.println("Mapa: "+ r.getMapa().getID());
        System.out.println("Usuari: "+ r.getUsuari().getID());
        System.out.println("Puntuacio: "+ r.getResultat());
        System.out.println();
    }
}
