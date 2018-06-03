package Domini;

import Domini.Mapa.Mapa;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class UsuariTest {
    Usuari usuari;

    @Before
    public void setUp(){
        usuari = new Usuari("enric", "hola");
    }

    @Test
    public void getID() {
        Assert.assertEquals("enric", usuari.getID());
    }

    @Test
    public void setPassword() {
        usuari.setPassword("adeu");
        Assert.assertTrue(usuari.checkPassword("adeu"));
    }

    @Test
    public void changePassword() {
        Assert.assertTrue(usuari.changePassword("hola", "adeu"));
        Assert.assertTrue(usuari.checkPassword("adeu"));
    }

    @Test
    public void addAndGetPartida() {
        Partida p = ControladorPartida.crearPartida(ControladorMapa.generarHidato(), usuari.getID());
        usuari.addPartida(p);
        Assert.assertEquals(p, usuari.getPartidas().get(p.getID()));
    }

    @Test
    public void popPartida() {
        Partida p = ControladorPartida.crearPartida(ControladorMapa.generarHidato(), usuari.getID());
        usuari.addPartida(p);
        Assert.assertEquals(1, usuari.getPartidas().size());
        usuari.popPartida(p);
        Assert.assertNull(usuari.getPartidas().get(p.getID()));
    }

    @Test
    public void getPartidas() {
        Partida p1 = ControladorPartida.crearPartida(ControladorMapa.generarHidato(), usuari.getID());
        Partida p2 = ControladorPartida.crearPartida(ControladorMapa.generarHidato(), usuari.getID());
        Partida p3 = ControladorPartida.crearPartida(ControladorMapa.generarHidato(), usuari.getID());

        usuari.addPartida(p1);
        usuari.addPartida(p2);
        usuari.addPartida(p3);

        Assert.assertEquals(p1, usuari.getPartidas().get(p1.getID()));
        Assert.assertEquals(p2, usuari.getPartidas().get(p2.getID()));
        Assert.assertEquals(p3, usuari.getPartidas().get(p3.getID()));
    }

    @Test
    public void checkPassword() {
        Assert.assertTrue(usuari.checkPassword("hola"));
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