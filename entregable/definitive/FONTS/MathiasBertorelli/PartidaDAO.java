////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Dades;

import java.io.*;
import java.util.*;

import Domini.ControladorMapa;
import Domini.Mapa.Mapa;
import Domini.Mapa.MapaFactory;
import Domini.Partida;
import Domini.Usuari;

import javax.net.ssl.ManagerFactoryParameters;

public class PartidaDAO {

    private PartidaDAO(){}

    /**
     * Guarda una partida al disc
     * @param IDPartida
     * @param IDMapa
     * @param topologia
     * @param adyacencia
     * @param matrix
     * @param numerosInicio
     * @param numerosInsertados
     * @param cantidadInterrogantes
     * @param reloj
     * @param usuariID
     * @throws IOException
     */
    public static void savePartida (String IDPartida, String IDMapa, String topologia, String adyacencia, String[][] matrix,
                                    String numerosInicio, String numerosInsertados, String cantidadInterrogantes, String reloj, String usuariID) throws IOException {

        Properties properties = new Properties();
        properties.setProperty("IDPartida", IDPartida);
        properties.setProperty("IDMapa", IDMapa);
        properties.setProperty("topologia", topologia);
        properties.setProperty("adyacencia", adyacencia);

        int filas = matrix.length;
        int columnas = matrix[0].length;
        properties.setProperty("filas", String.valueOf(filas));
        properties.setProperty("columnas", String.valueOf(columnas));
        String matrixString = null;
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) {
                if(i==0 && j==0) matrixString = matrix[i][j]+",";
                else matrixString+=matrix[i][j]+",";
            }
        }
        matrixString = matrixString.substring(0,matrixString.length()-1);
        properties.setProperty("matrix", matrixString);
        //esto para saber que numeros estaban puestos ya en el inicio
        properties.setProperty("numerosInicio", numerosInicio);
        //esto los que hemos añadido nosotros
        properties.setProperty("numerosInsertados", numerosInsertados);
        properties.setProperty("cantidadInterrogantes", cantidadInterrogantes);
        properties.setProperty("reloj", reloj);
        properties.setProperty("nomUsuari", usuariID);

        File file2 = new File("data/partidas/"+IDPartida+".properties");
        FileOutputStream fileOut = new FileOutputStream(file2);
        properties.store(fileOut, "Partida: |" +IDPartida+"| properties");
        fileOut.close();
    }

    /**
     * Carrega una partida del disc
     * @param ID
     * @param userID
     * @param topologia
     * @param adyacencia
     * @param matrix
     * @param reloj
     * @param numerosInicio
     * @param numerosInsertados
     * @param cantidadInterrogantes
     * @throws IOException
     */
    public static void loadPartida (String ID, StringBuilder userID, StringBuilder topologia, StringBuilder adyacencia,
                                       ArrayList<ArrayList<String>> matrix, StringBuilder reloj, Vector<Integer> numerosInicio,
                                       Vector<Integer> numerosInsertados, StringBuilder cantidadInterrogantes) throws IOException{
        InputStream input = new FileInputStream("data/partidas/"+ID+".properties");

        // load a properties file
        Properties prop = new Properties();
        prop.load(input);

        // get the property value
        topologia.append(prop.getProperty("topologia"));
        adyacencia.append(prop.getProperty("adyacencia"));
        int filas = Integer.parseInt(prop.getProperty("filas"));
        int columnas = Integer.parseInt(prop.getProperty("columnas"));
        String matrixString = prop.getProperty("matrix");

        List<String> items = Arrays.asList(matrixString.split("\\s*,\\s*"));
        for(int i=0; i<filas; ++i){
            ArrayList<String> al = new ArrayList<>(items.subList(columnas*i,columnas*i+columnas));
            matrix.add(al);
        }

        //cargamos los atributos específicos de partida
        reloj.append(Integer.parseInt(prop.getProperty("reloj")));
        String[] numerosInicioS = prop.getProperty("numerosInicio").split(",");
        String[] numerosInsertadosS = prop.getProperty("numerosInsertados").split(",");

        //para pasar de los strings a los números de vectores como se usa en partida
        for (int i = 0; i < numerosInicioS.length; ++i) { numerosInicio.add(Integer.parseInt(numerosInicioS[i])); }
        for (int i = 0; i < numerosInsertadosS.length; ++i) { numerosInsertados.add(Integer.parseInt(numerosInsertadosS[i])); }

        cantidadInterrogantes.append(prop.getProperty("cantidadInterrogantes"));
        userID.append(prop.getProperty("nomUsuari"));
    }

    //restablece los valores de la partida.

    /**
     * Carrega totes les partides que hi ha guardades al disc
     * @return ArrayList amb el ID de les partides.
     */
    public static ArrayList<String> loadAllPartidas()
    {
        ArrayList<String> partidasDisk = new ArrayList<>();
        File folder = new File("data/partidas");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(!listOfFiles[i].getName().equals(".gitignore")) {
                    String partidaName = listOfFiles[i].getName();
                    System.out.println(partidaName);
                    partidaName = partidaName.substring(0, partidaName.length() - 11);
                    partidasDisk.add(partidaName);
                }
            }
        }
        return partidasDisk;
    }

    /**
     * Borra la partida seleccionada
     * @param partidaID
     */

    public static void deletePartida(String partidaID) {
        File file = new File("data/partidas/"+partidaID+".properties");

        file.delete();
    }
}
