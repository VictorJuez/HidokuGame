package Domini;

import javafx.util.Pair;

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
    private Integer actual;
    private Vector v;
    private Integer posicio;

    protected class adyacencias{
        private String valor;
        boolean visitat;
        private Integer x;
        private Integer y;
        private Integer z;
        private Vector<Integer> ad = new Vector<>();

        public adyacencias(int y, int x, Vector<Integer> v){
            this.y = y;
            this.x = x;
            this.z = x + y*columnas;
            this.ad = v;
        }

        public Integer getZ() {
            return z;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }

        public Vector<Integer> getAd() {
            return ad;
        }
    };
    protected Vector <adyacencias> tablaAD = new Vector<>();

    protected abstract Vector<adyacencias> calculoAdyacencias();

    public Mapa(String[][] matrix) {
        this.matrix = matrix;
        this.solutionMatrix = copyMatrix(matrix);
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
        this.ID = UUID.randomUUID().toString();
        this.solucio = false;

        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants(this.matrix);
        numeros = numerosExistents.size();
    }
    public Mapa(){
        this.ID = UUID.randomUUID().toString();
        this.solucio = false;
    }
    public Mapa(String ID, String[][] matrix){
        this.matrix = matrix;
        this.solutionMatrix = copyMatrix(matrix);
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
        this.ID = ID;
        this.solucio = false;

        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants(this.matrix);
        numeros = numerosExistents.size();
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
    public void insertarNumero(int numero, int x, int y){
        solutionMatrix[x][y] = String.valueOf(numero);
    }
    public void borrarNumero(int x, int y){
        solutionMatrix[x][y] = "?";
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
        this.solutionMatrix = copyMatrix(matrix);
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
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
        Vector<Integer> v;
        v = getNumerosRestants();
        backtrackingResolucio(solutionMatrix, v);
        return this.solucio;
    }

    private String[][] copyMatrix(String[][] matrix){
        solutionMatrix = new String[filas][columnas];
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) solutionMatrix[i][j] = matrix[i][j];
        }
        return solutionMatrix;
    }

    public abstract Integer[][] pathFinder(int casillas_validas, int numero_fil, int numero_col);
    //public abstract boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v);
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
    protected Integer busca(String valor){       //et retorna la posicio don es troba el valor a la taula d'adjacencies
        for (int i = 0; i < tablaAD.size(); i++){
            if(tablaAD.get(i).valor.equals(valor)){
                return i;
            }
        }
        return -1;
    }


    protected boolean backtrackingResolucio(String[][] A, Vector v) {
        calculoAdyacencias();
        String[][] matrixaux;
        matrixaux = copyMatrix(matrix);
        return inner_backtrackingResolucio(matrixaux, v, 1, busca(Integer.toString(1)) );
    }

    protected Vector < Vector<Integer> > calculCamins(Integer actual, Integer posicio, Integer distancia, Vector<Integer> v){


    }

    protected Integer calculDistancia(Integer actual, Vector<Integer> v){   //en principi actual nomes et poden donar valors de v, el numero 1 o lultim numero (not sure daixo ultim)
        if(!v.isEmpty()) {
            if (v.contains(actual)) {
                if (actual == v.lastElement()) return numeros + interrogants - v.lastElement();
                else {
                    for (int i = 0; i < v.size() -1; i++) {
                        if (v.get(i) == actual) return v.get(i+1) - v.get(i);
                    }
                }
            } else {
                return v.firstElement() - 1;      //es el valor 1, en principi nomes entra si es un 1;
            }
        }
        return numeros + interrogants; //cas en que tot son interrogants i v esta buit
    }


    protected boolean inner_backtrackingResolucio(String[][] m, Vector v, Integer actual, Integer posicio){      //suposem que v esta ordenat
        Vector < Vector <Integer> > pos = new Vector<>();       //aqui guardare tots els camins posibles
        if (1 == numeros + interrogants) return true;
        else{

            Integer distancia = calculDistancia(actual, v);
            pos = calculCamins(actual, posicio, distancia, v);

            for (int i = 0; i < pos.size(); i++){
                for (int k = 0; k < pos.get(i).size(); k++){
                    tablaAD.get(pos.get(i).get(k)).visitat = true;
                }
                //crida al backtracking
                for (int k = 0; k < pos.get(i).size(); k++){
                    tablaAD.get(pos.get(i).get(k)).visitat = false;
                }
            }

        }
        return false;

    }
}
