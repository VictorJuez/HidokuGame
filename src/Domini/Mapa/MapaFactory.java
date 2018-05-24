package Domini.Mapa;

public class MapaFactory {
    public Mapa getMapa(String tipo, String angulos, String[][] tab){
        if(tipo.equals("Q")){
            if(angulos.equals("CA")) return new TableroCuadradoAngulos(tab);
            return new TableroCuadrado(tab);
        }

        else if(tipo.equals("T")){
            if(angulos.equals("CA")) return new TableroTriangularAngulos(tab);
            return new TableroTriangular(tab);
        }

        else if(tipo.equals("H")) return new TableroHexagonal(tab);

        return null;
    }

    public Mapa getMapa(String tipo, String angulos){
        if(tipo.equals("Q")){
            if(angulos.equals("CA")) return new TableroCuadradoAngulos();
            return new TableroCuadrado();
        }

        else if(tipo.equals("T")){
            if(angulos.equals("CA")) return new TableroTriangularAngulos();
            return new TableroTriangular();
        }

        else if(tipo.equals("H")) return new TableroHexagonal();

        return null;
    }

    public Mapa getMapa(String ID, String tipo, String angulos, String[][] tab){
        if(tipo.equals("Q")){
            if(angulos.equals("CA")) return new TableroCuadradoAngulos(ID, tab);
            return new TableroCuadrado(ID, tab);
        }

        else if(tipo.equals("T")){
            if(angulos.equals("CA")) return new TableroTriangularAngulos(ID, tab);
            return new TableroTriangular(ID, tab);
        }

        else if(tipo.equals("H")) return new TableroHexagonal(ID, tab);

        return null;
    }
}
