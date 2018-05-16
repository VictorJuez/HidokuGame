package Domini;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class TableroCuadradoAngulos extends TableroCuadrado {

    /**
     * Funcion creadora de TableroCuadrado
     * @param filas numero de filas del hidato
     * @param columnas numero de columnas del hidato
     */
    public TableroCuadradoAngulos(String[][] tab) {
        super(tab);
        angulos = "CA";
    }

    public TableroCuadradoAngulos(){
        super();
        angulos = "CA";
    }

    public TableroCuadradoAngulos(String ID, String[][] tab){
        super(ID, tab);
        angulos = "CA";
    }

    /**
     * Calcula para una casilla "?" a rellenar del hidato, si se le puede poner el numero restante: toInsert
     * @param x,y fila y columna de la casilla "?"
     * @param A El hidato a comprobar
     * @param toInsert El numero restante a comprobar
     * @param v El vector de numeros restantes
     * @return Boolean indicando si se puede poner el numero o no en la casilla.
     */
    @Override
    public boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v){
        //if(toInsert == 1) return true;
        boolean adjacentPetit = false;
        boolean adjacentGran = false;
        boolean adjacentInterrogant = false;
        for(int i=0; i<8; ++i){
            int xx = x;
            int yy = y;
            switch (i){
                case 0:
                    xx=x;
                    yy=y-1;
                    break;
                case 1:
                    xx=x-1;
                    yy=y;
                    break;
                case 2:
                    xx=x;
                    yy=y+1;
                    break;
                case 3:
                    xx=x+1;
                    yy=y;
                    break;
                case 4:
                    xx=x-1;
                    yy=y-1;
                    break;
                case 5:
                    xx=x-1;
                    yy=y+1;
                    break;
                case 6:
                    xx=x+1;
                    yy=y-1;
                    break;
                case 7:
                    xx=x+1;
                    yy=y+1;
                    break;
            }
            if(xx>=0 && yy>=0 && xx<A.length && yy<A[0].length) {

                if (isInteger(A[xx][yy])) {
                    int tableValue = Integer.parseInt(A[xx][yy]);

                    if (tableValue == toInsert - 1) adjacentPetit = true;
                    if (tableValue == toInsert + 1) adjacentGran = true;
                } else if (A[xx][yy].equals("?")) adjacentInterrogant = true;
            }
        }

        if(toInsert == 1 && adjacentGran) return true;
        if(toInsert == 1 && adjacentInterrogant) if(v.contains(toInsert+1)) return true;

        if(adjacentGran && adjacentPetit) return true;
        if(adjacentGran && adjacentInterrogant) if(v.contains(toInsert-1))return true;
        if(adjacentPetit && adjacentInterrogant) if(v.contains(toInsert+1)) return true;

        if(adjacentPetit && v.size() == 1 && toInsert == interrogants+numeros) return true;
        //if(adjacentGran && v.size() == 1) return true;

        return false;
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
            for(int i = -2; (i <= 5) && !trobat; i++){
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
