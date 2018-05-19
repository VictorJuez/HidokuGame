package Domini;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class TableroCuadrado extends Mapa {

    public TableroCuadrado(String[][] tab){
        super(tab);
        tipo = "Q";
        angulos = "C";
    }

    public TableroCuadrado(){
        super();
        tipo = "Q";
        angulos = "C";
    }

    public TableroCuadrado(String ID, String[][] tab){
        super(ID, tab);
        tipo = "Q";
        angulos = "C";
    }


    @Override
    protected void calculoAdyacencias() {
        Integer[] pos = new Integer[2];
        Integer[] posAD = new Integer[2];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                pos[0] = i;
                pos[1] = j;
                adyacencias a = new adyacencias();
                if ((pos[1] >= 0) && (pos[1] <= columnas - 1) && (pos[0] >= 0) && (pos[0] <= filas - 1)) {
                    if (!solutionMatrix[pos[0]][pos[1]].equals("*") && !solutionMatrix[pos[0]][pos[1]].equals("#")) {
                        a.valor = solutionMatrix[posAD[0]][posAD[1]];
                        a.x = j;
                        a.y = i;
                        a.ad = new Vector<Integer[]>;
                        for (int k = 0; k <= 3; k++){
                            posAD = siguienteCasilla(pos, k);
                            if ((posAD[1] >= 0) && (posAD[1] <= columnas - 1) && (posAD[0] >= 0) && (posAD[0] <= filas - 1)) {
                                if (!solutionMatrix[posAD[0]][posAD[1]].equals("*") && !solutionMatrix[posAD[0]][posAD[1]].equals("#")) {
                                    
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Comprueba si el hidato (matrix) ya resuelto está bien resuelto o no.
     * @return Boolean indicando si esta bien resuelto o no.
     */
    @Override
    public boolean matriuCorrecte(){
        int x = 0;
        int y = 0;
        boolean trobat = false;
        for(int y1 = 0; y1 < filas && !trobat; y1++){
            for (int x1 = 0; x1 < columnas; x1++){
                if(solutionMatrix[y1][x1].equals("1")){
                    trobat = true;
                    y = y1;
                    x = x1;
                }
            }
        }
        if(!trobat) return false;

        boolean correcte = true;
        int buscar = 2;
        int interr = interrogants + numeros -1;

        Integer[] pos = new Integer[2];
        Integer[] posant = new Integer[2];
        posant[0] = y;
        posant[1] = x;
        while(interr != 0 && correcte){
            trobat = false;
            for(int i = 0; (i <= 3) && !trobat; i++){
                pos = siguienteCasilla(posant,i);
                if ((pos[1] >= 0) && (pos[1] <= columnas -1) && (pos[0] >= 0) && (pos[0] <= filas -1) && solutionMatrix[pos[0]][pos[1]].equals(Integer.toString(buscar))){
                    interr--;
                    buscar++;
                    trobat = true;
                    posant[0] = pos[0];
                    posant[1] = pos[1];
                }

            }
            if (!trobat) correcte = false;
            else correcte = true;
        }
        return correcte;
    }

    protected Integer[] siguienteCasilla(Integer[] ant_casilla, int dir){
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
        }
        return sig_casilla;
    }

    /**
     * Genera un hidato del tipo cuadrado aleatoriamente
     * @param casillas_validas array de enteros que contiene la posicion de la casilla actual.
     * @param numero_fil El numero de filas del hidato
     * @param numero_col El numero de columnas del hidato
     * @return Matriz de enteros con el hidato generado.
     */
    @Override
    public Integer[][] pathFinder(int casillas_validas, int numero_fil, int numero_col) {
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

        //si se ha quedado atrapado, se lo comunicamos a la función principal
        if (atrapado) casillas_visitadas[0][0] = -5;
        return casillas_visitadas;
    }
}
