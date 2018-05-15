import Domini.*;
import javafx.util.Pair;

import java.util.HashMap;

public class Proves {
    public static void main(String[] args) {
        int contador = 0;
        while (true) {
            ++contador;
            System.out.println("iteraciones: "+contador);
            MapaController mapaController = new MapaController();
            Mapa m = mapaController.generarHidato();
            //System.out.println("ID: "+m.getID());
            //System.out.println(m.getTipo() + "," + m.getAngulos() + "," + m.getFilas() + "," + m.getColumnas());

            validarHidato(m);
            comprobarSolucion(m);
        }
    }

    public static void validarHidato(Mapa m){
        /*if(m.hidatoValido()){
            System.out.println("Té solucio:");
            System.out.println("ID: "+m.getID());
            System.out.println(m.getTipo() + "," + m.getAngulos() + "," + m.getFilas() + "," + m.getColumnas());
            printTablero(m.getSolutionMatrix());
        }*/

        if(!m.hidatoValido()) System.out.println("No té solucio");
    }

    public static void comprobarSolucion(Mapa m){
        MapaFactory mapaFactory = new MapaFactory();
        Mapa m2 = mapaFactory.getMapa(m.getTipo(), m.getAngulos(), m.getFilas(), m.getColumnas(), m.getSolutionMatrix());
        /*if(m2.matriuCorrecte()){
            System.out.println("Solucio correcte!");
        }*/
        if(!m2.matriuCorrecte()) {
            System.out.println("Solucio incorrecte!");
            System.out.println(m.getTipo() + "," + m.getAngulos() + "," + m.getFilas() + "," + m.getColumnas());
            printTablero(m.getSolutionMatrix());
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
}
