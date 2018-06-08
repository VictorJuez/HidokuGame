////////////////////////////////////////////////////////
////////////// PROGRAMAT PER VÍCTOR JUEZ ///////////////
////////////////////////////////////////////////////////
package Domini.Mapa;

public class MapaFactory {
    /**
     * Crea un mapa dados los parametros de entreada
     * @param tipo
     * @param angulos
     * @param tab
     * @return
     */
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

    /**
     * Crea un mapa dados los parametros de entreada
     * @param tipo
     * @param angulos
     * @return
     */
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

    /**
     * Crea un mapa dados los parametros de entreada
     * @param ID
     * @param name
     * @param tipo
     * @param angulos
     * @param tab
     * @return
     */
    public Mapa getMapa(String ID, String name, String tipo, String angulos, String[][] tab){
        if(tipo.equals("Q")){
            if(angulos.equals("CA")) return new TableroCuadradoAngulos(ID, name, tab);
            return new TableroCuadrado(ID, name, tab);
        }

        else if(tipo.equals("T")){
            if(angulos.equals("CA")) return new TableroTriangularAngulos(ID, name, tab);
            return new TableroTriangular(ID, name, tab);
        }

        else if(tipo.equals("H")) return new TableroHexagonal(ID, name, tab);

        return null;
    }
}
