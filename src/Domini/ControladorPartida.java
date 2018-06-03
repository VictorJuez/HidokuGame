package Domini;

import Dades.PartidaDAO;
import Domini.Mapa.Mapa;
import Domini.Mapa.UtilsMapaDecorator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class ControladorPartida
{
    private static String partidaEnCurso;
    private static HashMap<String, Partida> partidasMap = new HashMap<>();
    private static ArrayList<String> partidasDisk = new ArrayList<>();

    private ControladorPartida() {}

    public static void seleccionarPartida (String ID)
    {
        partidaEnCurso = ID;
        Partida p = partidasMap.get(ID);
        p.activarContador();
    }

    public static String getPartidaEnCurso() {
        return partidaEnCurso;
    }

    public static Partida crearPartida(Mapa m, String usuari)
    {
        //crea una partida y la añade al hashmap de partidas existentes.
        Partida p;
        p = new Partida(m, usuari);
        partidasMap.put(p.getID(), p);
        return p;
    }

    public static Partida crearPartidaRandom(String usuari)
    {
        Mapa mapaRandom = ControladorMapa.generarHidato();
        Partida p = new Partida(mapaRandom, usuari);
        partidasMap.put(p.getID(), p);
        ControladorUsuari.addPartidaToUser(usuari, p.getID());
        return p;
    }

    public static HashMap<String, Partida> getAllPartidas () throws IOException {
        loadAllPartidasDisk();
        for(int i=0; i<partidasDisk.size(); ++i){
            String id = partidasDisk.get(i);
            if(partidasMap.get(id) == null) partidasMap.put(id, loadPartidaDisk(id));
        }
        return partidasMap;
    }

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

    public static void savePartida(Partida p) {
        try {
            PartidaDAO.savePartida(p);
            ControladorUsuari.addPartidaToUser(p.getUsuari(), p.getID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deletePartida (Partida p) {
        partidasMap.remove(p.getID());
        PartidaDAO.deletePartida(p);
        ControladorUsuari.removePartidaToUser(p.getUsuari(), p.getID());
    }

    private static Partida loadPartidaDisk(String ID) throws IOException {
        Partida p = PartidaDAO.loadPartida(ID);
        partidasMap.put(ID, p);
        return p;
    }

    private static  void loadAllPartidasDisk(){
        partidasDisk = PartidaDAO.loadAllPartidas();
    }

    public static void tableroLleno()
    {
        Partida p = partidasMap.get(partidaEnCurso);
        UtilsMapaDecorator utilsMapa = new UtilsMapaDecorator(p.getMapaPartida());
        if (utilsMapa.hidatoValido())
        {
            //aqui entra si el Hidato está bien resuelto
            //String difiControladorUsuariltad = p.getMapaPartida().getDifiControladorUsuariltad();
            String difiControladorUsuariltad = "FACIL"; //para el testeo, de mientras lo dejo así
            System.out.println("si es valid");
            int puntuacion = calculoPuntuacion(difiControladorUsuariltad, p.getReloj(), p.getPistasConsultadas());
            //commit de la puntuacion en resultado
            String userID = ControladorUsuari.getUsuariActiu();
            ControladorUsuari.insertarResultat(ControladorUsuari.getUsuari(userID), puntuacion);
            p.setPuntuacion(puntuacion);
        }
    }

    //cada vez que haya que insertar un numero he de consultar interrogantes para saber si está la matriz llena
    public static boolean insertarNumero (int i, int j, int numero)
    {
        boolean b;
        Partida p = partidasMap.get(partidaEnCurso);
        b = p.insertarNumero(i, j, numero);
        p.actualizarContador();
        p.getMapaPartida().actualizaAdyacencias();
        if (p.getCantidadInterrogantes() == 0) tableroLleno();
        return b;
    }

    public static void borrarNumero (int i, int j)
    {
        Partida p = partidasMap.get(partidaEnCurso);
        p.borrarNumero(i, j);
        p.actualizarContador();
    }

    public static boolean reemplazarNumero (int i, int j, int numero)
    {
        Partida p = partidasMap.get(partidaEnCurso);
        return p.reemplazarNumero(i, j, numero);
    }

    public static void consultarPista ()
    {
        Partida p = partidasMap.get(partidaEnCurso);
        p.consultarPista();
    }

    public static int consultarTiempo ()
    {
        Partida p = partidasMap.get(partidaEnCurso);
        return p.getReloj();
    }

    public static int calculoPuntuacion (String difiControladorUsuariltad, int tiempo, int numeroPistas)
    {
        double factorTiempo = 0;
        double factorDifiControladorUsuariltad = 0;
        switch (difiControladorUsuariltad)
        {
            case ("FACIL"): { factorTiempo = 1; factorDifiControladorUsuariltad = 0.25; break; }
            case ("MEDIO"): { factorTiempo = 0.75; factorDifiControladorUsuariltad = 0.75; break; }
            case ("DIFICIL"): { factorTiempo = 0.25; factorDifiControladorUsuariltad = 1; break; }
        }
        factorTiempo = factorTiempo * tiempo;
        double factorPistas = Math.pow(2, numeroPistas);

        return intValue((100000 * factorDifiControladorUsuariltad) - factorPistas - factorTiempo);
    }
}
