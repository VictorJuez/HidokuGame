package Domini;

import java.util.UUID;

public class Tablero extends Mapa {
    private boolean adyacenciaAngulos;
    private int filas;
    private int columnas;
    private String[][] matrix;

    private Tablero(){
        //Not allowed
    }

    public static Tablero createTablero(int filas, int columnas, String[][] tab){
        Tablero t = new Tablero();
        t.filas = filas;
        t.columnas = columnas;
        t.matrix = tab;
        t.ID = UUID.randomUUID().toString();
        instances.add(t.ID);
        return t;
    }

    public String[][] getMatrix() {
        return matrix;
    }


}
