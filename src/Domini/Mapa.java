package Domini;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Mapa {
    protected String ID;
    protected static List<String> instances = new ArrayList();
    protected boolean adyacenciaAngulos;
    protected int filas;
    protected int columnas;
    protected int interrogants;
    protected int numeros;
    protected boolean teSolucio;
    protected String[][] matrix;

    public Mapa(){}

    public String getID() {
        return ID;
    }

    public static List getInstances() {
        return instances;
    }

    public String[][] getMatrix() {
        return matrix;
    }

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

    protected Vector<Integer> numerosRestants(){   //aixo es podria guardar tot com si fos un atribut
        Vector<String> existents = numerosExistents();
        Vector<Integer> total = new Vector<>();
        for(int k = 0; k < interrogants + existents.size(); k++){
            if (!existents.contains(Integer.toString(k+1))) total.add(k+1);
        }
        return total;
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

    protected void processa(String[][] A){
        this.teSolucio = true;
    }

    public void hidatoValido(){
        String[][] A = matrix;
        Vector<Integer> v;
        v = numerosRestants();
        backtrackingResolucio(A, v);
        if(this.teSolucio) System.out.println("\nTE SOLUCIO:");
        else System.out.println("\nNO TE SOLUCIO");
    }

    protected boolean backtrackingResolucio(String[][] A, Vector v){
        //if(k>=filas*columnas) return;
        //System.out.println(v);
        if(v.size() == 0) {
            processa(A);
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
                            //processa(A);
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
    protected boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v){return false;}

    //MATHIAS GENERATION ALGORITHM
    private boolean holeChecker(String[][] tablero, int i, int j, int max_fil, int max_col)
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

    //función para ver si la siguiente casilla del path que estamos siguiendo es válida.
    private boolean casillaValida(int i, int j, int num_filas, int num_col, Integer[][] casillas_visitadas) {
        if (i < (num_filas - 1) && j < (num_col - 1) && i > 0 && j > 0) {
            if (casillas_visitadas[i][j] == -1) return true;
        }
        return false;
    }

    //ayuda a hacer el cálculo de la siguiente casilla.
    //el boolean hexagono es para simplificar el cálculo, sacando una dirección fuera del rango de números que voy
    //a generar cuando haga el random.
    protected Integer[] siguienteCasilla(Integer[] ant_casilla, int dir) {
        Integer[] sig_casilla = new Integer[2];
        switch (dir) {
            //------ÉSTO PARA LOS CUADRADOS
            case (0):
                sig_casilla[0] = ant_casilla[0] - 1; //la casilla de arriba
                sig_casilla[1] = ant_casilla[1];
                break;
            case (1):
                sig_casilla[0] = ant_casilla[0];
                sig_casilla[1] = ant_casilla[1] + 1; //la de la derecha
                break;
            case (2):
                sig_casilla[0] = ant_casilla[0];
                sig_casilla[1] = ant_casilla[1] - 1; //la de la izquierda
                break;
            case (3):
                sig_casilla[0] = ant_casilla[0] + 1; //la casilla de abajo
                sig_casilla[1] = ant_casilla[1];
                break;
            case (-1):
                sig_casilla[0] = ant_casilla[0] - 1; //diagonal arriba-derecha
                sig_casilla[1] = ant_casilla[1] + 1;
                break;
            case (-2):
                sig_casilla[0] = ant_casilla[0] - 1; //diagonal arriba-izquierda
                sig_casilla[1] = ant_casilla[1] - 1;
                break;
            case (4):
                sig_casilla[0] = ant_casilla[0] + 1; //diagonal abajo-derecha
                sig_casilla[1] = ant_casilla[1] + 1;
                break;
            case (5):
                sig_casilla[0] = ant_casilla[0] + 1; //diagonal abajo-izquierda
                sig_casilla[1] = ant_casilla[1] - 1;
                break;
            //------ÉSTO PARA LOS TRIANGULOS
            case (6):
                sig_casilla[0] = ant_casilla[0]; //dos a la derecha
                sig_casilla[1] = ant_casilla[1] + 2;
                break;
            case (7):
                sig_casilla[0] = ant_casilla[0]; //dos a la izquierda
                sig_casilla[1] = ant_casilla[1] - 2;
                break;
            case (8):
                sig_casilla[0] = ant_casilla[0] - 1; //abajo-dos derecha
                sig_casilla[1] = ant_casilla[1] + 2;
                break;
            case (9):
                sig_casilla[0] = ant_casilla[0] + 1; //abajo-dos izquierda
                sig_casilla[1] = ant_casilla[1] - 2;
                break;
            case (-3):
                sig_casilla[0] = ant_casilla[0] - 1; //arriba-dos derecha
                sig_casilla[1] = ant_casilla[1] + 2;
                break;
            case (-4):
                sig_casilla[0] = ant_casilla[0] - 1; //arriba dos izquierda
                sig_casilla[1] = ant_casilla[1] - 2;
                break;
        }
        return sig_casilla;
    }

    //------------------IMPORTANTE
    //----Las columnas están desencajadas dependiendo de si el número de columna es par o impar.
    //------------------
    private Integer[][] pathFinderHexagonos(int casillas_validas, int numero_fil, int numero_col)
    {
        Integer[][] casillas_visitadas;
        boolean atrapado = false;
        casillas_visitadas = new Integer[numero_fil][numero_col];
        for (int i = 0; i < numero_fil; ++i) for (int j = 0; j < numero_col; ++j) casillas_visitadas[i][j] = -1;
        int dir;
        Integer[] ant_casilla = new Integer[2]; //[0] -> filas, [1] -> columnas CASILLA EN LA QUE ESTOY ACTUALMENTE
        Integer[] sig_casilla = new Integer[2]; //[0] -> filas, [1] -> columnas

        ant_casilla[0] = ThreadLocalRandom.current().nextInt(0, numero_fil);
        ant_casilla[1] = ThreadLocalRandom.current().nextInt(0, numero_col);

        casillas_visitadas[ant_casilla[0]][ant_casilla[1]] = 1;
        Integer[] adyacenciashex = new Integer[]{-2, 0, 1, 2, 3, 5};
        //las adyacencias funcionan igual sea costados o costados y angulos.
        boolean normal; //para saber la columna del hexágono (las adyacencias funcionan diferente)
        for (int i = 2; i < casillas_validas + 1 && !atrapado; ++i)
        {
            normal = ant_casilla[1] % 2 == 0; //true -> columna que empieza por -2.
            if (normal) dir = ThreadLocalRandom.current().nextInt(-2, 3 + 1);
            else
            {
                dir = ThreadLocalRandom.current().nextInt(0, 4 + 1);
                dir = adyacenciashex[dir];
            }
            sig_casilla = siguienteCasilla(ant_casilla, dir);
            int intentos = 0;
            while (!casillaValida(sig_casilla[0], sig_casilla[1], numero_fil, numero_col, casillas_visitadas) && !atrapado)
            {
                if (normal) dir = ThreadLocalRandom.current().nextInt(0, 2 + 1);
                else
                {
                    dir = ThreadLocalRandom.current().nextInt(0, 4 + 1);
                    dir = adyacenciashex[dir];
                }
                sig_casilla = siguienteCasilla(ant_casilla, dir);
                intentos += 1;
                if (intentos == 5) atrapado = true;
            }
            if (!atrapado)
            {
                ant_casilla = sig_casilla; //para avanzar en el hidato.
                casillas_visitadas[ant_casilla[0]][ant_casilla[1]] = i;
            }
        }
        if (atrapado) casillas_visitadas[0][0] = -5;
        return casillas_visitadas;
    }

    //------------------IMPORTANTE
    //----He decidido que los triangulos empiezan normal en el [0][0], en el [1][0] salen al revés (adyacencias cambian)
    //----a partir de ahí, se intercalan. [par][par] = normal, [impar][impar] = normal, [p][i] = reves, [i][i] = reves
    //------------------
    private Integer[][] pathFinderTriangulos(int casillas_validas, int adyacencia, int numero_fil, int numero_col)
    {
        Integer[][] casillas_visitadas;
        boolean atrapado = false; //para saber si se ha quedado atrapado intentando crear el path
        casillas_visitadas = new Integer[numero_fil][numero_col];
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! VVVVVVV CAMBIAR ÉSTO
        for (int i = 0; i < numero_fil; ++i) for (int j = 0; j < numero_col; ++j) casillas_visitadas[i][j] = -1;
        int dir;
        Integer[] ant_casilla = new Integer[2]; //[0] -> filas, [1] -> columnas CASILLA EN LA QUE ESTOY ACTUALMENTE
        Integer[] sig_casilla = new Integer[2]; //[0] -> filas, [1] -> columnas

        ant_casilla[0] = ThreadLocalRandom.current().nextInt(0, numero_fil);
        ant_casilla[1] = ThreadLocalRandom.current().nextInt(0, numero_col);

        casillas_visitadas[ant_casilla[0]][ant_casilla[1]] = 1;

        boolean normal; //para saber cómo está orientado el triángulo (normal o al revés).
        if (adyacencia == 1) //ADYACENCIA COSTADOS
        {
            for (int i = 2; i < casillas_validas + 1 && !atrapado; ++i) {
                normal = (ant_casilla[0] + ant_casilla[1]) % 2 == 0;
                if (normal) dir = ThreadLocalRandom.current().nextInt(1, 3 + 1);
                else dir = ThreadLocalRandom.current().nextInt(0, 2 + 1);
                sig_casilla = siguienteCasilla(ant_casilla, dir);
                int intentos = 0; //cuando intentos == numero adyacencias sabremos que se ha quedado atrapado
                while (!casillaValida(sig_casilla[0], sig_casilla[1], numero_fil, numero_col, casillas_visitadas) && !atrapado) {
                    if (normal) dir = ThreadLocalRandom.current().nextInt(1, 3 + 1);
                    else dir = ThreadLocalRandom.current().nextInt(0, 2 + 1);
                    sig_casilla = siguienteCasilla(ant_casilla, dir);
                    intentos += 1;
                    if (intentos == 3) atrapado = true;
                }
                if (!atrapado)
                {
                    ant_casilla = sig_casilla; //para avanzar en el hidato.
                    casillas_visitadas[ant_casilla[0]][ant_casilla[1]] = i;
                }
            }
        } else //ADYACENCIA DIAGONALES
        {
            for (int i = 2; i < casillas_validas + 1 && !atrapado; ++i)
            {
                normal = (ant_casilla[0] % 2 == 0) && (ant_casilla[1] % 2 == 0) || (ant_casilla[0] % 2 != 0) && (ant_casilla[1] % 2 != 0);
                if (normal) dir = ThreadLocalRandom.current().nextInt(-2, 9 + 1);
                else dir = ThreadLocalRandom.current().nextInt(-4, 7 + 1);
                sig_casilla = siguienteCasilla(ant_casilla, dir);
                int intentos = 0;
                while (!casillaValida(sig_casilla[0], sig_casilla[1], numero_fil, numero_col, casillas_visitadas) && !atrapado) {
                    if (normal) dir = ThreadLocalRandom.current().nextInt(-2, 9 + 1);
                    else dir = ThreadLocalRandom.current().nextInt(-4, 7 + 1);
                    sig_casilla = siguienteCasilla(ant_casilla, dir);
                    intentos += 1;
                    if (intentos == 12) atrapado = true;
                }
                if (!atrapado)
                {
                    ant_casilla = sig_casilla; //para avanzar en el hidato.
                    casillas_visitadas[ant_casilla[0]][ant_casilla[1]] = i;
                }
            }
        }
        //para ver si se ha quedado atrapado..
        if (atrapado) casillas_visitadas[0][0] = -5;
        return casillas_visitadas;
    }

    //en ésta función puedo escoger en replicar el código o meter un if en el random de la dirección. eficiencia vs replicar código.
    private Integer[][] pathFinderCuadrados(int casillas_validas, int adyacencia, int numero_fil, int numero_col) {
        Integer[][] casillas_visitadas;
        boolean atrapado = false; //para saber si se ha quedado atrapado intentando crear el path
        casillas_visitadas = new Integer[numero_fil][numero_col];
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! VVVVVVV CAMBIAR ÉSTO
        for (int i = 0; i < numero_fil; ++i) for (int j = 0; j < numero_col; ++j) casillas_visitadas[i][j] = -1;
        int dir;
        Integer[] ant_casilla = new Integer[2]; //[0] -> filas, [1] -> columnas
        Integer[] sig_casilla = new Integer[2]; //[0] -> filas, [1] -> columnas

        ant_casilla[0] = ThreadLocalRandom.current().nextInt(0, numero_fil);
        ant_casilla[1] = ThreadLocalRandom.current().nextInt(0, numero_col);

        casillas_visitadas[ant_casilla[0]][ant_casilla[1]] = 1;

        if (adyacencia == 1) //ADYACENCIA COSTADOS
        {
            for (int i = 2; i < casillas_validas + 1 && !atrapado; ++i)
            {
                dir = ThreadLocalRandom.current().nextInt(0, 3 + 1); //NO DEBE ACCEDER A LAS DIAGONALES
                sig_casilla = siguienteCasilla(ant_casilla, dir);
                int intentos = 0; //cuando intentos == numero adyacencias sabremos que se ha quedado atrapado
                while (!casillaValida(sig_casilla[0], sig_casilla[1], numero_fil, numero_col, casillas_visitadas) && !atrapado)
                {
                    dir = ThreadLocalRandom.current().nextInt(0, 3 + 1);
                    sig_casilla = siguienteCasilla(ant_casilla, dir);
                    intentos += 1;
                    if (intentos == 4) atrapado = true;
                }
                if (!atrapado)
                {
                    ant_casilla = sig_casilla; //para avanzar en el hidato.
                    casillas_visitadas[ant_casilla[0]][ant_casilla[1]] = i;
                }
            }
        } else //ADYACENCIA DIAGONALES
        {
            for (int i = 2; i < casillas_validas + 1 && !atrapado; ++i)
            {
                dir = ThreadLocalRandom.current().nextInt(-2, 5 + 1);
                sig_casilla = siguienteCasilla(ant_casilla, dir);
                int intentos = 0; //cuando intentos == numero adyacencias sabremos que se ha quedado atrapado
                while (!casillaValida(sig_casilla[0], sig_casilla[1], numero_fil, numero_col, casillas_visitadas) && !atrapado)
                {
                    dir = ThreadLocalRandom.current().nextInt(-2, 5 + 1);
                    sig_casilla = siguienteCasilla(ant_casilla, dir);
                    intentos += 1;
                    if (intentos == 8) atrapado = true;
                }
                if (!atrapado)
                {
                    ant_casilla = sig_casilla; //para avanzar en el hidato.
                    casillas_visitadas[ant_casilla[0]][ant_casilla[1]] = i;
                }
            }
        }
        //si se ha quedado atrapado, se lo comunicamos a la función principal
        if (atrapado) casillas_visitadas[0][0] = -5;
        return casillas_visitadas;
    }

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
                casillas_usadas = pathFinderCuadrados(casillas_validas, tipo_adyacencia, numero_fil, numero_col);
                while (casillas_usadas[0][0] == -5) casillas_usadas = pathFinderCuadrados(casillas_validas, tipo_adyacencia, numero_fil, numero_col);
                break;
            case (2):
                casillas_usadas = pathFinderTriangulos(casillas_validas, tipo_adyacencia, numero_fil, numero_col);
                while (casillas_usadas[0][0] == -5) casillas_usadas = pathFinderTriangulos(casillas_validas, tipo_adyacencia, numero_fil, numero_col);
                break;
            case (3):
                casillas_usadas = pathFinderHexagonos(casillas_validas, numero_fil, numero_col);
                while (casillas_usadas[0][0] == -5) casillas_usadas = pathFinderHexagonos(casillas_validas, numero_fil, numero_col);
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

        //ésto me escupe el hidato.
        /*for (int i = 0; i < numero_fil; ++i) {
            for (int j = 0; j < numero_col; ++j) {
                System.out.print(tablero[i][j]);
            }
            System.out.println();
        }*/

        //Tablero t = new Tablero();
    }

    public boolean matriuCorrecte(){return false;};

}
