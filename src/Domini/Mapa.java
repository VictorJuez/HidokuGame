package Domini;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Mapa {
    protected String ID;
    protected static List<String> instances = new ArrayList();
    protected int filas;
    protected int columnas;
    protected int interrogants;
    protected int numeros;
    protected boolean teSolucio;
    protected String[][] matrix;

    public Mapa(){}

    /**
     *  Devuelve el atributo ID del mapa
     * @return un String con el ID del mapa
     */
    public String getID() {
        return ID;
    }

    /**
     *  Devuelve una Lista con el ID de todas las instancias de Mapa
     * @return Lista de ID's
     */
    public static List getInstances() {
        return instances;
    }

    /**
     *  Devuelve el hidato con una matriz de Strings
     * @return matriz de Strings
     */
    public String[][] getMatrix() {
        return matrix;
    }

    /**
     *  Devuelve quales son los numeros que estan puestos en el hidato (matrix).
     * @return Vector de Integers con los numeros ya existentes
     */
    protected Vector<String> numerosExistents(){
        Vector<String> existents = new Vector<>();   //numeros que existeixen a la matrix
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matrix[i][j].equals("?")) {
                    interrogants += 1;
                }
                else if (!matrix[i][j].equals("#") && !matrix[i][j].equals("*") && !matrix[i][j].equals("-2")){
                    existents.add(matrix[i][j]);
                }
            }
        }
        numeros = existents.size();
        return existents;
    }

    /**
     *  Calcula y devuelve quales son los numeros que faltan por poner en el hidato (matrix) para resolverlo.
     * @return Vector de Integers con los numeros restantes
     */
    protected Vector<Integer> numerosRestants(){   //aixo es podria guardar tot com si fos un atribut
        Vector<String> existents = numerosExistents();
        Vector<Integer> total = new Vector<>();
        for(int k = 0; k < interrogants + existents.size(); k++){
            if (!existents.contains(Integer.toString(k+1))) total.add(k+1);
        }
        return total;
    }

    /**
     *  Comprueba si un String es un entero o no.
     * @param s El String a comprobar
     * @return Vector de Integers con los numeros restantes
     */
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

    /**
     *  Comprueba si existe solucion para el hidato (matrix).
     *
     */
    public void hidatoValido(){
        String[][] A = matrix;
        Vector<Integer> v;
        v = numerosRestants();
        backtrackingResolucio(A, v);
        if(this.teSolucio) System.out.println("\nTE SOLUCIO:");
        else System.out.println("\nNO TE SOLUCIO");
    }

    /**
     * Dado un hidato y su vector de numeros restantes comprueba si existe solucion.
     * @param A El hidato a comprobar
     * @param v El vector de numerosRestantes
     * @return Boolean indicando si existe solucion.
     */
    protected boolean backtrackingResolucio(String[][] A, Vector v){
        //if(k>=filas*columnas) return;
        //System.out.println(v);
        if(v.size() == 0) {
            this.teSolucio = true;
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

    /**
     * Calcula para una casilla "?" a rellenar del hidato, si se le puede poner el numero restante: toInsert
     * @param x,y fila y columna de la casilla "?"
     * @param A El hidato a comprobar
     * @param toInsert El numero restante a comprobar
     * @param v El vector de numeros restantes
     * @return Boolean indicando si se puede poner el numero o no en la casilla.
     */
    protected boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v){return false;}

    /**
     * Establece si la posicion del hidato i,j debe ser un # o no.
     * @param i,j fila y columna de la casilla a comprobar
     * @param tablero matriz con el hidato
     * @param max_fil El numero de filas del hidato
     * @param max_col El numero de columnas del hidato
     * @return Boolean indicando si la casilla sera # o no.
     */
    protected boolean holeChecker(String[][] tablero, int i, int j, int max_fil, int max_col)
    {
        int randValue = ThreadLocalRandom.current().nextInt(0, 100+1);
        //ahora vamos a ver si la casilla está en el borde.
        if (i == 0 || j == 0 || i == max_fil - 1 || j == max_col - 1) if (randValue > 55) return true;
        else if ((i == 0 && j == max_col - 1) || (i == max_fil - 1 && j == max_col - 1)
                || (i == max_fil - 1 && j == 0) || (i == 0 && j == 0)) if (randValue > 55) return true;
        //else if ((i < max_fil - 1 && j < max_col - 1) && tablero[i-1][j] == "#" || tablero[i][j+1] == "#" || tablero[i+1][j] == "#" || tablero[i][j-1] == "#")
        //if (randValue > 55) return true;
        return false;
    }

    /**
     * Comprueba si la casilla (i,j) está dentro del perimetro del hidato.
     * @param i,j fila y columna de la casilla a comprobar
     * @param num_filas El numero de filas del hidato
     * @param num_col El numero de columnas del hidato
     * @param casillas_visitadas matriz de enteros con informacion de las casillas visitadas.
     * @return Boolean indicando si la casilla es valida o no.
     */
    protected boolean casillaValida(int i, int j, int num_filas, int num_col, Integer[][] casillas_visitadas) {
        if (i < (num_filas - 1) && j < (num_col - 1) && i > 0 && j > 0) {
            if (casillas_visitadas[i][j] == -1) return true;
        }
        return false;
    }


    /**
     * Genera un hidato de cualquier tipo y adyacencia
     * @return un Pair donde:<br>
     *     <ul>
     *         <li>su primera posicion contiene un array con el índice del hidato, es decir [Topologia,Adyacencia,numeroFilas,numeroColumnas]
     *         del hidato generado</li>
     *         <li>su segunda posicion contiene una matriz de Strings con el hidato generado</li>
     *     </ul>
     */
    public Pair<String[], String[][]> generarHidato() {
        int numero_fil, numero_col; //número de filas y columnas del tablero
        int tipo_adyacencia; //1 -> costados, 2 -> costados y angulos
        int topologia; //1-> cuadrados, 2-> triangilos, 3-> hexagonos
        int num_casillas; //auxiliar para el pathfinder
        int casillas_validas; //casillas que contarán para la solución del Hidato.
        String[][] tablero;


        //----------------------------DEFINICIÓN DE LOS PARÁMETROS DEL HIDATO:
        numero_fil = ThreadLocalRandom.current().nextInt(5, 10 + 1);
        numero_col = ThreadLocalRandom.current().nextInt(5, 10 + 1);

        tipo_adyacencia = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        topologia = ThreadLocalRandom.current().nextInt(1, 3+1);

        num_casillas = numero_fil * numero_col;

        //tendrá un valor entre un cuarto del número de casillas y 3 cuartos.
        casillas_validas = ThreadLocalRandom.current().nextInt(num_casillas / 5, (num_casillas / 5) * 2);

        tablero = new String[numero_fil][numero_col];
        Integer[][] casillas_usadas = new Integer[numero_fil][numero_col];

        /*System.out.print("Numero de filas: ");
        System.out.println(numero_fil);
        System.out.print("Numero de columnas: ");
        System.out.println(numero_col);
        System.out.print("La topologia es");
        System.out.println(topologia);
        System.out.print("El tipo de adyacencia es: ");
        System.out.println(tipo_adyacencia);
        */

        //----------------------------DEFINICIÓN DEL HIDATO:
        //los while sirven para saber si se ha generado bien o se ha encerrado solo y no ha podido generar el hidato.
        switch (topologia) {
            case (1):
                TableroCuadrado t= new TableroCuadrado(numero_fil, numero_col, tablero);
                if(tipo_adyacencia == 2)t = new TableroCuadradoAngulos(numero_fil, numero_col, tablero);
                casillas_usadas = t.pathFinder(casillas_validas, numero_fil, numero_col);
                while (casillas_usadas[0][0] == -5) casillas_usadas = t.pathFinder(casillas_validas, numero_fil, numero_col);
                break;
            case (2):
                TableroTriangular t2 = new TableroTriangular(numero_fil, numero_col, tablero);
                if(tipo_adyacencia == 2)t2 = new TableroTriangularAngulos(numero_fil, numero_col, tablero);
                casillas_usadas = t2.pathFinder(casillas_validas, numero_fil, numero_col);
                while (casillas_usadas[0][0] == -5) casillas_usadas = t2.pathFinder(casillas_validas, numero_fil, numero_col);
                break;
            case (3):
                TableroHexagonal t3 = new TableroHexagonal(numero_fil, numero_col, tablero);
                casillas_usadas = t3.pathFinder(casillas_validas, numero_fil, numero_col);
                while (casillas_usadas[0][0] == -5) casillas_usadas = t3.pathFinder(casillas_validas, numero_fil, numero_col);
                break;
        }

        int randValue;
        //para tratar el array
        for (int i = 0; i < numero_fil; ++i) {
            for (int j = 0; j < numero_col; ++j) {
                if (casillas_usadas[i][j] == -2) tablero[i][j] = "#";
                else if (casillas_usadas[i][j] != -1) {
                    randValue = ThreadLocalRandom.current().nextInt(0, 100 + 1);
                    if (randValue > 85) tablero[i][j] = String.valueOf(casillas_usadas[i][j]); //85
                    else tablero[i][j] = "?";
                }
                else
                {
                    if (holeChecker(tablero, i, j, numero_fil, numero_col)) tablero[i][j] = "#";
                    else tablero[i][j] = "*";
                }
            }
        }

        Pair<String[], String[][]> result = new Pair<>(new String[4], new String[tablero.length][tablero[0].length]);
        switch (topologia)
        {
            case (1):
                if (tipo_adyacencia == 1)
                {
                    result = new Pair<>(new String[]{"Q","C",Integer.toString(tablero.length), Integer.toString(tablero[0].length)}, tablero);
                } else result = new Pair<>(new String[]{"Q","CA",Integer.toString(tablero.length), Integer.toString(tablero[0].length)}, tablero);
                break;
            case (2):
                if (tipo_adyacencia == 1)
                {
                    result = new Pair<>(new String[]{"T","C",Integer.toString(tablero.length), Integer.toString(tablero[0].length)}, tablero);
                } else result = new Pair<>(new String[]{"T","CA",Integer.toString(tablero.length), Integer.toString(tablero[0].length)}, tablero);
                break;
            case (3):
                if (tipo_adyacencia == 1)
                {
                    result = new Pair<>(new String[]{"H","C",Integer.toString(tablero.length), Integer.toString(tablero[0].length)}, tablero);
                } else result = new Pair<>(new String[]{"H","CA",Integer.toString(tablero.length), Integer.toString(tablero[0].length)}, tablero);
                break;
        }
        return result;
    }

    /**
     * Comprueba si el hidato (matrix) ya resuelto esta bien resuelto o no.
     * @return Boolean indicando si esta bien resuelto o no.
     */
    public boolean matriuCorrecte(){return false;}

}
