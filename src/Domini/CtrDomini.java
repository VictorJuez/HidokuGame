package Domini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CtrDomini {
    private static List<Mapa> mapasList = new ArrayList<>();
    private static HashMap<String, Mapa> mapasMap = new HashMap<>();

    public CtrDomini() {
    }

    public String[][] insertarHidato(String topologia, String angulos, int filas, int columnas, String[][] tab) {
        MapaFactory mapaFactory = new MapaFactory();
        Mapa m = mapaFactory.getMapa(topologia, angulos, filas, columnas, tab);
        mapasMap.put(m.getID(), m);
        return m.getMatrix();
    }

    public static HashMap<String, Mapa> getMapasMap() {
        return mapasMap;
    }

    public Mapa generarHidato(){
        Mapa result;
        MapaController mc = new MapaController();
        result = mc.generarHidato();
        mapasMap.put(result.getID(), result);
        return result;
    }

}