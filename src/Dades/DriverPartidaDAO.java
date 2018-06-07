////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Dades;

import java.io.IOException;
import java.util.Scanner;

public class DriverPartidaDAO {
    private static Scanner myScanner;

    public static void main(String[] args) throws IOException {
        String introduction = "Introduce qué operación desea ejecutar:\n"+
                "\t1) LoadPartida(ID)\n"+
                "\t2) LoadAllPartidas()\n"+
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
                    PartidaDAO.loadPartida(op);
                    break;
                case "2":
                    PartidaDAO.loadAllPartidas();
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
