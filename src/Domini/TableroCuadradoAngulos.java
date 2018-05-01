package Domini;

import java.util.Vector;

public class TableroCuadradoAngulos extends TableroCuadrado {
    public TableroCuadradoAngulos(int filas, int columnas, String[][] tab) {
        super(filas, columnas, tab);
    }

    @Override
    protected boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v){
        if(toInsert == 1) return true;
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

        if(adjacentGran && adjacentPetit) return true;
        if(adjacentGran && adjacentInterrogant) if(v.contains(toInsert-1))return true;
        if(adjacentPetit && adjacentInterrogant) if(v.contains(toInsert+1)) return true;

        if(adjacentPetit && v.size() == 1 && toInsert == interrogants+numeros) return true;
        //if(adjacentGran && v.size() == 1) return true;

        return false;
    }
}
