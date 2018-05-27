package Dades;

import Domini.*;
import Domini.Mapa.Mapa;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ResultatDAOTest {
    //ControladorResultat controladorResultat = new ControladorResultat();
    ControladorMapa controladorMapa = new ControladorMapa();
    ControladorUsuari controladorUsuari = new ControladorUsuari();
    MapaDAO mapaDAO = new MapaDAO();
    ArrayList<String> resultats = new ArrayList<>();

    @Test
    public void loadAndSaveResultat() throws IOException {
        Usuari usuari = controladorUsuari.insertarUsuari("enric", "hola");
        Mapa mapa = controladorMapa.generarHidato();

        ResultatDAO resultatDAO = new ResultatDAO();
        resultatDAO.saveResultat(new Resultat(usuari, mapa, 10));
        resultats.add(usuari.getID()+"_"+mapa.getID());
        HashMap<String, String> resultat= resultatDAO.loadResultat(usuari.getID(), mapa.getID());
        Assert.assertEquals("check username", "enric", resultat.get("usuari"));
        Assert.assertEquals("check map", mapa.getID(), resultat.get("mapa"));
        Assert.assertEquals("check result", "10", resultat.get("puntuacio"));
    }

    @After
    public void deleteFiles() {
        for(String resultat : resultats){
            File file = new File("data/resultats/"+resultat+".properties");
            file.delete();
        }
    }

    @After
    public void deleteAllFiles() {
        String[] pathNames = {"usuaris", "mapas","partidas", "resultats"};
        for(String pathName : pathNames) {
            for (File file : new File("data/" +pathName+"/").listFiles()) {
                if (!file.getName().equals(".gitignore")) {
                    //do nothing
                    file.delete();
                }
            }
        }
    }
}