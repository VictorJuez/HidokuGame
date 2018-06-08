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
    public void getUsuari() {
        Assert.assertNotNull(usuari);
        Assert.assertNotNull(ControladorUsuari.getUsuari(usuari.getID()));
        Assert.assertEquals(usuari, ControladorUsuari.getUsuari(usuari.getID()));
    }

    @Test
    public void login() {
        Assert.assertTrue(ControladorUsuari.login(usuari,"hola"));
    }

    @Test
    public void getAllUsers() {
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

    @Test
    public void insertAndGetResultat(){
        ControladorUsuari.insertarResultat(usuari, 10);
        ControladorUsuari.insertarResultat(usuari, 5);
        Assert.assertEquals(usuari.getPuntuacio(), 15);
        Assert.assertEquals(usuari.getRecord(), 10);
    }

    @After
    public void deleteAllFiles() {
        String[] pathNames = {"usuaris", "mapas","partidas"};
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