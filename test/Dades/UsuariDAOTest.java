package Dades;

import Domini.Usuari;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UsuariDAOTest {
    UsuariDAO usuariDAO = new UsuariDAO();

    @Test
    public void saveAndLoadUsuari() throws IOException {
        Usuari usuari = new Usuari("enric", "hola");
        usuariDAO.saveUsuari(usuari.getID(), usuari.getPassword(), usuari.getPartidasID(), usuari.getMapasID());

        String password = null;
        ArrayList<String> partidasID = new ArrayList<>();
        ArrayList<String> mapasID = new ArrayList<>();

        usuariDAO.loadUsuari(usuari.getID(), usuari.getPassword(), partidasID, mapasID);

        Assert.assertEquals(usuari.getPassword(), password);
        Assert.assertEquals(usuari.getPartidasID(), partidasID);
        Assert.assertEquals(usuari.getMapasID(), mapasID);
    }

    @Test
    public void loadAllUsuaris() {
    }
}