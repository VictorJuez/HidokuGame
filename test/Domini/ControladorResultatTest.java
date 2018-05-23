package Domini;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ControladorResultatTest {
    ArrayList<Mapa> mapaList = new ArrayList<>();
    ArrayList<Usuari> usuariList = new ArrayList<>();
    ControladorResultat ctResultat;

    @Before
    public void setUp() {
        ControladorUsuari ctUsuari = new ControladorUsuari();
        ControladorMapa ctMapa = new ControladorMapa();
        ctResultat = new ControladorResultat();
        for(int i=0; i<3; ++i) {
            mapaList.add(ctMapa.generarHidato());
            usuariList.add(ctUsuari.getUsuari(String.valueOf(i)));
        }
    }

    @Test
    public void insertarResultat() {
        Mapa mapa = mapaList.get(0);
        Usuari usuari = usuariList.get(0);
        Resultat resultat = ctResultat.insertarResultat(usuari, mapa, 10);

        Assert.assertEquals("Points ok", 10, resultat.getResultat());
        Assert.assertEquals("User ok", usuari.getID(), resultat.getUsuari().getID());
        Assert.assertEquals("Mapa ok", mapa.getID(), resultat.getMapa().getID());

    }

    @Test
    public void getGlobalRanking() {
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(1), 5);
        ctResultat.insertarResultat(usuariList.get(1), mapaList.get(0), 2);
        ctResultat.insertarResultat(usuariList.get(2), mapaList.get(2), 50);
        ctResultat.insertarResultat(usuariList.get(2), mapaList.get(2), 10);

        HashMap<Usuari, Integer> hm = ctResultat.getGlobalRanking();
        int resultUser1 = hm.get(usuariList.get(0));
        int resultUser2 = hm.get(usuariList.get(1));
        int resultUser3 = hm.get(usuariList.get(2));
        Assert.assertEquals("Same user, two different maps",15, resultUser1);
        Assert.assertEquals("One user, one map", 2, resultUser2);
        Assert.assertEquals("Same user, two same maps", 60, resultUser3);
    }

    @Test
    public void getMapRanking() {
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(1), 5);
        ctResultat.insertarResultat(usuariList.get(1), mapaList.get(0), 2);
        ctResultat.insertarResultat(usuariList.get(1), mapaList.get(0), 18);
        ctResultat.insertarResultat(usuariList.get(2), mapaList.get(2), 50);
        ctResultat.insertarResultat(usuariList.get(2), mapaList.get(2), 10);

        HashMap<Usuari, Integer> hm = ctResultat.getMapRanking(mapaList.get(0));
        int resultUser1 = hm.get(usuariList.get(0));
        int resultUser2 = hm.get(usuariList.get(1));
        Assert.assertEquals("Same user, two different maps",10, resultUser1);
        Assert.assertEquals("Same user, two same maps", 20, resultUser2);

    }

    @Test
    public void getUserGlobalResult() {
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 15);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(1), 20);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(2), 30);

        int r = ctResultat.getUserGlobalResult(usuariList.get(0));
        Assert.assertEquals(75,r);
    }

    @Test
    public void getUserMapResult() {
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 15);

        int r = ctResultat.getUserMapResult(usuariList.get(0), mapaList.get(0));
        Assert.assertEquals(25, r);
    }

    @Test
    public void getUserAllResults() {
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 5);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(1), 2);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(2), 50);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(2), 10);

        HashMap<Mapa, Integer> hm = ctResultat.getUserAllResults(usuariList.get(0));
        int resultMap1 = hm.get(mapaList.get(0));
        int resultMap2 = hm.get(mapaList.get(1));
        int resultMap3 = hm.get(mapaList.get(2));
        Assert.assertEquals("Same user, two same maps",15, resultMap1);
        Assert.assertEquals("One user, one map", 2, resultMap2);
        Assert.assertEquals("Same user, two same maps", 60, resultMap3);
    }

    @Test
    public void getMapAllResults() {
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 10);
        ctResultat.insertarResultat(usuariList.get(0), mapaList.get(0), 5);
        ctResultat.insertarResultat(usuariList.get(1), mapaList.get(0), 2);
        ctResultat.insertarResultat(usuariList.get(2), mapaList.get(0), 50);
        ctResultat.insertarResultat(usuariList.get(2), mapaList.get(0), 10);

        HashMap<Usuari, Integer> hm = ctResultat.getMapAllResults(mapaList.get(0));
        int resultUser1 = hm.get(usuariList.get(0));
        int resultUser2 = hm.get(usuariList.get(1));
        int resultUser3 = hm.get(usuariList.get(2));
        Assert.assertEquals("Same user, two different maps",15, resultUser1);
        Assert.assertEquals("One user, one map", 2, resultUser2);
        Assert.assertEquals("Same user, two same maps", 60, resultUser3);
    }
}