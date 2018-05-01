package Domini;

import java.util.UUID;
import java.util.Vector;

public class TableroHexagonal extends Mapa {

    public TableroHexagonal(int filas, int columnas, String[][] tab){
        this.matrix = tab;
        this.filas = filas;
        this.columnas = columnas;
        this.interrogants = 0;
        this.ID = UUID.randomUUID().toString();
        this.teSolucio = false;
        //hidatoValido();
        if(this.teSolucio) System.out.println("TE SOLUCIO");
        else System.out.println("NO TE SOLUCIO!!");
        instances.add(this.ID);
    }

    @Override
    protected boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v) {
        if(toInsert == 1) return true;
        boolean adjacentPetit = false;
        boolean adjacentGran = false;
        boolean adjacentInterrogant = false;
        Integer[] pos = {x,y};
        Integer[] nextPos = new Integer[2];
        Integer[] direccionesNormales = {-2,-1,0,1,2,3};
        Integer[] direccionesImpares = {0,1,2,3,4,5};
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
    protected boolean matriuCorrecte(){
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

        Integer[] pos = new Integer[2];
        while(interr != 0 && correcte){
            trobat = false;
            pos[0] = y;
            pos[1] = x;
            for(int i = 0; i <= 3 && !trobat; i++){
                pos = siguienteCasilla(pos,i);
                if ((pos[1] > 0) && (pos[1] < columnas -1) && (pos[0] > 0) && (pos[0] <filas -1) && matrix[pos[0]][pos[1]].equals(Integer.toString(buscar))){
                    y = pos[0];
                    x = pos[1];
                    interr--;
                    buscar++;
                }
                else{
                    this.teSolucio = false;
                    correcte = false; //control de errores
                }
            }
        }
        this.teSolucio = true;
        return correcte;
    }
}