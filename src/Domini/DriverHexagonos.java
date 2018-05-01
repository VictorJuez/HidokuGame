package Domini;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DriverHexagonos {
    private static Scanner myScanner;
    
    public static void main(String[] args){
        myScanner = new Scanner(System.in);
        insertarHidato();
    }

    public static void insertarHidato(){
        System.out.println("Introduce un hidato válido:");
        String params = "";
        params = myScanner.next();
        String[] index = new String[4];

        List<String> entry = Arrays.asList(params.split("\\s*,\\s*"));
        index = entry.toArray(index);

        int filas = Integer.parseInt(index[2]);
        int columnas = Integer.parseInt(index[3]);
        String angulos = index[1];
        String[][] tab = new String[filas][columnas];

        for (int i = 0; i < filas; ++i) {
            String filaTab = myScanner.next();
            List<String> items = Arrays.asList(filaTab.split("\\s*,\\s*"));
            tab[i] = items.toArray(tab[i]);
        }

        //NOMES S'HA DE CANVIAR AQUESTA PART PER ELS ALTRES DRIVERS
        TableroHexagonal m = new TableroHexagonal(filas,columnas,tab);
        //END CANVIS

        printTablero(tab);
    }

    public static void printTablero(String[][] matrix){
        int filas = matrix.length;
        int columnas = matrix[0].length;
        System.out.println("\nfilas: "+filas);
        System.out.println("columnas: "+columnas);
        System.out.println("TableroCuadrado:");
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) {
                System.out.print(matrix[i][j]);
                if(j!=columnas-1) System.out.print(",");
            }
            System.out.print("\n");
        }
    }
}
