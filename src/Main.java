import Domini.CtrDomini;
import javafx.util.Pair;

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
                "\t3) Generar hidato\n"+
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
                case "3":
                    generarHidato();
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
        System.out.println("TableroCuadrado:");
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) {
                System.out.print(matrix[i][j]);
                if(j!=columnas-1) System.out.print(",");
            }
            System.out.print("\n");
        }
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
        String[][] tab = new String[filas][columnas];

        for (int i = 0; i < filas; ++i) {
            String filaTab = myScanner.next();
            List<String> items = Arrays.asList(filaTab.split("\\s*,\\s*"));
            tab[i] = items.toArray(tab[i]);
        }

        String[][] t = ctDomini.insertarHidato(index[0], index[1], filas, columnas, tab);
        System.out.println("topologia: "+index[0]);
        System.out.println("Angulos: "+index[1]);
        printTablero(t);
    }

    public static void listaHidatos(){
        List l = ctDomini.listaHidatos();
        for(int i=0; i<l.size(); ++i) {
            System.out.println("ID: "+l.get(i));
            }
        }

    public static void generarHidato(){
        Pair<String[], String[][]> tab = ctDomini.generarHidato();
        String[] index = tab.getKey();
        for(int i=0; i<index.length; ++i) System.out.print(index[i]);
        System.out.println();

        String[][] t = ctDomini.insertarHidato(index[0], index[1], Integer.parseInt(index[2]), Integer.parseInt(index[3]), tab.getValue());
        printTablero(tab.getValue());
    }
    }
