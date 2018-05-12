package Domini;

import java.util.UUID;
import java.util.Vector;

public class TableroTriangular extends Mapa{

    /**
     * Funcion creadora de TableroCuadrado
     * @param filas numero de filas del hidato
     * @param columnas numero de columnas del hidato
     */
    public TableroTriangular(int filas, int columnas, String[][] tab){
        this.matrix = tab;
        this.filas = filas;
        this.columnas = columnas;
        this.interrogants = 0;
        this.ID = UUID.randomUUID().toString();
        this.teSolucio = false;
        instances.add(this.ID);
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
        Integer[] direccionesNormales = {1,2,3};
        Integer[] direccionesInversas = {0,1,2};
        Integer[] direcciones = new Integer[3];
        boolean normal = (pos[0] % 2 == 0) && (pos[1] % 2 == 0) || (pos[0] % 2 != 0) && (pos[1] % 2 != 0);
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
        if(adjacentPetit && adjacentInterrogant) if(v.contains(toInsert+1)) return true;
        if(adjacentGran && adjacentInterrogant) if(v.contains(toInsert-1))return true;

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
            if((posant[0] + posant[1])%2 == 0)i = 1;
            else i = 0;
            j = i + 2;
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
        return correcte;
    }
}
