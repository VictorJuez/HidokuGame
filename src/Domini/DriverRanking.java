package Domini;

import Domini.Mapa.Mapa;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class DriverRanking {
    private static Scanner myScanner;

    public static void main(String[] args) throws IOException {
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
                    insertResult();
                    break;
                case "2":
                    getResultPartida();
                    break;
                case "3":
                    getGlobalRanking();
                    break;

                case "4":
                    getMapaRanking();
                    break;
                case "5":
                    getUserResults();
                    break;
                case "6":
                    getGlobalResult();
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

    private static void getGlobalResult() {
        System.out.println("Insertar ID del jugador");
        String iduser = myScanner.next();
        Usuari usuari = ControladorUsuari.getUsuari(iduser);
        System.out.println(ControladorResultat.getUserGlobalResult(usuari));
    }

    private static void getUserResults() {
        System.out.println("Insertar ID del jugador");
        String iduser = myScanner.next();
        Usuari usuari = ControladorUsuari.getUsuari(iduser);
        getUserRanking(usuari);
    }

    private static void getMapaRanking() {
        System.out.println("Insertar ID del mapa");
        String idmapa = myScanner.next();
        Mapa mapa = ControladorMapa.getMapa(idmapa);
        getMapRanking(mapa);
    }

    private static void getResultPartida() {
        System.out.println("Insertar ID del mapa");
        String idmapa = myScanner.next();
        Mapa mapa = ControladorMapa.getMapa(idmapa);
        System.out.println("Insertar ID del jugador");
        String iduser = myScanner.next();
        Usuari usuari = ControladorUsuari.getUsuari(iduser);
        System.out.println("puntuacio en el mapa: "+ mapa.getID());
        System.out.println(ControladorResultat.getUserMapResult(usuari, mapa)+ "punts");
    }

    private static void insertResult() {
        System.out.println("Insertar ID del mapa");
        String idmapa = myScanner.next();
        Mapa mapa = null;
        if(!idmapa.equals("global"))mapa = ControladorMapa.getMapa(idmapa);
        System.out.println("Insertar ID del jugador");
        String iduser = myScanner.next();
        System.out.println("Insertar puntuacion");
        int puntuacion = myScanner.nextInt();
        ControladorResultat.insertarResultat(ControladorUsuari.getUsuari(iduser), mapa, puntuacion);
    }

    private static void getUserRanking(Usuari usuari) {
        HashMap<Mapa, Integer> hm = ControladorResultat.getUserAllResults(usuari);
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Mapa m = (Mapa) pair.getKey();
            System.out.println(m.getID() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    private static void getMapRanking(Mapa mapa) {
        HashMap<Usuari, Integer> hm = ControladorResultat.getMapRanking(mapa);
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Usuari m = (Usuari) pair.getKey();
            System.out.println(m.getID() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    private static void getGlobalRanking() {
        HashMap<String, Integer> hm = ControladorResultat.getGlobalRanking();
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
