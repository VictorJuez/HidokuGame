package Dades;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class UsuariDAO {

    private UsuariDAO(){}

    public static void saveUsuari(String id, String password, ArrayList<String> partidasID, ArrayList<String> mapasID) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("id", id);
        properties.setProperty("password", password);
        properties.setProperty("partidasID", arrayListToString(partidasID));
        properties.setProperty("mapasID", arrayListToString(mapasID));

        File file = new File("data/usuaris/"+id+".properties");
        FileOutputStream fileOut = new FileOutputStream(file);
        properties.store(fileOut, "Usuari | user:" +id);
        fileOut.close();
    }

    private static String arrayListToString(ArrayList<String> partidasID) {
        String result = "";
        for(String partidaID : partidasID){
            result+=partidaID+",";
        }
        return result;
    }

    public static void loadUsuari(String id, StringBuilder password, ArrayList<String> partidasID, ArrayList<String> mapasID) throws IOException {
        InputStream input = new FileInputStream("data/usuaris/"+id+".properties");

        // load a properties file
        Properties prop = new Properties();
        prop.load(input);

        // get the property value
        password.append(prop.getProperty("password"));
        String partidas = prop.getProperty("partidasID");
        String mapas = prop.getProperty("mapasID");
        if(!mapas.isEmpty()){
            String[] mapasParts = mapas.split(",");
            mapasID.addAll(Arrays.asList(mapasParts));
        }
        if(!partidas.isEmpty()){
            String[] partidasParts = partidas.split(",");
            partidasID.addAll(Arrays.asList(partidasParts));
        }
    }

    private static ArrayList<String> stringToArrayList(String str) {
        String[] parts = str.split(",");
        ArrayList<String> result = new ArrayList<>();
        for(String element : parts) result.add(element);

        return result;
    }

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
