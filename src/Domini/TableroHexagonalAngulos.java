package Domini;

import java.util.Vector;

public class TableroHexagonalAngulos extends TableroHexagonal {
    public TableroHexagonalAngulos(int filas, int columnas, String[][] tab) {
        super(filas, columnas, tab);
    }

    @Override
    protected boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v) {
        if(toInsert == 1) return true;
        boolean adjacentPetit = false;
        boolean adjacentGran = false;
        boolean adjacentInterrogant = false;
        Integer[] pos = {x,y};
        Integer[] nextPos = new Integer[2];
        Integer[] direccionesNormales = {-2,-1,0,1,2,3}; //cambiar a adyacencias angulos;
        Integer[] direccionesImpares = {0,1,2,3,4,5}; //cambiar a adyacencias con angulos;
        Integer[] direcciones = new Integer[3];
        boolean normal = pos[1]%2 == 0;
        if(normal) direcciones = direccionesNormales;
        else direcciones = direccionesImpares;

        for(int i=0; i<direcciones.length; ++i){
            nextPos = siguienteCasilla(pos,direcciones[i]);

            if(nextPos[0]>=0 && nextPos[1]>=0 && nextPos[0]<A.length && nextPos[1]<A[0].length) {

                if (isInteger(A[nextPos[0]][nextPos[1]])) {
                    int tableValue = Integer.parseInt(A[nextPos[0]][nextPos[1]]);

                    if (tableValue == toInsert - 1) adjacentPetit = true;
                    if (tableValue == toInsert + 1) adjacentGran = true;
                } else if (A[nextPos[0]][nextPos[1]].equals("?")) adjacentInterrogant = true;
            }
        }

        if(adjacentGran && adjacentPetit) return true;
        if(adjacentGran && adjacentInterrogant) if(v.contains(toInsert-1))return true;
        if(adjacentPetit && adjacentInterrogant) if(v.contains(toInsert+1)) return true;

        if(adjacentPetit && v.size() == 1 && toInsert == interrogants+numeros) return true;
        //if(adjacentGran && v.size() == 1) return true;
        return false;
    }
}
