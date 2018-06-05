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
    String dificultat;

    public Vector <adyacencias> tablaAD = new Vector<>();

    /**
     * Calcula la tabla de adyacencias del mapa tablaAD
     */
    protected abstract void calculoAdyacencias();

    public Mapa(String[][] matrix) {
        this.matrix = matrix;
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
        this.ID = UUID.randomUUID().toString();
        this.solucio = false;
        inicialitzaTabla();
        calculoAdyacencias();
        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants();
        numeros = numerosExistents.size();
        this.dificultat = calculoDificultat();
    }
    public Mapa(){
        this.ID = UUID.randomUUID().toString();
        this.solucio = false;
    }

    /**
     * Actualiza la tabla de adyacencias
     */
    public void actualizaAdyacencias(){
        tablaAD.removeAllElements();
        inicialitzaTabla();
        calculoAdyacencias();
    }

    public Mapa(String ID, String name, String[][] matrix){
        this.matrix = matrix;
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
        this.ID = ID;
        this.name = name;
        this.solucio = false;
        inicialitzaTabla();
        calculoAdyacencias();
        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants();
        numeros = numerosExistents.size();
        this.dificultat = calculoDificultat();
    }

    public Vector<adyacencias> getTablaAD() {
        if(tablaAD.size() == 0){
            tablaAD.removeAllElements();
            inicialitzaTabla();
            calculoAdyacencias();
        }
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
        numerosExistents = getNumerosExistents();
        numeros = numerosExistents.size();
        return numeros;
    }
    public void insertarNumero(int numero, int x, int y){
        matrix[x][y] = String.valueOf(numero);
        actualizaAdyacencias();
    }
    public void borrarNumero(int x, int y){
        matrix[x][y] = "?";
        actualizaAdyacencias();
    }
    public String getDificultad(){return dificultat;}

    @Override
    public boolean equals(Object obj){
        if (this==obj) return true;
        if (this == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Mapa m = (Mapa) obj;
        return this.ID.equals(m.getID());
    }

    /**
     * Funcion que calcula la dificultad en funcion de las adyacencias y los diferentes caminos que habra en el hidato. Un camino es un recorrido que va de un numero a otro numero que ya estaba puesto en el mapa original
     * @return Devuelve un String con la dificultad
     */
    private String calculoDificultat(){
        int ad = 0;
        for (int i = 0; i < tablaAD.size(); i++){
            ad = ad+tablaAD.get(i).getAd().size();
        }
        ad = ad/2;
        if (ad <=4 || interrogants-numeros<=7) return "FACIL";
        else if (ad <= 6 || interrogants-numeros <= 14) return "MEDIO";
        else return "DIFICIL";
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
        calculoAdyacencias();
        this.dificultat = calculoDificultat();
    }

    /**
     * Calcula los numeros que estaban puestos en el mapa
     * @return Retorna un vector de enteros con esos numeros
     */
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

    /**
     * Calcula cuantos interrogantes hay en el mapa
     * @return Devuelve un entero que indica la cantidad de interrogantes que hay
     */
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

    /**
     * Calcula los numeros que falta para añadir en el mapa
     * @return  Devuelve un Vector de Integers con esos numeros
     */
    public Vector<Integer> getNumerosRestants(){   //aixo es podria guardar tot com si fos un atribut
        if(numerosExistents == null) numerosExistents = getNumerosExistents();
        Vector<Integer> total = new Vector<>();
        for(int k = 0; k < interrogants + numerosExistents.size(); k++){
            if (!numerosExistents.contains(k+1)) total.add(k+1);
        }
        return total;
    }

    /**
     * Inicializa la tabla de adyacencias del mapa
     */
    void inicialitzaTabla(){
        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++){
                if(!matrix[i][j].equals("#") && !matrix[i][j].equals("*")){
                    adyacencias a = new adyacencias(i,j, matrix[i][j], this.columnas);
                    tablaAD.add(a);
                }
            }
        }
    }
}
