package Dades;

import Domini.ControladorMapa;
import Domini.Usuari;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UsuariDAOTest {
    UsuariDAO usuariDAO = new UsuariDAO();
    ArrayList<String> usuaris = new ArrayList<>();
    ControladorMapa controladorMapa = new ControladorMapa();

    @Test
    public void saveAndLoadUsuari() throws IOException {
        Usuari usuari = new Usuari("enric", "hola");
        usuari.addMapa(controladorMapa.generarHidato());
        usuari.addMapa(controladorMapa.generarHidato());
        usuariDAO.saveUsuari(usuari.getID(), usuari.getPassword(), usuari.getPartidasID(), usuari.getMapasID());
        usuaris.add(usuari.getID());

        StringBuilder password = new StringBuilder();
        ArrayList<String> partidasID = new ArrayList<>();
        ArrayList<String> mapasID = new ArrayList<>();

        usuariDAO.loadUsuari(usuari.getID(), password, partidasID, mapasID);
        Assert.assertEquals(usuari.getPassword(), password.toString());

        for(String mapaID : mapasID){
            Assert.assertTrue(usuari.getMapasID().contains(mapaID));
        }
    }

    @Test
    public void loadAllUsers() throws IOException {
        ArrayList<String> userList = new ArrayList<>();
        for(int i =0; i<3; ++i){
            Usuari usuari = new Usuari("enric"+i, "hola");
            userList.add(usuari.getID());
            usuariDAO.saveUsuari(usuari.getID(), usuari.getPassword(), usuari.getPartidasID(), usuari.getMapasID());
            usuaris.add(usuari.getID());
        }

        ArrayList<String> usersID = usuariDAO.loadAllUsuaris();

        for(int i=0; i<3; ++i){
            Assert.assertTrue(userList.contains(usersID.get(i)));
        }

    }

    @After
    public void deleteFiles() {
        for(String usuari : usuaris){
            File file = new File("data/usuaris/"+usuari+".properties");
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