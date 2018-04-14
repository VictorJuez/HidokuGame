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
        //this.matrix = new char[filas][columnas];

        /*for(int i = 0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) matrix[i][j]= 'c';
        }*/

        //System.out.println(matrix);
    }

    public void print(){
        System.out.println("filas: "+filas);
        System.out.println("columnas: "+columnas);
        System.out.println("Tablero:");
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) System.out.print(matrix[i][j]);
            System.out.print("\n");
        }
    }

}
