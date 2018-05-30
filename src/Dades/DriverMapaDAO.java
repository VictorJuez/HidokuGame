package Dades;

import java.io.IOException;
import java.util.Scanner;

public class DriverMapaDAO {
    private static Scanner myScanner;
    private static MapaDAO mdao = new MapaDAO();
    public static void main(String[] args) throws IOException {
        String introduction = "Introduce qué operación desea ejecutar:\n"+
                "\t1) LoadMapa(ID)\n"+
                "\t2) LoadAllMaps()\n"+
                "\tx) Para salir del juego\n";
        System.out.println(introduction);
        String op = "";
        myScanner = new Scanner(System.in);
        op = myScanner.next();

        boolean active = true;
        while(active) {
            switch (op){
                case "1":
                    op = myScanner.next();
                    mdao.loadMapa(op);
                    break;
                case "2":
                    mdao.loadAllMapas();
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
