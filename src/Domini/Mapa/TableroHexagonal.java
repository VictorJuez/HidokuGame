package Domini.Mapa;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class TableroHexagonal extends Mapa {

    public TableroHexagonal(String[][] tab){
        super(tab);
        tipo = "H";
        angulos = "C";
    }

    public TableroHexagonal(){
        super();
        tipo = "H";
        angulos = "C";
    }

    public TableroHexagonal(String ID, String name, String[][] tab){
        super(ID, name, tab);
        tipo = "H";
        angulos = "C";
    }

    @Override
    protected void calculoAdyacencias() {
        Integer[] pos = new Integer[2];
        Integer[] posAD;
        Integer[] par = {-2,0,1, 2, 3, 5};
        Integer[] impar = {-1, 0, 1, 2, 3, 4};
        Integer[] dir;
        for(int i = 0; i < tablaAD.size(); ++i){
            pos[0] = tablaAD.get(i).getY();
            pos[1] = tablaAD.get(i).getX();
            //int z = tablaAD.get(i).getZ();
            if((pos[0]%2 == 0))dir = par;
            else dir = impar;
            for(int j = 0; j <6; ++j){
                posAD = siguienteCasilla(pos,dir[j]);
                if ((posAD[1] >= 0) && (posAD[1] <= columnas - 1) && (posAD[0] >= 0) && (posAD[0] <= filas - 1)){ //si posAD esta en els limits
                    int z = posAD[0]*columnas + posAD[1];
                    for(int k = 0; k < tablaAD.size(); k++){
                        if (tablaAD.get(k).getZ() == z){
                            tablaAD.get(i).getAd().add(k);
                        }
                    }
                }
            }
        }
    }




    protected Integer[] siguienteCasilla(Integer[] ant_casilla, int dir){
        //{-2, 0, 1, 2, 3, 5}
        Integer[] sig_casilla = new Integer[2];
        switch (dir) {
            case (-1):
                sig_casilla[0] = ant_casilla[0] - 1; //diagonal arriba-derecha
                sig_casilla[1] = ant_casilla[1] + 1;
                break;
            case (0):
                sig_casilla[0] = ant_casilla[0] - 1; //la casilla de arriba
                sig_casilla[1] = ant_casilla[1];
                break;
            case (-2):
                sig_casilla[0] = ant_casilla[0] - 1; //diagonal arriba-izquierda
                sig_casilla[1] = ant_casilla[1] - 1;
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
            case (4):
                sig_casilla[0] = ant_casilla[0] + 1; //diagonal abajo-derecha
                sig_casilla[1] = ant_casilla[1] + 1;
                break;
            case (5):
                sig_casilla[0] = ant_casilla[0] + 1; //diagonal abajo-izquierda
                sig_casilla[1] = ant_casilla[1] - 1;
                break;
        }
        return sig_casilla;
    }

    /**
     * Genera un hidato del tipo hexagono aleatoriamente
     * @param casillas_validas array de enteros que contiene la posicion de la casilla actual.
     * @param numero_fil El numero de filas del hidato
     * @param numero_col El numero de columnas del hidato
     * @return Matriz de enteros con el hidato generado.
     */
    @Override
    public Integer[][] pathFinder(int casillas_validas, int numero_fil, int numero_col)
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
            normal = ant_casilla[0]%2 ==1; //true -> fila impar = normal
            if (normal) dir = ThreadLocalRandom.current().nextInt(-1, 4 + 1);
            else
            {
                dir = ThreadLocalRandom.current().nextInt(0, 5 + 1);
                dir = adyacenciashex[dir];
            }
            sig_casilla = siguienteCasilla(ant_casilla, dir);
            int intentos = 0;
            while (!casillaValida(sig_casilla[0], sig_casilla[1], numero_fil, numero_col, casillas_visitadas) && !atrapado)
            {
                if (normal) dir = ThreadLocalRandom.current().nextInt(-1, 4 + 1);
                else
                {
                    dir = ThreadLocalRandom.current().nextInt(0, 5 + 1);
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
}
