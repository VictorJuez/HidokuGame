package Dades;

import Domini.Mapa;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import Domini.MapaFactory;

public class MapaDAO {

    public static void saveMapa(Mapa m) throws IOException {

        Properties properties = new Properties();
        properties.setProperty("ID", m.getID());
        properties.setProperty("topologia", m.getTipo());
        properties.setProperty("adyacencia", m.getAngulos());
        properties.setProperty("filas", String.valueOf(m.getFilas()));
        properties.setProperty("columnas", String.valueOf(m.getColumnas()));
        String matrixString = null;
        String[][] matrix = m.getMatrix();
        for(int i=0; i<m.getFilas(); ++i){
            for(int j=0; j<m.getColumnas(); ++j) {
                if(i==0 && j==0) matrixString = matrix[i][j]+",";
                else matrixString+=matrix[i][j]+",";
            }
        }
        matrixString = matrixString.substring(0,matrixString.length()-1);
        properties.setProperty("matrix", matrixString);

        File file2 = new File("data/mapas/"+m.getID()+".properties");
        FileOutputStream fileOut = new FileOutputStream(file2);
        properties.store(fileOut, "Mapa: |" +m.getID()+"| properties");
        fileOut.close();
    }

    public static Mapa loadMapa(String ID) throws IOException{
        InputStream input = new FileInputStream("data/mapas/"+ID+".properties");

        // load a properties file
        Properties prop = new Properties();
        prop.load(input);

        // get the property value
        String topologia = prop.getProperty("topologia");
        String adyacencia = prop.getProperty("adyacencia");
        String matrixString = prop.getProperty("matrix");
        int filas = Integer.parseInt(prop.getProperty("filas"));
        int columnas = Integer.parseInt(prop.getProperty("columnas"));
        String[][] matrix = new String[filas][columnas];

        List<String> items = Arrays.asList(matrixString.split("\\s*,\\s*"));
        int k=0;
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) {
                matrix[i][j] = items.get(k++);
            }
        }
        MapaFactory mapaFactory = new MapaFactory();
        return mapaFactory.getMapa(ID, topologia, adyacencia, matrix);
    }

    public static ArrayList<String> loadAllMapas(){
        ArrayList<String> mapasDisk = new ArrayList<>();
        File folder = new File("data/mapas");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(!listOfFiles[i].getName().equals(".gitignore")) {
                    String mapName = listOfFiles[i].getName();
                    System.out.println(mapName);
                    mapName = mapName.substring(0, mapName.length() - 11);
                    mapasDisk.add(mapName);
                }
            }
        }
        return mapasDisk;
    }

    public void borrarMapa(Mapa mapa) {
        File file = new File("data/mapas/"+mapa.getID()+".properties");

        file.delete();
    }
}
