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
        ControladorResultat.loadAllResultsDisk();
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
                    insertarResultado();
                    break;
                case "2":
                    getResultado();
                    break;
                case "3":
                    getGlobalRanking();
                    break;

                case "4":
                    getRankingMapa();
                    break;
                case "5":
                    getUserResults();
                    break;
                case "6":
                    getGlobalResultUser();
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

    private static void getGlobalResultUser() {
        System.out.println("Insertar ID del jugador");
        String iduser = myScanner.next();
        Usuari usuari = null;
        try {
            usuari = ControladorUsuari.getUsuari(iduser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(ControladorResultat.getUserGlobalResult(usuari));
    }

    private static void getUserResults() {
        System.out.println("Insertar ID del jugador");
        String iduser = myScanner.next();
        Usuari usuari = null;
        try {
            usuari = ControladorUsuari.getUsuari(iduser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getUserRanking(usuari);
    }

    private static void getRankingMapa() {
        System.out.println("Insertar ID del mapa");
        String idmapa = myScanner.next();
        Mapa mapa = null;
        try {
            mapa = ControladorMapa.getMapa(idmapa);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMapRanking(mapa);
    }

    private static void getResultado() {
        System.out.println("Insertar ID del mapa");
        String idmapa = myScanner.next();
        Mapa mapa = null;
        try {
            mapa = ControladorMapa.getMapa(idmapa);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Insertar ID del jugador");
        String iduser = myScanner.next();
        Usuari usuari = null;
        try {
            usuari = ControladorUsuari.getUsuari(iduser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("puntuacio en el mapa: "+ mapa.getID());
        System.out.println(ControladorResultat.getUserMapResult(usuari, mapa)+ "punts");
    }

    private static void insertarResultado() {
        System.out.println("Insertar ID del mapa");
        String idmapa = myScanner.next();
        Mapa mapa = null;
        try {
            mapa = ControladorMapa.getMapa(idmapa);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("Insertar ID del jugador");
        //String iduser = myScanner.next();
        Usuari usuari = ControladorUsuari.insertarUsuari("enric", "hola");
        System.out.println("Insertar puntuacion");
        int puntuacion = myScanner.nextInt();
        ControladorResultat.insertarResultat(usuari, mapa, puntuacion);
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
