import Domini.CtrDomini;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Introduce un hidato válido:");
        String params = "";
        Scanner myScanner = new Scanner(System.in);
        params = myScanner.next();
        int filas = Integer.parseInt(String.valueOf(params.charAt(5)));
        int columnas = Integer.parseInt(String.valueOf(params.charAt(7)));

        String[][] tab = new String[filas][columnas];

        for(int i=0; i<filas; ++i) {
            String filaTab = myScanner.next();
            List<String> items = Arrays.asList(filaTab.split("\\s*,\\s*"));
            tab[i] = items.toArray(tab[i]);
        }


        CtrDomini ctDomini = new CtrDomini();
        ctDomini.insertarHidato(filas,columnas,tab);
    }
}
