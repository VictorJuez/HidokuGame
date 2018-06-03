package Domini.Mapa;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class TableroCuadradoAngulos extends TableroCuadrado {

    public TableroCuadradoAngulos(String[][] tab) {
        super(tab);
        angulos = "CA";
    }

    public TableroCuadradoAngulos(){
        super();
        angulos = "CA";
    }

    public TableroCuadradoAngulos(String ID, String name, String[][] tab){
        super(ID, name, tab);
        angulos = "CA";
    }



    @Override
    protected void calculoAdyacencias() {
        Integer[] pos = new Integer[2];
        Integer[] posAD;
        for(int i = 0; i < tablaAD.size(); ++i){
            pos[0] = tablaAD.get(i).getY();
            pos[1] = tablaAD.get(i).getX();
            //int z = tablaAD.get(i).getZ();
            for(int j = -2; j <= 5; ++j){
                posAD = siguienteCasilla(pos,j);
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


    @Override
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
        }
        return sig_casilla;
    }

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
        //si se ha quedado atrapado, se lo comunicamos a la función principal
        if (atrapado) casillas_visitadas[0][0] = -5;
        return casillas_visitadas;
    }
}
