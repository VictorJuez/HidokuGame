package Domini;

import java.util.Scanner;

public class CtrDomini {

    public CtrDomini() {
    }

    public void helloWorld() {
        System.out.println("Hello World!");
    }

    public void insertarHidato() {
        /*
        * Simplemente creado para testear el siguiente input:
        *   Q,CA,3,4
        *   #,1,?,#
        *   ?,?,?,?
        *   7,?,9,#
        *
        *   lo unico que hace es printarlo.
         */
        String params,tablero = "";
        Scanner myScanner = new Scanner(System.in);
        params = myScanner.next();
        tablero = myScanner.next();
        tablero += myScanner.next();
        tablero += myScanner.next();
        System.out.println(params);
        System.out.println(tablero);
    }

    public void generarHidato(){
        //Mahias aqui es donde puedes poner tu funcion.
    }

}
