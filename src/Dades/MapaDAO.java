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
        System.out.println(matrixString);
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

        // get the property value and print it out
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

        //printTablero(matrix);
        MapaFactory mapaFactory = new MapaFactory();
        return mapaFactory.getMapa(ID, topologia, adyacencia, matrix);
    }

    public static ArrayList<String> loadAllMapas(){
        ArrayList<String> mapasDisk = new ArrayList<>();
        File folder = new File("data/mapas");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                //System.out.println("File " + listOfFiles[i].getName());
                String mapName = listOfFiles[i].getName();
                mapName = mapName.substring(0, mapName.length()-11);
                mapasDisk.add(mapName);
            }
        }
        return mapasDisk;
    }

    public static void printTablero(String[][] matrix){
        int filas = matrix.length;
        int columnas = matrix[0].length;
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) {
                System.out.print(matrix[i][j]);
                if(j!=columnas-1) System.out.print(",");
            }
            System.out.print("\n");
        }
        System.out.println();
    }
}
