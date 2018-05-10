package Domini;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DriverTriangulos {
    private static Scanner myScanner;

    public static void main(String[] args) {
        System.out.println("Hidato Game");
        String introduction = "Introduce qué operación desea ejecutar:\n"+
                "\t1) validar hidato\n"+
                "\t2) comprobar resolucion hidato\n";

        System.out.println(introduction);
        myScanner = new Scanner(System.in);
        String op = " ";
        op = myScanner.next();
        switch (op){
            case "1":
                validarHidato();
                break;
            case "2":
                resolverHidato();
                break;
        }
    }

    public static TableroTriangular insertarHidato(){
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
        TableroTriangular m = null;
        if(angulos.equals("CA")) m = new TableroTriangularAngulos(filas,columnas,tab);
        else m = new TableroTriangular(filas,columnas,tab);
        //END CANVIS

        return m;
    }

    public static void resolverHidato(){
        TableroTriangular t = insertarHidato();
        t.hidatoValido();
        printTablero(t.getMatrix());
    }

    public static void validarHidato(){
        TableroTriangular t = insertarHidato();
        if(t.matriuCorrecte()) System.out.println("\nLa solució és correcte");
        else System.out.println("\nLa solució és incorrecte");
    }

    public static void printTablero(String[][] matrix){
        int filas = matrix.length;
        int columnas = matrix[0].length;

        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) {
                System.out.print(matrix[i][j]);
                if(matrix[i][j].length() == 1) System.out.print(" ");
                if(j!=columnas-1) System.out.print(" ");
            }
            System.out.print("\n");
        }
    }
}
