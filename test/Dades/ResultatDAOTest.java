package Dades;

import Domini.*;
import Domini.Mapa.Mapa;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ResultatDAOTest {
    ControladorResultat controladorResultat = new ControladorResultat();
    ControladorMapa controladorMapa = new ControladorMapa();
    ControladorUsuari controladorUsuari = new ControladorUsuari();
    MapaDAO mapaDAO = new MapaDAO();
    ArrayList<String> resultats = new ArrayList<>();

    @Test
    public void loadAndSaveResultat() throws IOException {
        Usuari usuari = controladorUsuari.insertarUsuari("enric", "hola");
        Mapa mapa = controladorMapa.generarHidato();
        Resultat resultat = controladorResultat.insertarResultat(usuari, mapa, 10);

        ResultatDAO resultatDAO = new ResultatDAO();
        resultatDAO.saveResultat(resultat);
        resultats.add(usuari.getID()+"_"+mapa.getID());
        Resultat resultLoaded = resultatDAO.loadResultat(usuari.getID(), mapa.getID());
        Assert.assertEquals("check username", "enric", resultLoaded.getUsuari().getID());
        Assert.assertEquals("check map", mapa.getID(), resultLoaded.getMapa().getID());
        Assert.assertEquals("check result", 10, resultLoaded.getResultat());
    }

    @After
    public void deleteFiles() {
        for(String resultat : resultats){
            File file = new File("data/resultats/"+resultat+".properties");
            file.delete();
        }
    }
}