package Domini;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CtrDomini {
    private static List<Mapa> mapasList = new ArrayList<>();

    public CtrDomini() {
    }

    public String[][] insertarHidato(String topologia, String angulos, int filas, int columnas, String[][] tab) {

        Mapa m;

        switch (topologia){
            case "Q":
                if(angulos.equals("CA")) m = new TableroCuadradoAngulos(filas,columnas,tab);
                else m = new TableroCuadrado(filas,columnas,tab);
                break;
            case "T":
                if(angulos.equals("CA")) m = new TableroTriangularAngulos(filas,columnas,tab);
                else m = new TableroTriangular(filas,columnas,tab);
                break;
            case "H":
                m = new TableroHexagonal(filas,columnas,tab);
                break;
            default:
                m = null;
        }
        mapasList.add(m);
        return m.getMatrix();
    }

    public List listaHidatos(){
        List l = TableroCuadrado.getInstances();
        return l;
    }

    public static List getMapasList() {
        System.out.println(mapasList.get(0).filas);
        return mapasList;
    }

    public Pair<String[], String[][]> generarHidato(){
        Mapa m = new Mapa();
        Pair<String[], String[][]> tab = m.generarHidato();
        mapasList.add(m);
        return tab;
    }

}