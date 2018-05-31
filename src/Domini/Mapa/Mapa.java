package Domini.Mapa;

import java.util.Collections;
import java.util.UUID;
import java.util.Vector;

import static java.util.Collections.sort;


public abstract class Mapa {
    protected String ID;
    protected String name;
    protected int filas;
    protected int columnas;
    protected int interrogants;
    protected int numeros;
    protected String tipo;
    protected String angulos;
    protected Vector<Integer> numerosExistents;
    protected Vector<Integer> numerosRestants;
    protected String[][] matrix;
    protected boolean solucio;

    public class adyacencias{
        private String valor;
        boolean visitat;
        private Integer x;
        private Integer y;
        private Integer z;
        private Vector<Integer> ad;

        public adyacencias(int y, int x, String valor){
            this.y = y;
            this.x = x;
            this.z = x + y*columnas;
            this.valor = valor;
            this.visitat = false;
            this.ad = new Vector<>();
        }

        public Integer getZ() {
            return z;
        }
        public Integer getX() {
            return x;
        }
        public Integer getY() { return y; }


        public Vector<Integer> getAd() {
            return ad;
        }

        public void add(Integer i) {
            ad.add(i);
        }

        public String getValor() {
            return valor;
        }
        public void setValorAdyacencia(String value){
            this.valor = value;
        }
    };

    protected void putValorV(int value){
        numerosExistents.add(value);
        sort(numerosExistents);
        numerosRestants.remove(value);
    }
    protected void borrarV (int value){
        numerosRestants.add(value);
        numerosExistents.remove(value);
    }

    protected Vector <adyacencias> tablaAD = new Vector<>();
    public void setValorTabla(int pos, String valor){
        tablaAD.get(pos).setValorAdyacencia(valor);
    }
    protected abstract Vector<adyacencias> calculoAdyacencias();

    public Mapa(String[][] matrix) {
        this.matrix = matrix;
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
        this.ID = UUID.randomUUID().toString();
        this.solucio = false;
        inicialitzaTabla();
        tablaAD = calculoAdyacencias();
        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants();
        numeros = numerosExistents.size();
    }
    public Mapa(){
        this.ID = UUID.randomUUID().toString();
        this.solucio = false;
    }
    public Mapa(String ID, String name, String[][] matrix){
        this.matrix = matrix;
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
        this.ID = ID;
        this.name = name;
        this.solucio = false;
        inicialitzaTabla();
        tablaAD = calculoAdyacencias();
        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants();
        numeros = numerosExistents.size();
    }

    public Vector<adyacencias> getTablaAD() {
        if(tablaAD.size() == 0)tablaAD = calculoAdyacencias();
        return tablaAD;
    }
    public String getID() {
        return ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
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
    public int getNumeros() {
        return numeros;
    }
    public void insertarNumero(int numero, int x, int y){
        matrix[x][y] = String.valueOf(numero);
    }
    public void borrarNumero(int x, int y){
        matrix[x][y] = "?";
    }

    @Override
    public boolean equals(Object obj){
        if (this==obj) return true;
        if (this == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Mapa m = (Mapa) obj;
        return this.ID.equals(m.getID());
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants();
        numeros = numerosExistents.size();
        inicialitzaTabla();
        tablaAD = calculoAdyacencias();
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
        sort(existents);
        return existents;
    }


    public int getInterrogants(){
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

    public abstract Integer[][] pathFinder(int casillas_validas, int numero_fil, int numero_col);
    //public abstract boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v);

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
    public Vector<Integer> getNumerosRestants(){   //aixo es podria guardar tot com si fos un atribut
        if(numerosExistents == null) numerosExistents = getNumerosExistents();
        Vector<Integer> total = new Vector<>();
        for(int k = 0; k < interrogants + numerosExistents.size(); k++){
            if (!numerosExistents.contains(k+1)) total.add(k+1);
        }
        return total;
    }

    void inicialitzaTabla(){
        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++){
                if(!matrix[i][j].equals("#") && !matrix[i][j].equals("*")){
                    adyacencias a = new adyacencias(i,j, matrix[i][j]);
                    tablaAD.add(a);
                }
            }
        }
    }
}
