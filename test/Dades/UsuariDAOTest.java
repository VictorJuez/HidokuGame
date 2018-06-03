package Dades;

import Domini.ControladorMapa;
import Domini.Usuari;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UsuariDAOTest {
    ArrayList<String> usuaris = new ArrayList<>();

    @Test
    public void saveAndLoadUsuari() throws IOException {
        Usuari usuari = new Usuari("enric", "hola");
        UsuariDAO.saveUsuari(usuari.getID(), usuari.getPassword(), usuari.getPuntuacio(), usuari.getRecord(), usuari.getPartidasID());
        usuaris.add(usuari.getID());

        StringBuilder password = new StringBuilder();
        StringBuilder puntuacio = new StringBuilder();
        StringBuilder record = new StringBuilder();
        ArrayList<String> partidasID = new ArrayList<>();
        ArrayList<String> mapasID = new ArrayList<>();

        UsuariDAO.loadUsuari(usuari.getID(), password, puntuacio, record, partidasID);
        Assert.assertEquals(usuari.getPassword(), password.toString());
    }

    @Test
    public void loadAllUsers() throws IOException {
        ArrayList<String> userList = new ArrayList<>();
        for(int i =0; i<3; ++i){
            Usuari usuari = new Usuari("enric"+i, "hola");
            userList.add(usuari.getID());
            UsuariDAO.saveUsuari(usuari.getID(), usuari.getPassword(), usuari.getPuntuacio(), usuari.getRecord(), usuari.getPartidasID());
            usuaris.add(usuari.getID());
        }

        ArrayList<String> usersID = UsuariDAO.loadAllUsuaris();

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