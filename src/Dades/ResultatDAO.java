package Dades;

import Domini.ControladorMapa;
import Domini.ControladorResultat;
import Domini.Mapa.Mapa;
import Domini.Mapa.MapaFactory;
import Domini.Resultat;
import Domini.Usuari;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class ResultatDAO {
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

    public Resultat loadResultat(String userID, String mapaID) throws IOException {
        InputStream input = new FileInputStream("data/resultats/"+userID+"_"+mapaID+".properties");

        // load a properties file
        Properties prop = new Properties();
        prop.load(input);

        // get the property value
        String resultat = prop.getProperty("resultat");

        ControladorResultat controladorResultat = new ControladorResultat();
        ControladorMapa controladorMapa = new ControladorMapa();
        Usuari usuari = new Usuari(userID);
        Mapa mapa = controladorMapa.getMapa(mapaID);
        Resultat r = controladorResultat.insertarResultat(usuari, mapa, Integer.parseInt(resultat));
        return r;
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
                    Resultat r = loadResultat(parts[0], parts[1]);
                    int puntuacio = r.getResultat();
                    resultsDisk.put(resultName, puntuacio);
                }
            }
        }
        return resultsDisk;
    }
}
