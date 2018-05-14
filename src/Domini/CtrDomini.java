package Domini;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CtrDomini {
    private static List<MapaI> mapasList = new ArrayList<>();
    private static HashMap<String, MapaI> mapasMap = new HashMap<>();

    public CtrDomini() {
    }

    public String[][] insertarHidato(String topologia, String angulos, int filas, int columnas, String[][] tab) {
        MapaFactory mapaFactory = new MapaFactory();
        MapaI m = mapaFactory.getMapa(topologia, angulos, filas, columnas, tab);
        mapasMap.put(m.getID(), m);
        return m.getMatrix();
    }

    public static HashMap<String, MapaI> getMapasMap() {
        return mapasMap;
    }

    public MapaI generarHidato(){
        MapaI result;
        MapaController mc = new MapaController();
        result = mc.generarHidato();
        mapasMap.put(result.getID(), result);
        return result;
    }

}