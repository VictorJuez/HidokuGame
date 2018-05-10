import Domini.*;
import javafx.util.Pair;

public class Proves {
    public static void main(String[] args) {
        int contador = 0;
        while (true) {
            ++contador;
            System.out.println("iteraciones: "+contador);
            Mapa m1 = new Mapa();
            Pair<String[], String[][]> tab = m1.generarHidato();
            String[] index = tab.getKey();
            for (int i = 0; i < index.length; ++i) System.out.print(index[i]);
            System.out.println();

            int filas = Integer.parseInt(index[2]);
            int columnas = Integer.parseInt(index[3]);

            Mapa m;

            switch (index[0]) {
                case "Q":
                    if (index[1].equals("CA")) m = new TableroCuadradoAngulos(filas, columnas, tab.getValue());
                    else m = new TableroCuadrado(filas, columnas, tab.getValue());
                    break;
                case "T":
                    if (index[1].equals("CA")) m = new TableroTriangularAngulos(filas, columnas, tab.getValue());
                    else m = new TableroTriangular(filas, columnas, tab.getValue());
                    break;
                case "H":
                    m = new TableroHexagonal(filas, columnas, tab.getValue());
                    break;
                default:
                    m = null;
            }
            m.hidatoValido();
        }
    }
}
