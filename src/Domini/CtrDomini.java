package Domini;

import java.util.List;

public class CtrDomini {

    public CtrDomini() {
    }

    public String[][] insertarHidato(int filas, int columnas, String[][] tab) {
        Tablero t = Tablero.createTablero(filas,columnas,tab);
        return t.getMatrix();
    }

    public void generarHidato(){
        //Mahias aqui es donde puedes poner tu funcion.
    }

    public List listaHidatos(){
        List l = Tablero.getInstances();
        return l;
    }

}
