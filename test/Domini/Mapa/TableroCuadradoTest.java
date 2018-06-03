package Domini.Mapa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class TableroCuadradoTest {
    MapaFactory mapaFactory = new MapaFactory();
    Mapa mapa;

    @Before
    public void setUp(){
        String[][] matrix = {{"1","?"},{"?","3"}};
        mapa = mapaFactory.getMapa("Q", "C", matrix);
    }

    @Test
    public void getTipo() {
        String tipo = mapa.getTipo();
        Assert.assertEquals(tipo, "Q");
    }

    @Test
    public void getAngulos() {
        String angulos = mapa.getAngulos();
        Assert.assertEquals("C", angulos);
    }

    @Test
    public void getMatrix() {
        String[][] matrix = mapa.getMatrix();
        String[][] expected = {{"1","?"},{"?","3"}};
        Assert.assertArrayEquals(matrix, expected);
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