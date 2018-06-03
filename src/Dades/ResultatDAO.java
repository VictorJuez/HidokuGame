package Dades;

import Domini.*;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class ResultatDAO {

    private ResultatDAO(){}

    public static void saveResultat(String usuari, String mapa, String puntuacio) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("usuari", usuari);
        properties.setProperty("mapa", mapa);
        properties.setProperty("puntuacio", puntuacio);

        File file2 = new File("data/resultats/"+usuari+"_"+mapa+".properties");
        FileOutputStream fileOut = new FileOutputStream(file2);
        properties.store(fileOut, "Result | user:" +usuari+" - map: " + mapa);
        fileOut.close();
    }

    public static HashMap<String, String> loadResultat(String userID, String mapaID) throws IOException {
        InputStream input = new FileInputStream("data/resultats/"+userID+"_"+mapaID+".properties");

        // load a properties file
        Properties prop = new Properties();
        prop.load(input);

        // get the property value
        HashMap<String, String> result = new HashMap<>();
        String puntuacio = prop.getProperty("puntuacio");
        result.put("mapa", mapaID);
        result.put("usuari", userID);
        result.put("puntuacio", puntuacio);

        return result;
    }

    public static HashMap<String, Integer> loadAllResults() throws IOException {
        HashMap<String, Integer> resultsDisk = new HashMap<String, Integer>();
        File folder = new File("data/resultats");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(!listOfFiles[i].getName().equals(".gitignore")) {
                    String resultName = listOfFiles[i].getName();
                    resultName = resultName.substring(0, resultName.length() - 11);
                    String parts[] = resultName.split("_");
                    HashMap<String, String> r = loadResultat(parts[0], parts[1]);
                    String puntuacio = r.get("puntuacio");
                    resultsDisk.put(resultName, Integer.valueOf(puntuacio));
                }
            }
        }
        return resultsDisk;
    }
}
