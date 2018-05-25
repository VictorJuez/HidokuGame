package Dades;

import Domini.*;
import Domini.Mapa.Mapa;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class ResultatDAO {
    //private ControladorResultat controladorResultat = new ControladorResultat();
    private ControladorMapa controladorMapa = new ControladorMapa();
    private ControladorUsuari controladorUsuari = new ControladorUsuari();

    public void saveResultat(Resultat r) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("usuari", r.getUsuari().getID());
        properties.setProperty("mapa", r.getMapa().getID());
        properties.setProperty("resultat", String.valueOf(r.getResultat()));

        File file2 = new File("data/resultats/"+r.getUsuari().getID()+"_"+r.getMapa().getID()+".properties");
        FileOutputStream fileOut = new FileOutputStream(file2);
        properties.store(fileOut, "Result | user:" +r.getUsuari().getID()+" - map: " + r.getMapa().getID());
        fileOut.close();
    }

    public HashMap<String, String> loadResultat(String userID, String mapaID) throws IOException {
        InputStream input = new FileInputStream("data/resultats/"+userID+"_"+mapaID+".properties");

        // load a properties file
        Properties prop = new Properties();
        prop.load(input);

        // get the property value
        HashMap<String, String> result = new HashMap<>();
        String resultat = prop.getProperty("resultat");
        result.put("mapa", mapaID);
        result.put("usuari", userID);
        result.put("puntuacio", resultat);

        return result;
    }

    public HashMap<String, Integer> loadAllResults() throws IOException {
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
