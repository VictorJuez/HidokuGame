package Domini;

import Domini.Mapa.Mapa;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class DriverRanking {
    private static Scanner myScanner;
    private static ControladorResultat ctResultat = new ControladorResultat();
    private static ControladorMapa ctMapa = new ControladorMapa();
    private static ControladorUsuari ctUsuari = new ControladorUsuari();

    public static void main(String[] args) throws IOException {
        ctResultat.loadAllResultsDisk();
        System.out.println("Hidato Game");
        String introduction = "Introduce qué operación desea ejecutar:\n"+
                "\t1)Insertar resultado partida \n"+
                "\t2)Obtener resultado partida\n"+
                "\t3)Obtener ranking global\n"+
                "\t4)Obtener ranking de un mapa\n"+
                "\t5)Obtener resultados de un usuario\n"+
                "\t6)Obtener resultado Global de un usuario\n"+
                "\tx) Para salir del juego\n";

        System.out.println(introduction);
        String op = "";
        myScanner = new Scanner(System.in);
        op = myScanner.next();
        boolean active = true;
        while(active) {
            switch (op){
                case "1":
                    System.out.println("Insertar ID del mapa");
                    String idmapa = myScanner.next();
                    Mapa mapa = ctMapa.getMapa(idmapa);
                    //System.out.println("Insertar ID del jugador");
                    //String iduser = myScanner.next();
                    Usuari usuari = ctUsuari.insertarUsuari("enric", "hola");
                    System.out.println("Insertar puntuacion");
                    int puntuacion = myScanner.nextInt();
                    ctResultat.insertarResultat(usuari, mapa, puntuacion);
                    break;
                case "2":
                    System.out.println("Insertar ID del mapa");
                    idmapa = myScanner.next();
                    mapa = ctMapa.getMapa(idmapa);
                    System.out.println("Insertar ID del jugador");
                    String iduser = myScanner.next();
                    usuari = ctUsuari.getUsuari(iduser);
                    System.out.println("puntuacio en el mapa: "+ mapa.getID());
                    System.out.println(ctResultat.getUserMapResult(usuari, mapa)+ "punts");
                    break;
                case "3":
                    getGlobalRanking();
                    break;

                case "4":
                    System.out.println("Insertar ID del mapa");
                    idmapa = myScanner.next();
                    mapa = ctMapa.getMapa(idmapa);
                    getMapRanking(mapa);
                    break;
                case "5":
                    System.out.println("Insertar ID del jugador");
                    iduser = myScanner.next();
                    usuari = ctUsuari.getUsuari(iduser);
                    getUserRanking(usuari);
                    break;
                case "6":
                    System.out.println("Insertar ID del jugador");
                    iduser = myScanner.next();
                    usuari = ctUsuari.getUsuari(iduser);
                    System.out.println(ctResultat.getUserGlobalResult(usuari));
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

    private static void getUserRanking(Usuari usuari) {
        HashMap<Mapa, Integer> hm = ctResultat.getUserAllResults(usuari);
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Mapa m = (Mapa) pair.getKey();
            System.out.println(m.getID() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    private static void getMapRanking(Mapa mapa) {
        HashMap<Usuari, Integer> hm = ctResultat.getMapRanking(mapa);
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Usuari m = (Usuari) pair.getKey();
            System.out.println(m.getID() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    private static void getGlobalRanking() {
        HashMap<String, Integer> hm = ctResultat.getGlobalRanking();
        printMap(hm);
    }

    private static void printMap(HashMap<String, Integer> m){
        Iterator it = m.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
