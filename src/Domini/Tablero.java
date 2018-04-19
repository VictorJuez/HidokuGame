package Domini;

public class Tablero extends Mapa {
    private boolean adyacenciaAngulos;
    private int filas;
    private int columnas;
    private String[][] matrix;

    public Tablero(int filas, int columnas, String[][] tab){
        this.filas = filas;
        this.columnas = columnas;
        this.matrix = tab;
        this.ID = 1;
    }

    public String[][] getMatrix() {
        return matrix;
    }


}
