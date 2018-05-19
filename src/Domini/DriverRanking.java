package Domini;

import java.io.IOException;
import java.util.Scanner;

public class DriverRanking {
    private static Scanner myScanner;
    private static ControladorResultat ctResultat = new ControladorResultat();
    private static ControladorMapa ctMapa = new ControladorMapa();
    private static ControladorUsuari ctUsuari = new ControladorUsuari();

    public static void main(String[] args) throws IOException {
        System.out.println("Hidato Game");
        String introduction = "Introduce qué operación desea ejecutar:\n"+
                "\t1)Insertar resultado partida \n"+
                "\t2)Obtener resultado partida\n"+
                "\t3)Obtener ranking global\n"+
                "\t4)Obtener ranking de un mapa\n"+
                "\t5)Obtener resultados de un usuario\n"+
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
                    System.out.println("Insertar ID del jugador");
                    String iduser = myScanner.next();
                    Usuari usuari = ctUsuari.getUsuari(iduser);
                    System.out.println("Insertar puntuacion");
                    int puntuacion = myScanner.nextInt();
                    ctResultat.insertarResultat(usuari, mapa, puntuacion);
                    break;
                case "2":
                    System.out.println("Insertar ID del mapa");
                    idmapa = myScanner.next();
                    mapa = ctMapa.getMapa(idmapa);
                    System.out.println("Insertar ID del jugador");
                    iduser = myScanner.next();
                    usuari = ctUsuari.getUsuari(iduser);
                    System.out.println("puntuacio en el mapa: "+ mapa.getID());
                    System.out.println(ctResultat.getUserMapResult(usuari, mapa)+ "punts");
                    break;
                case "3":

                    break;

                case "4":

                    break;
                case "5":

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
}
