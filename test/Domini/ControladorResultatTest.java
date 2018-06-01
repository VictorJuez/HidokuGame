package Domini;

import Domini.Mapa.Mapa;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorResultatTest {
    ArrayList<Mapa> mapaList = new ArrayList<>();
    ArrayList<Usuari> usuariList = new ArrayList<>();
    ControladorMapa ctMapa = new ControladorMapa();

    @Before
    public void setUp() {
        ControladorUsuari.cleanUp();
        for(int i=0; i<3; ++i) {
            mapaList.add(ctMapa.generarHidato());
            usuariList.add(ControladorUsuari.insertarUsuari(String.valueOf(i), String.valueOf(i)));
        }
    }

    @Test
    public void insertarResultat() {
        Mapa mapa = mapaList.get(0);
        Usuari usuari = usuariList.get(0);
        Resultat resultat = ControladorResultat.insertarResultat(usuari, mapa, 10);

        Assert.assertEquals("Points ok", 10, resultat.getResultat());
        Assert.assertEquals("User ok", usuari.getID(), resultat.getUsuari().getID());
        Assert.assertEquals("Mapa ok", mapa.getID(), resultat.getMapa().getID());

    }

    @Test
    public void getGlobalRanking() {
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(1), 5);
        ControladorResultat.insertarResultat(usuariList.get(1), mapaList.get(0), 2);
        ControladorResultat.insertarResultat(usuariList.get(2), mapaList.get(2), 50);
        ControladorResultat.insertarResultat(usuariList.get(2), mapaList.get(2), 10);

        HashMap<String, Integer> hm = ControladorResultat.getGlobalRanking();
        int resultUser1 = hm.get(usuariList.get(0).getID());
        int resultUser2 = hm.get(usuariList.get(1).getID());
        int resultUser3 = hm.get(usuariList.get(2).getID());
        Assert.assertEquals("Same user, two different maps",15, resultUser1);
        Assert.assertEquals("One user, one map", 2, resultUser2);
        Assert.assertEquals("Same user, two same maps", 60, resultUser3);
    }

    @Test
    public void getMapRanking() {
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(1), 5);
        ControladorResultat.insertarResultat(usuariList.get(1), mapaList.get(0), 2);
        ControladorResultat.insertarResultat(usuariList.get(1), mapaList.get(0), 18);
        ControladorResultat.insertarResultat(usuariList.get(2), mapaList.get(2), 50);
        ControladorResultat.insertarResultat(usuariList.get(2), mapaList.get(2), 10);

        HashMap<Usuari, Integer> hm = ControladorResultat.getMapRanking(mapaList.get(0));
        int resultUser1 = hm.get(usuariList.get(0));
        int resultUser2 = hm.get(usuariList.get(1));
        Assert.assertEquals("Same user, two different maps",10, resultUser1);
        Assert.assertEquals("Same user, two same maps", 20, resultUser2);

    }

    @Test
    public void getUserGlobalResult() {
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 15);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(1), 20);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(2), 30);

        int r = ControladorResultat.getUserGlobalResult(usuariList.get(0));
        Assert.assertEquals(75,r);
    }

    @Test
    public void getUserMapResult() {
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 15);

        int r = ControladorResultat.getUserMapResult(usuariList.get(0), mapaList.get(0));
        Assert.assertEquals(25, r);
    }

    @Test
    public void getUserAllResults() {
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 5);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(1), 2);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(2), 50);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(2), 10);

        HashMap<Mapa, Integer> hm = ControladorResultat.getUserAllResults(usuariList.get(0));
        int resultMap1 = hm.get(mapaList.get(0));
        int resultMap2 = hm.get(mapaList.get(1));
        int resultMap3 = hm.get(mapaList.get(2));
        Assert.assertEquals("Same user, two same maps",15, resultMap1);
        Assert.assertEquals("One user, one map", 2, resultMap2);
        Assert.assertEquals("Same user, two same maps", 60, resultMap3);
    }

    @Test
    public void getMapAllResults() {
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ControladorResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 5);
        ControladorResultat.insertarResultat(usuariList.get(1), mapaList.get(0), 2);
        ControladorResultat.insertarResultat(usuariList.get(2), mapaList.get(0), 50);
        ControladorResultat.insertarResultat(usuariList.get(2), mapaList.get(0), 10);

        HashMap<Usuari, Integer> hm = ControladorResultat.getMapAllResults(mapaList.get(0));
        int resultUser1 = hm.get(usuariList.get(0));
        int resultUser2 = hm.get(usuariList.get(1));
        int resultUser3 = hm.get(usuariList.get(2));
        Assert.assertEquals("Same user, two different maps",15, resultUser1);
        Assert.assertEquals("One user, one map", 2, resultUser2);
        Assert.assertEquals("Same user, two same maps", 60, resultUser3);
    }

    @After
    public void deleteFiles() {
        for (File file : new File("data/resultats/").listFiles()) {
            if (!file.getName().equals(".gitignore")) {
                //do nothing
                file.delete();
            }
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