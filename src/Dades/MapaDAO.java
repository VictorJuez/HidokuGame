package Dades;

import Domini.Mapa;

import java.io.*;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MapaDAO {

    public static void saveMapa(Mapa m) throws IOException {

        JSONObject obj = new JSONObject();
        obj.put("ID", m.getID());
        obj.put("topologia", m.getTipo());
        obj.put("adyacencia", m.getAngulos());

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
            //System.out.println("Successfully Copied JSON Object to File...");
            //System.out.println("\nJSON Object: " + obj);
        }

    }

    public static JSONObject loadMapa(String ID) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(
                "data/mapas/"+ID+".json"));

        JSONObject jsonMap = (JSONObject) obj;

        return jsonMap;

    }

    public static ArrayList<String> loadAllMapas(){
        ArrayList<String> mapasDisk = new ArrayList<>();
        File folder = new File("data/mapas");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                //System.out.println("File " + listOfFiles[i].getName());
                String mapName = listOfFiles[i].getName();
                mapName = mapName.substring(0, mapName.length()-5);
                mapasDisk.add(mapName);
            }
        }
        return mapasDisk;
    }
}
