import Domini.CtrDomini;
import Domini.Mapa;

import java.util.Arrays;
import java.util.HashMap;
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
                "\t4) Comprovar si existe solucion hidato\n"+
                "\t5) Comprovar solucion hidato\n"+
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
                case "4":
                    System.out.println("Inserta el ID del hidato a validar");
                    op = myScanner.next();
                    validarHidato(op);
                    break;
                case "5":
                    System.out.println("Inserta el ID del hidato a comprobar");
                    op = myScanner.next();
                    comprobarHidato(op);
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
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) {
                System.out.print(matrix[i][j]);
                if(j!=columnas-1) System.out.print(",");
            }
            System.out.print("\n");
        }
        System.out.println();
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

        Mapa m = ctDomini.insertarHidato(index[0], index[1], filas, columnas, tab);
        System.out.println("ID: "+m.getID());
        System.out.println(m.getTipo() + "," + m.getAngulos() + "," + m.getFilas() + "," + m.getColumnas());
        printTablero(m.getMatrix());
    }

    public static void listaHidatos(){
        HashMap<String, Mapa> l = ctDomini.getMapasMap();
        l.forEach((k,v) -> {
            System.out.println("ID: "+ k);
            System.out.println(v.getTipo() + "," + v.getAngulos() + "," + v.getFilas() + "," + v.getColumnas());
            printTablero(v.getMatrix());
        });
    }

    public static void generarHidato(){
        Mapa m = ctDomini.generarHidato();
        System.out.println("ID: "+m.getID());
        System.out.println(m.getTipo() + "," + m.getAngulos() + "," + m.getFilas() + "," + m.getColumnas());

        //String[][] t = ctDomini.insertarHidato(index[0], index[1], Integer.parseInt(index[2]), Integer.parseInt(index[3]), tab.getValue());
        printTablero(m.getMatrix());
     }

     public static void validarHidato(String ID){
        HashMap l = ctDomini.getMapasMap();
        Mapa m = (Mapa) l.get(ID);
        if(m.hidatoValido()){
            System.out.println("Té solucio:");
            System.out.println("ID: "+m.getID());
            System.out.println(m.getTipo() + "," + m.getAngulos() + "," + m.getFilas() + "," + m.getColumnas());
            printTablero(m.getSolutionMatrix());
        }
        else System.out.println("No té solucio");
        //m.hidatoValido();
     }

     public static void comprobarHidato(String ID){
         HashMap l = ctDomini.getMapasMap();
         Mapa m = (Mapa) l.get(ID);
         if(m.matriuCorrecte()){
             System.out.println("Solucio correcte!");
             printTablero(m.getSolutionMatrix());
         }
         else System.out.println("Solucio incorrecte");
         //m.hidatoValido();
     }
    }
