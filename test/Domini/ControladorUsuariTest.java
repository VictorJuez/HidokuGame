package Domini;

import org.junit.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ControladorUsuariTest {
    ArrayList<String> usuaris = new ArrayList<>();
    static Usuari usuari;

    @BeforeClass
    public static void before(){
        usuari = ControladorUsuari.insertarUsuari("enric", "hola");
    }

    @Test
    public void insertarUsuari() {
        Assert.assertEquals(usuari.getID(), "enric");
    }

    @Test
    public void getUsuari() throws Exception {
        Assert.assertNotNull(usuari);
        Assert.assertNotNull(ControladorUsuari.getUsuari(usuari.getID()));
        Assert.assertEquals(usuari, ControladorUsuari.getUsuari(usuari.getID()));
    }

    @Test
    public void login() throws Exception {
        Assert.assertTrue(ControladorUsuari.login("enric","hola"));
    }

    @Test
    public void getAllUsers() throws Exception {
        ArrayList<String> usersList = new ArrayList<>();
        for(int i=0; i<3; ++i){
            Usuari usuari = ControladorUsuari.insertarUsuari("enric"+i, "hola");
            usersList.add(usuari.getID());
            usuaris.add(usuari.getID());
        }
        HashMap<String, Usuari> usuariHashMap = ControladorUsuari.getAllUsers();
        for(int i=0; i<3; ++i){
            Assert.assertTrue(usuariHashMap.containsKey(usersList.get(i)));
            Assert.assertEquals(ControladorUsuari.getUsuari(usersList.get(i)), usuariHashMap.get(usersList.get(i)));
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