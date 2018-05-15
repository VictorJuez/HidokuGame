package Domini;

import java.util.UUID;
import java.util.Vector;

public abstract class Mapa {
    protected String ID;
    protected int filas;
    protected int columnas;
    protected int interrogants;
    protected int numeros;
    protected String tipo;
    protected String angulos;
    protected Vector<Integer> numerosExistents;
    protected Vector<Integer> numerosRestants;
    protected String[][] matrix;
    protected String[][] solutionMatrix;
    protected boolean solucio;

    public Mapa (int filas, int columnas, String[][] tab){
        this.matrix = tab;
        this.filas = filas;
        this.columnas = columnas;
        this.ID = UUID.randomUUID().toString();
        this.solucio = false;

        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants(matrix);
        numeros = numerosExistents.size();
    }

    public Mapa (int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;
        this.ID = UUID.randomUUID().toString();
        this.solucio = false;
    }

    public String getID() {
        return ID;
    }
    public int getFilas() {
        return filas;
    }
    public int getColumnas() {
        return columnas;
    }
    public String getTipo() {
        return tipo;
    }
    public String getAngulos() {
        return angulos;
    }
    public boolean isSolucio() {
        return solucio;
    }
    public String[][] getMatrix() {
        return matrix;
    }
    public String[][] getSolutionMatrix() {
        return solutionMatrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants(matrix);
        numeros = numerosExistents.size();
    }
    public Vector<Integer> getNumerosExistents(){
        Vector<Integer> existents = new Vector<>();   //numeros que existeixen a la matrix
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!matrix[i][j].equals("#") && !matrix[i][j].equals("*") && !matrix[i][j].equals("-2") && !matrix[i][j].equals("?")){
                    existents.add(Integer.valueOf(matrix[i][j]));
                }
            }
        }
        return existents;
    }
    public int getInterrogants(String[][] matrix){
        int interrogants = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matrix[i][j].equals("?")) {
                    interrogants += 1;
                }
            }
        }
        return interrogants;
    }
    public boolean hidatoValido(){
        solutionMatrix = new String[filas][columnas];
        String[][] B = matrix;
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) solutionMatrix[i][j] = B[i][j];
        }
        Vector<Integer> v;
        v = getNumerosRestants();
        backtrackingResolucio(solutionMatrix, v);
        return this.solucio;
    }

    public abstract Integer[][] pathFinder(int casillas_validas, int numero_fil, int numero_col);
    public abstract boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v);
    public abstract boolean matriuCorrecte();

    protected boolean isInteger(String s) {
        try
        {
            Integer.parseInt(s);
            // s is a valid integer
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
    protected boolean casillaValida(int i, int j, int num_filas, int num_col, Integer[][] casillas_visitadas) {
        if (i < (num_filas - 1) && j < (num_col - 1) && i > 0 && j > 0) {
            if (casillas_visitadas[i][j] == -1) return true;
        }
        return false;
    }
    protected Vector<Integer> getNumerosRestants(){   //aixo es podria guardar tot com si fos un atribut
        if(numerosExistents == null) numerosExistents = getNumerosExistents();
        Vector<Integer> total = new Vector<>();
        for(int k = 0; k < interrogants + numerosExistents.size(); k++){
            if (!numerosExistents.contains(k+1)) total.add(k+1);
        }
        return total;
    }
    protected boolean backtrackingResolucio(String[][] A, Vector v){
        if(v.size() == 0) {
            if (getInterrogants(A) == 0) this.solucio=true;
            return true;
        }
        else{
            boolean b = false;
            for(int i=0; i<A.length && !b; ++i) {
                for(int j=0; j<A[0].length; ++j) {
                    if (A[i][j].equals("?")) {
                        int aux = (int) v.get(0);
                        if (posicioCorrecte(i, j, A, aux, v)) {
                            A[i][j] = String.valueOf(aux);
                            //imprimirMatriu(A);
                            v.remove(0);
                            b = backtrackingResolucio(A, v);
                            if(b) return true;
                            v.add(0, aux);
                            A[i][j] = "?";
                        }
                    }
                }
            }
        }
        return false;
    }
}
