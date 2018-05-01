package Domini;

import javafx.util.Pair;

import java.util.List;

public class CtrDomini {
    /**

    public CtrDomini() {
    }

    public String[][] insertarHidato(String topologia, String angulos, int filas, int columnas, String[][] tab) {

        Mapa m = new Mapa();

        switch (topologia){
            case "Q":
                if(angulos.equals("CA")) m = new TableroCuadradoAngulos(filas,columnas,tab);
                else m = new TableroCuadrado(filas,columnas,tab);
                break;
            case "T":
                if(angulos.equals("CA")) m = new TableroTriangularAngulos(filas,columnas,tab);
                else m = new TableroTriangular(filas,columnas,tab);
            case "H":
                m = new TableroHexagonal(filas,columnas,tab);
            default:
                m = null;
        }

        return m.getMatrix();
    }

    public List listaHidatos(){
        List l = TableroCuadrado.getInstances();
        return l;
    }

    public Pair<String[], String[][]> generarHidato(){
        Mapa m = new Mapa();
        Pair<String[], String[][]> tab = m.generarHidato();
        String[] index = tab.getKey();
        String[][] matrix = tab.getValue();


        return tab;
    }
     **/

}