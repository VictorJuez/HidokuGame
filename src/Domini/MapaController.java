package Domini;

import javafx.util.Pair;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class MapaController {
    MapaI m;

    public MapaController(MapaI m) {
        this.m = m;
    }

    public MapaController(){};

    /**
     *  Comprueba si existe solucion para el hidato (m.getMatrix()).
     *
     */
    public String[][] hidatoValido(){
        String[][] A = new String[m.getFilas()][m.getColumnas()];
        String[][] B = m.getMatrix();
        for(int i=0; i<m.getFilas(); ++i){
            for(int j=0; j<m.getColumnas(); ++j) A[i][j] = B[i][j];
        }
        Vector<Integer> v;
        v = numerosRestants();
        backtrackingResolucio(A, v);
        if(m.teSolucio()) {
            System.out.println("\nTE SOLUCIO:");
            return A;
        }
        else System.out.println("\nNO TE SOLUCIO");
        return null;
    }

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

    protected boolean backtrackingResolucio(String[][] A, Vector v){
        //if(k>=m.getFilas()*m.getColumnas()) return;
        //System.out.println(v);
        if(v.size() == 0) {
            m.setSolucio(true);
            return true;
        }
        else{
            boolean b = false;
            for(int i=0; i<A.length && !b; ++i) {
                for(int j=0; j<A[0].length; ++j) {
                    if (A[i][j].equals("?")) {
                        int aux = (int) v.get(0);
                        if (m.posicioCorrecte(i, j, A, aux, v)) {
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


    protected boolean casillaValida(int i, int j, int num_filas, int num_col, Integer[][] casillas_visitadas) {
        if (i < (num_filas - 1) && j < (num_col - 1) && i > 0 && j > 0) {
            if (casillas_visitadas[i][j] == -1) return true;
        }
        return false;
    }

    /**
     *  Devuelve quales son los numeros que estan puestos en el hidato (m.getMatrix()).
     * @return Vector de Integers con los numeros ya existentes
     */
    protected Vector<String> numerosExistents(){
        Vector<String> existents = new Vector<>();   //numeros que existeixen a la m.getMatrix()
        for (int i = 0; i < m.getFilas(); i++) {
            for (int j = 0; j < m.getColumnas(); j++) {
                if (!m.getMatrix()[i][j].equals("#") && !m.getMatrix()[i][j].equals("*") && !m.getMatrix()[i][j].equals("-2")){
                    existents.add(m.getMatrix()[i][j]);
                }
            }
        }
        return existents;
    }

    public int interrogants(){
        int interrogants = 0;
        for (int i = 0; i < m.getFilas(); i++) {
            for (int j = 0; j < m.getColumnas(); j++) {
                if (m.getMatrix()[i][j].equals("?")) {
                    interrogants += 1;
                }
            }
        }
        return interrogants;
    }

    /**
     *  Calcula y devuelve quales son los numeros que faltan por poner en el hidato (m.getMatrix()) para resolverlo.
     * @return Vector de Integers con los numeros restantes
     */
    protected Vector<Integer> numerosRestants(){   //aixo es podria guardar tot com si fos un atribut
        Vector<String> existents = numerosExistents();
        Vector<Integer> total = new Vector<>();
        for(int k = 0; k < m.getInterrogants() + existents.size(); k++){
            if (!existents.contains(Integer.toString(k+1))) total.add(k+1);
        }
        return total;
    }

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

    protected String[][] completePath(Integer[][] casillas_usadas, int numero_fil, int numero_col){
        String[][] tablero = new String[numero_fil][numero_col];
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
        return tablero;
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
    public MapaI generarHidato() {
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

        //----------------------------DEFINICIÓN DEL HIDATO:
        //los while sirven para saber si se ha generado bien o se ha encerrado solo y no ha podido generar el hidato.
        MapaFactory mapaFactory = new MapaFactory();
        String[] tipos = {"Q","T","H"};
        String[] angulos = {"C", "CA"};
        MapaI result = mapaFactory.getMapa(tipos[topologia-1],angulos[tipo_adyacencia-1], numero_fil, numero_col, this);
        this.m = result;

        casillas_usadas = result.pathFinder(casillas_validas, numero_fil, numero_col);
        while (casillas_usadas[0][0] == -5) casillas_usadas = result.pathFinder(casillas_validas, numero_fil, numero_col);

        tablero = completePath(casillas_usadas, numero_fil, numero_col);

        result.setMatrix(tablero);

       return result;
    }

}
