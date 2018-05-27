package Domini;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ControladorUsuariTest {
    ControladorUsuari controladorUsuari = new ControladorUsuari();
    ArrayList<String> usuaris = new ArrayList<>();

    @Test
    public void insertarUsuari() {
        Usuari usuari = controladorUsuari.insertarUsuari("enric", "hola");
        usuaris.add(usuari.getID());
        Assert.assertEquals(usuari.getID(), "enric");
    }

    @Test
    public void getUsuari() {
        Usuari usuari = controladorUsuari.insertarUsuari("enric", "hola");
        usuaris.add(usuari.getID());
        Assert.assertEquals(usuari, controladorUsuari.getUsuari(usuari.getID()));
    }

    @Test
    public void login() {
        Usuari usuari = controladorUsuari.insertarUsuari("enric", "hola");
        usuaris.add(usuari.getID());
        Assert.assertTrue(controladorUsuari.login("enric","hola"));
    }

    @Test
    public void getAllUsers() {
        ArrayList<String> usersList = new ArrayList<>();
        for(int i=0; i<3; ++i){
            Usuari usuari = controladorUsuari.insertarUsuari("enric"+i, "hola");
            usersList.add(usuari.getID());
            usuaris.add(usuari.getID());
        }
        HashMap<String, Usuari> usuariHashMap = controladorUsuari.getAllUsers();
        for(int i=0; i<3; ++i){
            Assert.assertTrue(usuariHashMap.containsKey(usersList.get(i)));
            Assert.assertEquals(controladorUsuari.getUsuari(usersList.get(i)), usuariHashMap.get(usersList.get(i)));
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