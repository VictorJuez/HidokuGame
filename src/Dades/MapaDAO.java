package Dades;

import Domini.Mapa;
import jdk.nashorn.internal.runtime.JSONFunctions;

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MapaDAO {

    public static void saveMapa(Mapa m) throws IOException {

        JSONObject obj = new JSONObject();
        obj.put("ID", m.getID());
        obj.put("filas", String.valueOf(m.getFilas()));
        obj.put("columnas", String.valueOf(m.getColumnas()));
        obj.put("topologia", m.getTipo());
        obj.put("adyacencias", m.getAngulos());

        JSONArray matrix = new JSONArray();

        String[][] tablero = m.getMatrix();
        for(int i=0; i<m.getFilas(); ++i) {
            JSONArray line = new JSONArray();
            for(int j=0; j<m.getColumnas(); ++j){
                line.add(tablero[i][j]);
            }
            matrix.add(line);
        }

        obj.put("matrix", matrix);

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter("data/mapas/"+m.getID()+".json")) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        }

    }

    public static void loadMapa(String ID) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(
                "data/mapas/"+ID+".json"));

        JSONObject jsonObject = (JSONObject) obj;
        System.out.println("JSON lodaded: "+((JSONObject) obj).toJSONString());

    }
}
