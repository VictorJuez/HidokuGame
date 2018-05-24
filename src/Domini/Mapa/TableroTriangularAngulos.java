package Domini.Mapa;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class TableroTriangularAngulos extends TableroTriangular {

    public TableroTriangularAngulos(String[][] tab) {
        super(tab);
        angulos = "CA";
    }

    public TableroTriangularAngulos(){
        super();
        angulos = "CA";
    }

    public TableroTriangularAngulos(String ID, String[][] tab){
        super(ID, tab);
        angulos = "CA";
    }



    @Override
    protected Vector<adyacencias> calculoAdyacencias() {
        Integer[] pos = new Integer[2];
        Integer[] posAD;
        inicialitzaTabla();
        for(int i = 0; i < tablaAD.size(); ++i){
            pos[0] = tablaAD.get(i).getY();
            pos[1] = tablaAD.get(i).getX();
            //int z = tablaAD.get(i).getZ();
            int j;
            if((pos[0] + pos[1])%2 == 0)j = -2;
            else j= -4;
            int w = j + 11;
            for(; j <= w; ++j){
                posAD = siguienteCasilla(pos,j);
                if ((posAD[1] >= 0) && (posAD[1] <= columnas - 1) && (posAD[0] >= 0) && (posAD[0] <= filas - 1)){ //si posAD esta en els limits
                    int z = posAD[0]*columnas + posAD[1];
                    for(int k = 0; k < tablaAD.size(); k++){
                        if (tablaAD.get(k).getZ() == z){
                            tablaAD.get(i).ad.add(k);
                        }
                    }
                }
            }
        }
        return tablaAD;
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
                if(matrix[y1][x1].equals("1")){
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

        Integer[] pos;
        Integer[] posant = new Integer[2];
        posant[0] = y;
        posant[1] = x;
        int j;
        int i;
        while(interr != 0 && correcte){
            trobat = false;
            if((posant[0] + posant[1])%2 == 0)i = -2;
            else i = -4;
            j = i + 11;
            while((i <= j) && !trobat){
                pos = siguienteCasilla(posant,i);
                if ((pos[1] >= 0) && (pos[1] <= columnas -1) && (pos[0] >= 0) && (pos[0] <= filas -1) ){
                    if (matrix[pos[0]][pos[1]].equals(Integer.toString(buscar))) {
                        interr--;
                        buscar++;
                        trobat = true;
                        posant[0] = pos[0];
                        posant[1] = pos[1];
                    }

                }
                i++;

            }
            if (!trobat) correcte = false;
            else correcte = true;
        }

        this.solucio = correcte;
        return correcte;
    }

    @Override
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
                sig_casilla[0] = ant_casilla[0] + 1; //abajo-dos derecha
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
        for (int i = 2; i < casillas_validas + 1 && !atrapado; ++i)
        {
            normal = (ant_casilla[0] + ant_casilla[1]) % 2 == 0;
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
        //para ver si se ha quedado atrapado..
        if (atrapado) casillas_visitadas[0][0] = -5;
        return casillas_visitadas;
    }
}
