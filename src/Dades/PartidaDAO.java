package Dades;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import Domini.Mapa.Mapa;
import Domini.Mapa.MapaFactory;
import Domini.Partida;

public class PartidaDAO {

    public static void savePartida (Partida p) throws IOException {

        Properties properties = new Properties();
        properties.setProperty("ID", p.getID());
        properties.setProperty("topologia", p.getTipoMapa());
        properties.setProperty("adyacencia", p.getAngulosMapa());
        properties.setProperty("filas", String.valueOf(p.getFilasMapa()));
        properties.setProperty("columnas", String.valueOf(p.getColumnasMapa()));
        String matrixString = null;
        String[][] matrix = p.getMatrixMapa();
        for(int i=0; i<p.getFilasMapa(); ++i){
            for(int j=0; j<p.getColumnasMapa(); ++j) {
                if(i==0 && j==0) matrixString = matrix[i][j]+",";
                else matrixString+=matrix[i][j]+",";
            }
        }
        matrixString = matrixString.substring(0,matrixString.length()-1);
        properties.setProperty("matrix", matrixString);

        //esto para saber que numeros estaban puestos ya en el inicio
        String numerosInicio = p.getNumerosInicio();
        properties.setProperty("numerosInicio", numerosInicio);
        //esto los que hemos añadido nosotros
        String numerosInsertados = p.getNumerosInsertados();
        properties.setProperty("numerosRestantes", numerosInsertados);

        properties.setProperty("reloj", String.valueOf(p.getReloj()));

        File file2 = new File("data/partidas/"+p.getID()+".properties");
        FileOutputStream fileOut = new FileOutputStream(file2);
        properties.store(fileOut, "Partida: |" +p.getID()+"| properties");
        fileOut.close();
    }


    public static Partida loadPartida (String ID) throws IOException{
        InputStream input = new FileInputStream("data/mapas/"+ID+".properties");

        // load a properties file
        Properties prop = new Properties();
        prop.load(input);

        // get the property value
        String topologia = prop.getProperty("topologia");
        String adyacencia = prop.getProperty("adyacencia");
        int filas = Integer.parseInt(prop.getProperty("filas"));
        int columnas = Integer.parseInt(prop.getProperty("columnas"));
        String matrixString = prop.getProperty("matrix");
        String[][] matrix = new String[filas][columnas];

        //cargar la matriz del mapa
        List<String> items = Arrays.asList(matrixString.split("\\s*,\\s*"));
        int k=0;
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) {
                matrix[i][j] = items.get(k++);
            }
        }

        //cargamos los atributos específicos de partida
        double reloj = Double.parseDouble(prop.getProperty("reloj"));

        String numerosInicioS = prop.getProperty("numerosInicio");
        String numerosRestantesS = prop.getProperty("numerosRestantes");
        int cantidadInterrogantes = Integer.parseInt(prop.getProperty("cantidadInterrogantes"));

        //tratamiento de string a Vector<Integer>
        Vector<Integer> numerosInicio = new Vector<Integer>();
        Vector<Integer> numerosRestantes = new Vector<Integer>();

        for (int i = 0; i < numerosInicioS.length(); ++i)
        {
            if (numerosInicioS.charAt(i) != ',') numerosInicio.add(Character.getNumericValue(numerosInicioS.charAt(i)));
        }

        MapaFactory mapaFactory = new MapaFactory();
        Mapa m = mapaFactory.getMapa(ID, topologia, adyacencia, matrix);

        Partida p = new Partida(m);
        //ya tiene el mapa bien puesto, lo único que he de modificar son numerosInicio, numerosRestantes, reloj,
        // ID (para que tenga el mismo y poder sobreescribir)
        setPartida(ID, reloj, p);

        return p; //retorna la partida tal y como la dejamos
    }

    //restablece los valores de la partida.
    private static void setPartida (String ID, double reloj, Partida p)
    {
        p.setID(ID);
        p.setReloj(reloj);
    }
}
