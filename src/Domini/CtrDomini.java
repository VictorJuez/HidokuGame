package Domini;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CtrDomini {

    public CtrDomini() {
    }

    public String[][] insertarHidato(int filas, int columnas, String[][] tab) {
        TableroHexagonal t = new TableroHexagonal(filas,columnas,tab);
        return t.getMatrix();
    }

    public List listaHidatos(){
        List l = TableroCuadrado.getInstances();
        return l;
    }

}