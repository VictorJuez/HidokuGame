package Domini.Mapa;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class TableroTriangular extends Mapa {

    public TableroTriangular(String[][] tab){
        super(tab);
        tipo = "T";
        angulos = "C";
    }

    public TableroTriangular(){
        super();
        tipo = "T";
        angulos = "C";
    }

    public TableroTriangular(String ID, String[][] tab){
        super(ID, tab);
        tipo = "T";
        angulos = "C";
    }


    @Override
    protected Vector<adyacencias> calculoAdyacencias() {
        Integer[] pos = new Integer[2];
        Integer[] posAD;
        for(int i = 0; i < tablaAD.size(); ++i){
            pos[0] = tablaAD.get(i).getY();
            pos[1] = tablaAD.get(i).getX();
            //int z = tablaAD.get(i).getZ();
            int j;
            if((pos[0] + pos[1])%2 == 0)j = 1;
            else j= 0;
            int w = j + 2;
            for(; j <= w; ++j){
                posAD = siguienteCasilla(pos,j);
                if ((posAD[1] >= 0) && (posAD[1] <= columnas - 1) && (posAD[0] >= 0) && (posAD[0] <= filas - 1)){ //si posAD esta en els limits
                    int z = posAD[0]*columnas + posAD[1];
                    for(int k = 0; k < tablaAD.size(); k++){
                        if (tablaAD.get(k).getZ() == z){
                            tablaAD.get(i).add(k);
                        }
                    }
                }
            }
        }
        return tablaAD;
    }


    protected Integer[] siguienteCasilla(Integer[] ant_casilla, int dir){
        Integer[] sig_casilla = new Integer[2];
        switch (dir) {
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
     * Genera un hidato del tipo triangulo aleatoriamente
     * @param casillas_validas array de enteros que contiene la posicion de la casilla actual
     * @param numero_fil El numero de filas del hidato
     * @param numero_col El numero de columnas del hidato
     * @return Matriz de enteros con el hidato generado.
     */
    @Override
    public Integer[][] pathFinder(int casillas_validas, int numero_fil, int numero_col)
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
        //para ver si se ha quedado atrapado..
        if (atrapado) casillas_visitadas[0][0] = -5;
        return casillas_visitadas;
    }
}
