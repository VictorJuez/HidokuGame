import Domini.CtrDomini;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner myScanner;
    private static CtrDomini ctDomini;

    public static void main(String[] args) {
        ctDomini = new CtrDomini();
        System.out.println("Hidato Game");
        String introduction = "Introduce qué operación desea ejecutar:\n"+
                "\t1) Insertar un nuevo hidato\n"+
                "\t2) Lista hidatos\n"+
                "\tx) Para salir del juego\n";

        System.out.println(introduction);
        String op = "";
        myScanner = new Scanner(System.in);
        op = myScanner.next();
        boolean active = true;
        while(active) {
            switch (op){
                case "1":
                    insertarHidato();
                    break;
                case "2":
                    listaHidatos();
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

    public static void printTablero(String[][] matrix){
        int filas = matrix.length;
        int columnas = matrix[0].length;
        System.out.println("\nfilas: "+filas);
        System.out.println("columnas: "+columnas);
        System.out.println("Tablero:");
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) System.out.print(matrix[i][j]);
            System.out.print("\n");
        }
    }

    public static void insertarHidato(){
        System.out.println("Introduce un hidato válido:");
        String params = "";
        params = myScanner.next();
        int filas = Integer.parseInt(String.valueOf(params.charAt(5)));
        int columnas = Integer.parseInt(String.valueOf(params.charAt(7)));

        String[][] tab = new String[filas][columnas];

        for (int i = 0; i < filas; ++i) {
            String filaTab = myScanner.next();
            List<String> items = Arrays.asList(filaTab.split("\\s*,\\s*"));
            tab[i] = items.toArray(tab[i]);
        }

        String[][] t = ctDomini.insertarHidato(filas, columnas, tab);
        printTablero(t);
    }

    public static void listaHidatos(){
        List l = ctDomini.listaHidatos();
        for(int i=0; i<l.size(); ++i) {
            System.out.println("ID: "+l.get(i));
            }
        }
    }
