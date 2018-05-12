package Domini;

import java.util.Vector;

public class TableroTriangularAngulos extends TableroTriangular {

    /**
     * Funcion creadora de TableroTriangular
     * @param filas numero de filas del hidato
     * @param columnas numero de columnas del hidato
     */
    public TableroTriangularAngulos(int filas, int columnas, String[][] tab) {
        super(filas, columnas, tab);
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
    protected boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v) {
        boolean adjacentPetit = false;
        boolean adjacentGran = false;
        boolean adjacentInterrogant = false;
        Integer[] pos = {x,y};
        Integer[] nextPos = new Integer[2];
        Integer[] direccionesNormales = {-2,-1,0,1,2,3,4,5,6,7,8,9};
        Integer[] direccionesInversas = {-4,-3,-2,-1,0,1,2,3,4,5,6,7};
        Integer[] direcciones = new Integer[12];
        boolean normal = (pos[0]+pos[1])%2 == 0;
        if(normal) direcciones = direccionesNormales;
        else direcciones = direccionesInversas;

        for(int i=0; i<direcciones.length; ++i){
            nextPos = siguienteCasilla(pos,direcciones[i]);

            if(nextPos[0]>=0 && nextPos[1]>=0 && nextPos[0]<filas && nextPos[1]<columnas) {

                if (isInteger(A[nextPos[0]][nextPos[1]])) {
                    int tableValue = Integer.parseInt(A[nextPos[0]][nextPos[1]]);

                    if (tableValue == toInsert - 1) adjacentPetit = true;
                    if (tableValue == toInsert + 1) adjacentGran = true;
                } else if (A[nextPos[0]][nextPos[1]].equals("?")) adjacentInterrogant = true;
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
        numerosExistents();
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

        this.teSolucio = correcte;
        return correcte;
    }
}
