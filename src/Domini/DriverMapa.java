package Domini;

import Domini.Mapa.Mapa;
import Domini.Mapa.UtilsMapaDecorator;
import Domini.Mapa.adyacencias;

import java.io.IOException;
import java.util.*;

public class DriverMapa {

    private static Scanner myScanner;

    public static void main(String[] args) throws IOException {
        System.out.println("Hidato Game");
        String introduction = "Introduce qué operación desea ejecutar:\n"+
                "\t1) Insertar un nuevo hidato\n"+
                "\t2) Lista hidatos\n"+
                "\t3) Generar hidato\n"+
                "\t4) Comprovar si existe solucion hidato\n"+
                "\t5) Ver adyacencias\n"+
                "\t6) Save mapa from disk\n"+
                "\t7) Delete mapa\n"+
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
                    System.out.println("Ver adyacencias");
                    op = myScanner.next();
                    adyacencia(op);
                    break;
                case "6":
                    System.out.println("Inserta el ID del hidato a guardar");
                    op = myScanner.next();
                    System.out.println("Inserta el nom per guardar el mapa");
                    String name = myScanner.next();
                    saveMapa(op, name);
                    break;
                case "7":
                    System.out.println("Inserta el ID del hidato a borrar");
                    op = myScanner.next();
                    Mapa mapa = ControladorMapa.getMapa(op);
                    ControladorMapa.borrarMapa(mapa);
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

    private static void saveMapa(String id, String name) {
        Mapa m = ControladorMapa.getMapa(id);
        ControladorMapa.saveMapa(m, name);
    }

    private static void adyacencias(String op) throws IOException {
        Mapa m = ControladorMapa.getMapa(op);
        Vector<adyacencias> k = m.getTablaAD();
        for (int i = 0; i < k.size(); i++){
            System.out.println("("+k.get(i).getY()+") ("+k.get(i).getX()+") z = "+ k.get(i).getZ() + " posicio =" + i);
            for (int j = 0; j < k.get(i).getAd().size(); j++){
                System.out.println("["+k.get(i).getAd().get(j)+"]");
            }
            System.out.println();
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

    public static void insertarHidato() throws IOException {
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

        Mapa m = ControladorMapa.insertarHidato(index[0], index[1], tab);
        System.out.println("ID: "+m.getID());
        System.out.println(m.getTipo() + "," + m.getAngulos() + "," + m.getFilas() + "," + m.getColumnas());
        printTablero(m.getMatrix());
    }

    public static void listaHidatos() throws IOException {
        ArrayList<String> l = ControladorMapa.getAllSavedMaps();
        for(String s : l) {
            Mapa v = ControladorMapa.getMapa(s);
            System.out.println("ID: " + v.getID());
            System.out.println("name: " + v.getName());
            System.out.println(v.getTipo() + "," + v.getAngulos() + "," + v.getFilas() + "," + v.getColumnas());
            printTablero(v.getMatrix());
        }
    }

    public static void generarHidato() throws IOException {
        Mapa m = ControladorMapa.generarHidato();
        System.out.println("ID: "+m.getID());
        System.out.println(m.getTipo() + "," + m.getAngulos() + "," + m.getFilas() + "," + m.getColumnas());

        //String[][] t = ctDomini.insertarHidato(index[0], index[1], Integer.parseInt(index[2]), Integer.parseInt(index[3]), tab.getValue());
        printTablero(m.getMatrix());
     }

     public static void validarHidato(String ID) throws IOException {
         Mapa m = ControladorMapa.getMapa(ID);
         UtilsMapaDecorator um = new UtilsMapaDecorator(m);
         if(um.hidatoValido()){
            System.out.println("Té solucio:");
            System.out.println("ID: "+m.getID());
            System.out.println(m.getTipo() + "," + m.getAngulos() + "," + m.getFilas() + "," + m.getColumnas());
            printTablero(m.getMatrix());
        }
        else System.out.println("No té solucio");
        //m.hidatoValido();
     }

     public static void adyacencia(String ID) throws IOException {
         Mapa m = ControladorMapa.getMapa(ID);
         for(int i = 0; i < m.getTablaAD().size(); i++){
             System.out.println("posicio a la taula: "+ i + " identificador: " + m.getTablaAD().get(i).getZ());
             System.out.println("vector adjacents: "+ m.getTablaAD().get(i).getAd());
         }
     }

     /*public static void loadMapa(String ID) throws IOException, ParseException {
        Mapa m = ControladorMapa.loadMapa(ID);
        System.out.println("ID: "+m.getID());
        System.out.println(m.getTipo() + "," + m.getAngulos() + "," + m.getFilas() + "," + m.getColumnas());
        printTablero(m.getMatrix());
     }*/
    }
