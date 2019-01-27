////////////////////////////////////////////////////////
////////////// PROGRAMAT PER VÍCTOR JUEZ ///////////////
////////////////////////////////////////////////////////
package Dades;

import java.io.*;
import java.util.*;

public class UsuariDAO {

    private UsuariDAO(){}

    /**
     * Guardar un usuari al disc
     * @param id
     * @param password
     * @param puntuacio
     * @param record
     * @param partidasID
     * @throws IOException
     */
    public static void saveUsuari(String id, String password, long puntuacio, long record, ArrayList<String> partidasID) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("id", id);
        properties.setProperty("password", password);
        properties.setProperty("puntuacio", String.valueOf(puntuacio));
        properties.setProperty("record", String.valueOf(record));
        properties.setProperty("partidasID", arrayListToString(partidasID));

        File file = new File("data/usuaris/"+id+".properties");
        FileOutputStream fileOut = new FileOutputStream(file);
        properties.store(fileOut, "Usuari | user:" +id);
        fileOut.close();
    }

    /**
     * Convertir un arrayList a un string separat per comes.
     * @param partidasID
     * @return
     */
    private static String arrayListToString(ArrayList<String> partidasID) {
        String result = "";
        for(String partidaID : partidasID){
            result+=partidaID+",";
        }
        return result;
    }

    /**
     * Carregar un usuari del disc
     * @param id
     * @param password
     * @param puntuacio
     * @param record
     * @param partidasID
     * @throws IOException
     */
    public static void loadUsuari(String id, StringBuilder password, StringBuilder puntuacio, StringBuilder record, ArrayList<String> partidasID) throws IOException {
        InputStream input = new FileInputStream("data/usuaris/"+id+".properties");

        // load a properties file
        Properties prop = new Properties();
        prop.load(input);

        // get the property value
        password.append(prop.getProperty("password"));
        puntuacio.append(prop.getProperty("puntuacio"));
        record.append(prop.getProperty("record"));
        String partidas = prop.getProperty("partidasID");
        if(!partidas.isEmpty()){
            String[] partidasParts = partidas.split(",");
            partidasID.addAll(Arrays.asList(partidasParts));
        }
    }

    /**
     * Carrega tots els usuaris del disc
     * @return un ArrayList amb l'id de tots els usuaris que estan al disc.
     */
    public static ArrayList<String> loadAllUsuaris(){
        File folder = new File("data/usuaris");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(!listOfFiles[i].getName().equals(".gitignore")) {
                    String resultName = listOfFiles[i].getName();
                    String usuariID = resultName.substring(0, resultName.length() - 11);
                    result.add(usuariID);
                }
            }
        }
        return result;
    }
}
