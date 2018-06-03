package Domini;

import Dades.MapaDAO;
import Domini.Mapa.Mapa;
import Domini.Mapa.MapaFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class ControladorMapa {
    private static HashMap<String, Mapa> mapasMap = new HashMap<>();
    private static ArrayList<String> mapasDisk = new ArrayList<>();
    private ControladorMapa(){}
    /**
     * Establece si la posicion del hidato i,j debe ser un # o no.
     * @param i,j fila y columna de la casilla a comprobar
     * @param max_fil El numero de filas del hidato
     * @param max_col El numero de columnas del hidato
     * @return Boolean indicando si la casilla sera # o no.
     */
    private static boolean holeChecker(int i, int j, int max_fil, int max_col, String[][] matrix)
    {
        int randValue = ThreadLocalRandom.current().nextInt(0, 100+1);
        //ahora vamos a ver si la casilla está en el borde.
        //comprueba la columna minima y la extrema de la matriz y las filas tmb
        if (i == 0 || j == 0 || i == max_fil - 1 || j == max_col - 1) if (randValue > 55) return true;
        //comprueba las 4 esquinas de la matriz
        else if ((i == 0 && j == max_col - 1) || (i == max_fil - 1 && j == max_col - 1)
                || (i == max_fil - 1 && j == 0) || (i == 0 && j == 0)) if (randValue > 55) return true;
//        else if (matrix[i-1][j].equals("#") || matrix[i+1][j].equals("#") || matrix[i][j-1].equals("#") || matrix[i][j+1].equals("#")) if (randValue > 55) return true;
        return false;
    }

    private static String[][] completePath(Integer[][] casillas_usadas, int numero_fil, int numero_col){
        String[][] tablero = new String[numero_fil][numero_col];
        int randValue;
        int biggestNumber = 0;
        int biggestNumPosi = 0;
        int biggestNumPosj = 0;
        //para tratar el array
        for (int i = 0; i < numero_fil; ++i) {
            for (int j = 0; j < numero_col; ++j) {
                if (casillas_usadas[i][j] == -2) tablero[i][j] = "#";
                else if (casillas_usadas[i][j] != -1) {
                    if (casillas_usadas[i][j] == 1) tablero[i][j] = "1";
                    else
                    {
                        if (casillas_usadas[i][j] > biggestNumber)
                        {
                            biggestNumber = casillas_usadas[i][j];
                            biggestNumPosi = i;
                            biggestNumPosj = j;
                        }
                        randValue = ThreadLocalRandom.current().nextInt(0, 100 + 1);
                        if (randValue > 85) tablero[i][j] = String.valueOf(casillas_usadas[i][j]); //85
                        else tablero[i][j] = "?";
                    }
                }
                else
                {
                    if (holeChecker(i, j, numero_fil, numero_col, tablero)) tablero[i][j] = "#";
                    else tablero[i][j] = "*";
                }
            }
        }
        tablero[biggestNumPosi][biggestNumPosj] = String.valueOf(biggestNumber);
        return tablero;
    }

    /**
     * Genera un hidato de cualquier tipo y adyacencia
     * @return un Pair donde:<br>
     *     <ul>
     *         <li>su primera posicion contiene un array con el índice del hidato, es decir [Topologia,Adyacencia,numeroFilas,numeroColumnas]
     *         del hidato generado</li>
     *         <li>su segunda posicion contiene una matriz de Strings con el hidato generado</li>
     *     </ul>
     */
    public static Mapa generarHidato() {
        int numero_fil, numero_col; //número de filas y columnas del tablero
        int tipo_adyacencia; //1 -> costados, 2 -> costados y angulos
        int topologia; //1-> cuadrados, 2-> triangilos, 3-> hexagonos
        int num_casillas; //auxiliar para el pathfinder
        int casillas_validas; //casillas que contarán para la solución del Hidato.
        String[][] tablero;


        //----------------------------DEFINICIÓN DE LOS PARÁMETROS DEL HIDATO:
        numero_fil = ThreadLocalRandom.current().nextInt(5, 15 + 1);
        numero_col = ThreadLocalRandom.current().nextInt(5, 15 + 1);

        tipo_adyacencia = ThreadLocalRandom.current().nextInt(1, 2 + 1);    //1 - 2+1
        topologia = ThreadLocalRandom.current().nextInt(1, 3+1);               //1 - 3+1
        num_casillas = numero_fil * numero_col;

        //tendrá un valor entre un cuarto del número de casillas y 3 cuartos.
        casillas_validas = ThreadLocalRandom.current().nextInt(num_casillas / 5, (num_casillas / 5) * 2);

        Integer[][] casillas_usadas = new Integer[numero_fil][numero_col];

        //----------------------------DEFINICIÓN DEL HIDATO:
        //los while sirven para saber si se ha generado bien o se ha encerrado solo y no ha podido generar el hidato.
        MapaFactory mapaFactory = new MapaFactory();
        String[] tipos = {"Q","T","H"};
        String[] angulos = {"C", "CA"};
        Mapa result = mapaFactory.getMapa(tipos[topologia-1],angulos[tipo_adyacencia-1]);

        casillas_usadas = result.pathFinder(casillas_validas, numero_fil, numero_col);
        while (casillas_usadas[0][0] == -5) casillas_usadas = result.pathFinder(casillas_validas, numero_fil, numero_col);

        tablero = completePath(casillas_usadas, numero_fil, numero_col);

        result.setMatrix(tablero);

        mapasMap.put(result.getID(), result);
        return result;
    }

    public static Mapa insertarHidato(String topologia, String angulos, String[][] tab) {
        MapaFactory mapaFactory = new MapaFactory();
        Mapa m = mapaFactory.getMapa(topologia, angulos, tab);
        mapasMap.put(m.getID(), m);
        return m;
    }

    public static ArrayList<String> getAllSavedMaps() throws IOException {
        loadAllMapsDisk();
        for(int i=0; i<mapasDisk.size(); ++i){
            String id = mapasDisk.get(i);
            if(mapasMap.get(id) == null) mapasMap.put(id, loadMapaDisk(id));
        }

        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> mapsID = new ArrayList<String>(mapasMap.keySet());
        for(String mapID : mapsID){
            if(getMapa(mapID).getName() != null) result.add(mapID);
        }

        return result;
    }

    public static Mapa getMapa(String ID) {
        if(mapasMap.get(ID) == null){
            try {
                return loadMapaDisk(ID);
            } catch (IOException e) {
                return null;
            }
        }

        return mapasMap.get(ID);
    }

    public static void saveMapa(Mapa m, String name) {
        try {
            m.setName(name);
            MapaDAO.saveMapa(m.getID(), name, m.getTipo(), m.getAngulos(), m.getFilas(), m.getColumnas(), m.getMatrix());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void borrarMapa(Mapa mapa) {
        mapasMap.remove(mapa.getID());
        MapaDAO.borrarMapa(mapa.getID());
    }

    private static Mapa loadMapaDisk(String ID) throws IOException {
        StringBuilder topologia = new StringBuilder();
        StringBuilder adyacencia = new StringBuilder();
        StringBuilder name = new StringBuilder();
        ArrayList<ArrayList<String>> matrix = new ArrayList<ArrayList<String>>();
        MapaDAO.loadMapa(ID, name, topologia, adyacencia, matrix);

        String[][] matrixResult = new String[matrix.size()][matrix.get(0).size()];
        for(int i=0; i<matrixResult.length; ++i){
            matrixResult[i] = matrix.get(i).toArray(matrixResult[i]);
        }

        MapaFactory mapaFactory = new MapaFactory();
        Mapa mapa = mapaFactory.getMapa(ID, name.toString(), topologia.toString(), adyacencia.toString(), matrixResult);

        mapasMap.put(ID, mapa);
        return mapa;
    }

    private static void loadAllMapsDisk(){
        mapasDisk = MapaDAO.loadAllMapas();
    }
}
