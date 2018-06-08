////////////////////////////////////////////////////////
////////////// PROGRAMAT PER VÍCTOR JUEZ ///////////////
////////////////////////////////////////////////////////
package Dades;

import Domini.Mapa.Mapa;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import Domini.Mapa.MapaFactory;

public class MapaDAO {

    private MapaDAO(){}

    /**
     * Guardar un mapa al disc
     * @param ID
     * @param name
     * @param topologia
     * @param adyacencias
     * @param filas
     * @param columnas
     * @param matriz
     * @throws IOException
     */
    public static void saveMapa(String ID, String name, String topologia, String adyacencias, int filas, int columnas, String[][] matriz) throws IOException {

        Properties properties = new Properties();
        properties.setProperty("ID", ID);
        properties.setProperty("name", name);
        properties.setProperty("topologia", topologia);
        properties.setProperty("adyacencia", adyacencias);
        properties.setProperty("filas", String.valueOf(filas));
        properties.setProperty("columnas", String.valueOf(columnas));
        String matrixString = null;
        String[][] matrix = matriz;
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) {
                if(i==0 && j==0) matrixString = matrix[i][j]+",";
                else matrixString+=matrix[i][j]+",";
            }
        }
        matrixString = matrixString.substring(0,matrixString.length()-1);
        properties.setProperty("matrix", matrixString);

        File file2 = new File("data/mapas/"+ID+".properties");
        FileOutputStream fileOut = new FileOutputStream(file2);
        properties.store(fileOut, "Mapa: |" +ID+"| properties");
        fileOut.close();
    }

    /**
     * Carregar un mapa del disc
     * @param ID
     * @param name
     * @param topologia
     * @param adyacencia
     * @param matrix
     * @throws IOException
     */
    public static void loadMapa(String ID, StringBuilder name, StringBuilder topologia, StringBuilder adyacencia, ArrayList<ArrayList<String>> matrix) throws IOException{
        InputStream input = new FileInputStream("data/mapas/"+ID+".properties");

        // load a properties file
        Properties prop = new Properties();
        prop.load(input);

        // get the property value
        name.append(prop.getProperty("name"));
        topologia.append(prop.getProperty("topologia"));
        adyacencia.append(prop.getProperty("adyacencia"));
        String matrixString = prop.getProperty("matrix");
        int filas = Integer.parseInt(prop.getProperty("filas"));
        int columnas = Integer.parseInt(prop.getProperty("columnas"));
        String[][] aux = new String[filas][columnas];

        List<String> items = Arrays.asList(matrixString.split("\\s*,\\s*"));
        for(int i=0; i<filas; ++i){
            ArrayList<String> al = new ArrayList<>(items.subList(columnas*i,columnas*i+columnas));
            matrix.add(al);
        }
    }

    /**
     * Carregar tots els mapes del disc
     * @return ArrayList amb tots els id's dels mapes que estan al disc.
     */
    public static ArrayList<String> loadAllMapas(){
        ArrayList<String> mapasDisk = new ArrayList<>();
        File folder = new File("data/mapas");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(!listOfFiles[i].getName().equals(".gitignore")) {
                    String mapName = listOfFiles[i].getName();
                    mapName = mapName.substring(0, mapName.length() - 11);
                    mapasDisk.add(mapName);
                }
            }
        }
        return mapasDisk;
    }

    /**
     * Borra un mapa del disc.
     * @param mapaID
     */
    public static void borrarMapa(String mapaID) {
        File file = new File("data/mapas/"+mapaID+".properties");

        file.delete();
    }
}
