package Domini;

import java.util.Scanner;

public class CtrDomini {

    public CtrDomini() {
    }

    public void helloWorld() {
        System.out.println("Hello World!");
    }

    public void insertarHidato() {
        String params,tablero = "";
        Scanner myScanner = new Scanner(System.in);
        params = myScanner.next();
        tablero = myScanner.next();
        tablero += myScanner.next();
        tablero += myScanner.next();
        System.out.println(params);
        System.out.println(tablero);
    }

}
