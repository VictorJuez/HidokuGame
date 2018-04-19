import Domini.CtrDomini;
import Domini.Tablero;

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
        Tablero t = ctDomini.insertarHidato(filas,columnas,tab);
        print(t);

    }

    public static void print(Tablero t){
       String[][] matrix = t.getMatrix();

        int filas = matrix.length;
        int columnas = matrix[0].length;
        System.out.println("filas: "+filas);
        System.out.println("columnas: "+columnas);
        System.out.println("Tablero:");
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) System.out.print(matrix[i][j]);
            System.out.print("\n");
        }
    }
}
