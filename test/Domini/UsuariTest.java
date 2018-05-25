package Domini;

import Domini.Mapa.Mapa;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsuariTest {
    Usuari usuari;
    ControladorPartida controladorPartida = new ControladorPartida();
    ControladorMapa controladorMapa = new ControladorMapa();

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
        Partida p = controladorPartida.crearPartida(controladorMapa.generarHidato());
        usuari.addPartida(p);
        Assert.assertEquals(p, usuari.getPartidas().get(p.getID()));
    }

    @Test
    public void popPartida() {
        Partida p = controladorPartida.crearPartida(controladorMapa.generarHidato());
        usuari.addPartida(p);
        Assert.assertEquals(1, usuari.getPartidas().size());
        usuari.popPartida(p);
        Assert.assertNull(usuari.getPartidas().get(p.getID()));
    }

    @Test
    public void addAndGetMapa() {
        Mapa m = controladorMapa.generarHidato();
        usuari.addMapa(m);
        Assert.assertEquals(m, usuari.getMapas().get(m.getID()));
    }

    @Test
    public void popMapa() {
        Mapa m = controladorMapa.generarHidato();
        usuari.addMapa(m);
        usuari.popMapa(m);
        Assert.assertNull(usuari.getMapas().get(m.getID()));
    }

    @Test
    public void getMapas() {
        Mapa m = controladorMapa.generarHidato();
        Mapa m1 = controladorMapa.generarHidato();
        Mapa m2 = controladorMapa.generarHidato();
        usuari.addMapa(m);
        usuari.addMapa(m1);
        usuari.addMapa(m2);

        Assert.assertEquals(3, usuari.getMapas().size());
        Assert.assertEquals(m, usuari.getMapas().get(m.getID()));
        Assert.assertEquals(m1, usuari.getMapas().get(m1.getID()));
        Assert.assertEquals(m2, usuari.getMapas().get(m2.getID()));
    }

    @Test
    public void getPartidas() {
        Partida p1 = controladorPartida.crearPartida(controladorMapa.generarHidato());
        Partida p2 = controladorPartida.crearPartida(controladorMapa.generarHidato());
        Partida p3 = controladorPartida.crearPartida(controladorMapa.generarHidato());

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
}