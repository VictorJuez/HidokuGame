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

    /**
     * Calcula la tabla de adyacencias y la retorna
     * @return Vector con las adyacencias
     */
    public Vector<adyacencias> getTablaAD() {
        if(tablaAD.size() == 0){
            tablaAD.removeAllElements();
            inicialitzaTabla();
            calculoAdyacencias();
        }
        return tablaAD;
    }

    /**
     * Obtener el id del Mapa
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Setter del nombre del mapa
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtener el nombre del mapa
     * @return nombre
     */
    public String getName() {
        return name;
    }

    /**
     * Obtener el numero de filas del mapa
     * @return numero de filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Obtener el numero de columnas del mapa
     * @return numero de columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Obtener la topologia del mapa
     * @return topologia
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtener el tipo de adyacencias del mapa
     * @return String indicando las adyacencias
     */
    public String getAngulos() {
        return angulos;
    }

    /**
     * Obtener la matriz del mapa
     * @return String[][] de la matriz del mapa
     */
    public String[][] getMatrix() {
        return matrix;
    }

    /**
     * Obtener la cantidad de numeros que hay puestos en el mapa
     * @return entero con la cantidad de numeros
     */
    public int getNumeros() {
        numerosExistents = getNumerosExistents();
        numeros = numerosExistents.size();
        return numeros;
    }

    /**
     * Insertar un numero en el mapa
     * @param numero
     * @param x
     * @param y
     */
    public void insertarNumero(int numero, int x, int y){
        matrix[x][y] = String.valueOf(numero);
        actualizaAdyacencias();
    }

    /**
     * Eliminar un numero insertado en el mapa y ponerlo como ?
     * @param x
     * @param y
     */
    public void borrarNumero(int x, int y){
        matrix[x][y] = "?";
        actualizaAdyacencias();
    }

    /**
     * Obtener la dificultad del hidato
     * @return
     */
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

    /**
     * Set de la matriz del mapa
     * @param matrix
     */
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
                if (Character.isDigit(matrix[i][j].charAt(0))){
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

    /**
     * Rellena casillas_validas con ?, * i # para obtener la matriz del mapa
     * @param casillas_validas
     * @param numero_fil
     * @param numero_col
     * @return matriz en formato Integer[][]
     */
    public abstract Integer[][] pathFinder(int casillas_validas, int numero_fil, int numero_col);

    /**
     * Comprueba que la posicion i,j sea correcta dentro del mapa.
     * @param i
     * @param j
     * @param num_filas
     * @param num_col
     * @param casillas_visitadas
     * @return true si es correcta, false si no
     */
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

    /**
     * Comprueba que cuando se inserta un nuevo hidato tenga la primera y ultima posicion puesta
     * @return true si tiene la primera y ultima posicion puestas, false si no
     */
    public boolean matriuBenInicialitzada() {
        if(numerosExistents.contains(1) && numerosExistents.contains(numeros+interrogants)) return true;
        return false;
    }
}
