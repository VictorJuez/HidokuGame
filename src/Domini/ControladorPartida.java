////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Domini;

import Dades.PartidaDAO;
import Domini.Mapa.Mapa;
import Domini.Mapa.MapaFactory;
import Domini.Mapa.UtilsMapaDecorator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class ControladorPartida
{
    private static String partidaEnCurso;
    private static HashMap<String, Partida> partidasMap = new HashMap<>();
    private static HashMap<String, Partida> partidasUsuariActiu = new HashMap<>();
    private static ArrayList<String> partidasDisk = new ArrayList<>();

    private ControladorPartida() {}

    /**
     * Selecciona una partida de les carregades al programa y la marca com activa
     * @param ID
     */

    public static void seleccionarPartida (String ID)
    {
        partidaEnCurso = ID;
        Partida p = partidasMap.get(ID);
        p.activarContador();
    }

    /**
     * Ens retorna el ID de la partida que està activa en el moment que es crida la funció
     * @return String amb el ID de la partida
     */

    public static String getPartidaEnCurso() {
        return partidaEnCurso;
    }

    /**
     * Retorna el ID de l'usuari propietari d'una partida
     * @param ID
     * @return String amb el ID de l'usuari
     */

    public static String getUsuariPartida(String ID) {
        Partida p = partidasMap.get(ID);
        return p.getUsuari();
    }

    /**
     * Crea una partida amb un mapa concret i per a l'usuari seleccionat
     * @param m
     * @param usuari
     * @return Partida creada amb els paràmetres enviats.
     */

    public static Partida crearPartida(Mapa m, String usuari)
    {
        //crea una partida y la añade al hashmap de partidas existentes.
        Partida p = new Partida(m, usuari);
        partidasMap.put(p.getID(), p);
        //ControladorUsuari.addPartidaToUser(usuari, p.getID());
        return p;
    }

    /**
     * Crea una partida per a l'usuari seleccionat amb un mapa generat aleatoriament
     * @param usuari
     * @return Partida amb un mapa aleatori per a l'usuari seleccionat
     */

    public static Partida crearPartidaRandom(String usuari)
    {
        Mapa mapaRandom = ControladorMapa.generarHidato();
        Partida p = new Partida(mapaRandom, usuari);
        partidasMap.put(p.getID(), p);
        //ControladorUsuari.addPartidaToUser(usuari, p.getID());
        return p;
    }

    /**
     * Retorna un HashMap amb totes les partides que hi ha al disc, amb KEY -> ID y VALUE -> la partida
     * @return HashMap
     * @throws IOException
     */

    public static HashMap<String, Partida> getAllPartidas () throws IOException {
        loadAllPartidasDisk();
        for(int i=0; i<partidasDisk.size(); ++i){
            String id = partidasDisk.get(i);
            if(partidasMap.get(id) == null) partidasMap.put(id, loadPartidaDisk(id));
        }
        return partidasMap;
    }

    /**
     * Retorna la partida que s'identifica per la ID indicada.
     * @param ID
     * @return Partida identificada pel ID
     */

    public static Partida getPartida(String ID) {
        if(partidasMap.get(ID) == null){
            try {
                return loadPartidaDisk(ID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return partidasMap.get(ID);
    }

    /**
     * Guarda una partida al disc.
     * @param p
     */

    public static void savePartida(Partida p) {
        try {
            PartidaDAO.savePartida(p.getID(), p.getMapaPartida().getID(), p.getTipoMapa(), p.getAngulosMapa(), p.getMatrixMapa(), p.getNumerosInicio(),
                    p.getNumerosInsertados(), String.valueOf(p.getCantidadInterrogantes()), String.valueOf(p.getReloj()), p.getUsuari());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ControladorUsuari.addPartidaToUser(p.getUsuari(), p.getID());
    }

    /**
     * Borra una partida del disc.
     * @param p
     */

    public static void deletePartida (Partida p) {
        partidasMap.remove(p.getID());
        PartidaDAO.deletePartida(p.getID());
        ControladorUsuari.removePartidaToUser(p.getUsuari(), p.getID());
    }

    /**
     * Carrega la partida identificada per la ID del disc.
     * @param ID
     * @return La partida identificada per ID
     */
    private static Partida loadPartidaDisk(String ID) throws IOException {
        StringBuilder userID                = new StringBuilder();
        StringBuilder topologia             = new StringBuilder();
        StringBuilder adyacencia            = new StringBuilder();
        StringBuilder reloj                 = new StringBuilder();
        StringBuilder cantidadInterrogantes = new StringBuilder();

        ArrayList<ArrayList<String>> matrix = new ArrayList<ArrayList<String>>();
        Vector<Integer> numerosInicio       = new Vector<>();
        Vector<Integer> numerosInsertados   = new Vector<>();

        PartidaDAO.loadPartida(ID,userID, topologia, adyacencia, matrix, reloj, numerosInicio, numerosInsertados, cantidadInterrogantes);

        String[][] matrixResult = new String[matrix.size()][matrix.get(0).size()];
        for(int i=0; i<matrixResult.length; ++i){
            matrixResult[i] = matrix.get(i).toArray(matrixResult[i]);
        }

        //cargo el mapa que estaba usando partida
        MapaFactory mF = new MapaFactory();
        Mapa m = mF.getMapa(topologia.toString(), adyacencia.toString(), matrixResult);

        Partida p = new Partida(ID, userID.toString(), numerosInicio, numerosInsertados, Integer.valueOf(cantidadInterrogantes.toString()), m, Integer.valueOf(reloj.toString()));
        //ya tiene el mapa bien puesto, lo único que he de modificar son numerosInicio, numerosRestantes, reloj,
        // ID (para que tenga el mismo y poder sobreescribir)

        //return p; //retorna la partida tal y como la dejamos

        partidasMap.put(ID, p);
        return p;
    }

    /**
     * Carrega a partidasDisk totes les partides que estàn presents al disc
     */

    private static  void loadAllPartidasDisk(){
        partidasDisk = PartidaDAO.loadAllPartidas();
    }

    // funcion movida a PartidaS
    /*public static void tableroLleno()
    {
        Partida p = partidasMap.get(partidaEnCurso);
        UtilsMapaDecorator utilsMapa = new UtilsMapaDecorator(p.getMapaPartida());
        if (utilsMapa.hidatoValido())
        {
            String difiControladorUsuariltad = "FACIL"; //para el testeo, de mientras lo dejo así
            System.out.println("si es valid");
            int puntuacion = calculoPuntuacion(difiControladorUsuariltad, p.getReloj(), p.getPistasConsultadas());
            //commit de la puntuacion en resultado
            String userID = ControladorUsuari.getUsuariActiu();
            ControladorUsuari.insertarResultat(ControladorUsuari.getUsuari(userID), puntuacion);
            p.setPuntuacion(puntuacion);
        }
    }*/

    /**
     * Inserta un nombre a la casella indicada de la partida que està activada
     * @param i
     * @param j
     * @param numero
     * @return Un boolean indicant si s'ha pogut insertar el nombre
     */

    //cada vez que haya que insertar un numero he de consultar interrogantes para saber si está la matriz llena
    public static boolean insertarNumero (int i, int j, int numero)
    {
        boolean b;
        Partida p = partidasMap.get(partidaEnCurso);
        b = p.insertarNumero(i, j, numero);
        p.actualizarContador();
        p.getMapaPartida().actualizaAdyacencias();
        //if (p.getCantidadInterrogantes() == 0) tableroLleno();
        return b;
    }

    /**
     * Borra el nombre indicat a la casella indicada pels paràmetres
     * @param i
     * @param j
     * @return Un boolean que indica si el nombre de la casella s'ha pogut esborrar
     */

    public static boolean borrarNumero (int i, int j)
    {
        boolean t = false;
        Partida p = partidasMap.get(partidaEnCurso);
        t = p.borrarNumero(i, j);
        p.actualizarContador();
        return t;
    }

    /**
     * Esborra un numero a la casella indicada i fica un altre
     * @param i
     * @param j
     * @param numero
     * @return Un boolean que indica si s'ha pogut efectuar la substitució
     */

    public static boolean reemplazarNumero (int i, int j, int numero)
    {
        Partida p = partidasMap.get(partidaEnCurso);
        return p.reemplazarNumero(i, j, numero);
    }

    /**
     * Consulta una pista disponible pel mapa
     * @return Retorna un Array de Integers amb el nombre a col·locar i la fila i la columna de la casella on ha d'anar
     */

    public static Integer[] consultarPista ()
    {
        Partida p = partidasMap.get(partidaEnCurso);
        return p.consultarPista();
    }

    /**
     * Consulta el temps que ha estat emprat fins ara en la partida
     * @return Un Integer amb la quantitat de segons que han passat des de l'inici de la partida (sense comptar pauses)
     */

    public static int consultarTiempo ()
    {
        Partida p = partidasMap.get(partidaEnCurso);
        return p.getReloj();
    }

    /**
     * Càlcul de la puntuació corresponent a la partida que s'ha finalitzat
     * @param difiControladorUsuariltad
     * @param tiempo
     * @param numeroPistas
     * @return Un Integer que indica la puntuació de la partida.
     */

    public static int calculoPuntuacion (String difiControladorUsuariltad, int tiempo, int numeroPistas)
    {
        double factorTiempo = 0;
        double factorDifiControladorUsuariltad = 0;
        switch (difiControladorUsuariltad)
        {
            case ("FACIL"): { factorTiempo = 100; factorDifiControladorUsuariltad = 0.25; break; }
            case ("MEDIO"): { factorTiempo = 75; factorDifiControladorUsuariltad = 0.75; break; }
            case ("DIFICIL"): { factorTiempo = 25; factorDifiControladorUsuariltad = 1; break; }
        }
        factorTiempo = factorTiempo * tiempo;
        double factorPistas = Math.pow(2, numeroPistas);

        return intValue((100000 * factorDifiControladorUsuariltad) - factorPistas - factorTiempo * tiempo);
    }
}
