package Domini;

import java.util.List;

public class CtrDomini {

    public CtrDomini() {
    }

    public String[][] insertarHidato(int filas, int columnas, String[][] tab) {
        TableroCuadrado t = new TableroCuadrado(filas,columnas,tab);
        return t.getMatrix();
    }

    public void generarHidato(){
        //Mahias aqui es donde puedes poner tu funcion.
    }

    public List listaHidatos(){
        List l = TableroCuadrado.getInstances();
        return l;
    }

}