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
    ArrayList<String> resultats = new ArrayList<>();

    @Test
    public void loadAndSaveResultat() throws IOException {
        Usuari usuari = ControladorUsuari.insertarUsuari("enric", "hola");
        Mapa mapa = ControladorMapa.generarHidato();
        Resultat r = new Resultat(usuari, mapa, 10);
        ResultatDAO.saveResultat(r.getUsuari().getID(), r.getMapa().getID(), String.valueOf(r.getPuntuacio()));
        resultats.add(usuari.getID()+"_"+mapa.getID());
        HashMap<String, String> resultat= ResultatDAO.loadResultat(usuari.getID(), mapa.getID());
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