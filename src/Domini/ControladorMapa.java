package Domini;

import Dades.MapaDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class ControladorMapa {
    private static HashMap<String, Mapa> mapasMap = new HashMap<>();
    private ArrayList<String> mapasDisk = new ArrayList<>();
    private MapaDAO md = new MapaDAO();
    public ControladorMapa(){}
    /**
     * Establece si la posicion del hidato i,j debe ser un # o no.
     * @param i,j fila y columna de la casilla a comprobar
     * @param max_fil El numero de filas del hidato
     * @param max_col El numero de columnas del hidato
     * @return Boolean indicando si la casilla sera # o no.
     */
    protected boolean holeChecker(int i, int j, int max_fil, int max_col)
    {
        int randValue = ThreadLocalRandom.current().nextInt(0, 100+1);
        //ahora vamos a ver si la casilla está en el borde.
        if (i == 0 || j == 0 || i == max_fil - 1 || j == max_col - 1) if (randValue > 55) return true;
        else if ((i == 0 && j == max_col - 1) || (i == max_fil - 1 && j == max_col - 1)
                || (i == max_fil - 1 && j == 0) || (i == 0 && j == 0)) if (randValue > 55) return true;
        //else if ((i < max_fil - 1 && j < max_col - 1) && tablero[i-1][j] == "#" || tablero[i][j+1] == "#" || tablero[i+1][j] == "#" || tablero[i][j-1] == "#")
        //if (randValue > 55) return true;
        return false;
    }

    protected String[][] completePath(Integer[][] casillas_usadas, int numero_fil, int numero_col){
        String[][] tablero = new String[numero_fil][numero_col];
        int randValue;
        //para tratar el array
        for (int i = 0; i < numero_fil; ++i) {
            for (int j = 0; j < numero_col; ++j) {
                if (casillas_usadas[i][j] == -2) tablero[i][j] = "#";
                else if (casillas_usadas[i][j] != -1) {
                    randValue = ThreadLocalRandom.current().nextInt(0, 100 + 1);
                    if (randValue > 85) tablero[i][j] = String.valueOf(casillas_usadas[i][j]); //85
                    else tablero[i][j] = "?";
                }
                else
                {
                    if (holeChecker(i, j, numero_fil, numero_col)) tablero[i][j] = "#";
                    else tablero[i][j] = "*";
                }
            }
        }
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
    public Mapa generarHidato() {
        int numero_fil, numero_col; //número de filas y columnas del tablero
        int tipo_adyacencia; //1 -> costados, 2 -> costados y angulos
        int topologia; //1-> cuadrados, 2-> triangilos, 3-> hexagonos
        int num_casillas; //auxiliar para el pathfinder
        int casillas_validas; //casillas que contarán para la solución del Hidato.
        String[][] tablero;


        //----------------------------DEFINICIÓN DE LOS PARÁMETROS DEL HIDATO:
        numero_fil = ThreadLocalRandom.current().nextInt(5, 10 + 1);
        numero_col = ThreadLocalRandom.current().nextInt(5, 10 + 1);

        tipo_adyacencia = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        topologia = ThreadLocalRandom.current().nextInt(1, 3+1);
        num_casillas = numero_fil * numero_col;

        //tendrá un valor entre un cuarto del número de casillas y 3 cuartos.
        casillas_validas = ThreadLocalRandom.current().nextInt(num_casillas / 5, (num_casillas / 5) * 2);

        tablero = new String[numero_fil][numero_col];
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

    public Mapa insertarHidato(String topologia, String angulos, String[][] tab) {
        MapaFactory mapaFactory = new MapaFactory();
        Mapa m = mapaFactory.getMapa(topologia, angulos, tab);
        mapasMap.put(m.getID(), m);
        return m;
    }

    public HashMap<String, Mapa> getAllMapas() throws IOException {
        loadAllMapsDisk();
        for(int i=0; i<mapasDisk.size(); ++i){
            String id = mapasDisk.get(i);
            if(mapasMap.get(id) == null) mapasMap.put(id, loadMapaDisk(id));
        }
        return mapasMap;
    }

    public Mapa getMapa(String ID) throws IOException {
        if(mapasMap.get(ID) == null){
            return loadMapaDisk(ID);
        }

        return mapasMap.get(ID);
    }

    public void saveMapa(Mapa m) throws IOException {
        md.saveMapa(m);
    }

    public void borrarMapa(Mapa mapa) {
        mapasMap.remove(mapa.getID());
        md.borrarMapa(mapa);
    }

    private Mapa loadMapaDisk(String ID) throws IOException {
        return md.loadMapa(ID);

    }

    private void loadAllMapsDisk(){
        mapasDisk = md.loadAllMapas();
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
