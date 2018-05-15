package Domini;

public class MapaFactory {
    public Mapa getMapa(String tipo, String angulos, int filas, int columnas, String[][] tab){
        if(tipo.equals("Q")){
            if(angulos.equals("CA")) return new TableroCuadradoAngulos(filas, columnas, tab);
            return new TableroCuadrado(filas, columnas, tab);
        }

        else if(tipo.equals("T")){
            if(angulos.equals("CA")) return new TableroTriangularAngulos(filas, columnas, tab);
            return new TableroTriangular(filas, columnas, tab);
        }

        else if(tipo.equals("H")) return new TableroHexagonal(filas, columnas, tab);

        return null;
    }

    public Mapa getMapa(String tipo, String angulos, int filas, int columnas){
        if(tipo.equals("Q")){
            if(angulos.equals("CA")) return new TableroCuadradoAngulos(filas, columnas);
            return new TableroCuadrado(filas, columnas);
        }

        else if(tipo.equals("T")){
            if(angulos.equals("CA")) return new TableroTriangularAngulos(filas, columnas);
            return new TableroTriangular(filas, columnas);
        }

        else if(tipo.equals("H")) return new TableroHexagonal(filas, columnas);

        return null;
    }
}
