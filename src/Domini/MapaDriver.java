package Domini;

import javafx.util.Pair;

public class MapaDriver {
    public static void main(String[] args) {
        generarHidato();

    }

    public static void generarHidato(){
        Mapa m = new Mapa();
        Pair<String[], String[][]> tab = m.generarHidato();
        String[] index = tab.getKey();

        //String[][] t = m.insertarHidato(index[0], index[1], Integer.parseInt(index[2]), Integer.parseInt(index[3]), tab.getValue());
        printTablero(index, tab.getValue());
    }

    public static void printTablero(String[] index, String[][] matrix){
        int filas = matrix.length;
        int columnas = matrix[0].length;
        System.out.println("Mapa generado aleatoriamente:\n");

        for(int i=0; i<index.length; ++i) {
            System.out.print(index[i]);
            if(i!=index.length-1) System.out.print(",");
        }
        System.out.println();

        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) {
                System.out.print(matrix[i][j]);
                if(j!=columnas-1) System.out.print(",");
            }
            System.out.print("\n");
        }
    }

}
