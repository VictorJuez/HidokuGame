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
    private ArrayList<String> partidasDisk = new ArrayList<>();
    public PartidaDAO pDAO = new PartidaDAO();
    private ControladorResultat cR = new ControladorResultat();
    private ControladorUsuari cU = new ControladorUsuari();

    public ControladorPartida() {}

    public void seleccionarPartida (String ID)
    {
        this.partidaEnCurso = ID;
        Partida p = partidasMap.get(ID);
        p.activarContador();
    }

    public Partida crearPartida(Mapa m, String usuari)
    {
        //crea una partida y la añade al hashmap de partidas existentes.
        Partida p;
        p = new Partida(m, usuari);
        this.partidasMap.put(p.getID(), p);
        return p;
    }

    public Partida crearPartidaRandom(String usuari)
    {
        ControladorMapa cM = new ControladorMapa();
        Mapa mapaRandom = cM.generarHidato();
        Partida p = new Partida(mapaRandom, usuari);
        this.partidasMap.put(p.getID(), p);
        return p;
    }

    public HashMap<String, Partida> getAllPartidas () throws IOException {
        loadAllPartidasDisk();
        for(int i=0; i<partidasDisk.size(); ++i){
            String id = partidasDisk.get(i);
            if(partidasMap.get(id) == null) partidasMap.put(id, loadPartidaDisk(id));
        }
        return partidasMap;
    }

    public Partida getPartida(String ID) {
        if(partidasMap.get(ID) == null){
            try {
                return loadPartidaDisk(ID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return partidasMap.get(ID);
    }

    public void savePartida(Partida p) {
        try {
            pDAO.savePartida(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePartida (Partida p) {
        partidasMap.remove(p.getID());
        pDAO.deletePartida(p);
    }

    private Partida loadPartidaDisk(String ID) throws IOException {
        Partida p = pDAO.loadPartida(ID);
        partidasMap.put(ID, p);
        return p;
    }

    private void loadAllPartidasDisk(){
        partidasDisk = pDAO.loadAllPartidas();
    }

    public void tableroLleno()
    {
        Partida p = partidasMap.get(partidaEnCurso);
        UtilsMapaDecorator utilsMapa = new UtilsMapaDecorator(p.getMapaPartida());
        if (utilsMapa.hidatoValido())
        {
            //aqui entra si el Hidato está bien resuelto
            String dificultad = p.getMapaPartida().getDificultad();

            int puntuacion = calculoPuntuacion(dificultad, p.getReloj(), p.getNumeroPistas());
            //commit de la puntuacion en resultado
            String userID = cU.getUsuariActiu();
            cR.insertarResultat(cU.getUsuari(userID), p.getMapaPartida(), puntuacion);
        }
    }

    //cada vez que haya que insertar un numero he de consultar interrogantes para saber si está la matriz llena
    public void insertarNumero (int i, int j, int numero)
    {
        Partida p = partidasMap.get(partidaEnCurso);
        p.insertarNumero(i, j, numero);
        p.actualizarContador();
        if (p.getCantidadInterrogantes() == 0) tableroLleno();
    }

    public void borrarNumero (int i, int j)
    {
        Partida p = partidasMap.get(partidaEnCurso);
        p.borrarNumero(i, j);
        p.actualizarContador();
    }

    public void reemplazarNumero (int i, int j, int numero)
    {
        Partida p = partidasMap.get(partidaEnCurso);
        p.reemplazarNumero(i, j, numero);
    }


    public int consultarTiempo ()
    {
        Partida p = partidasMap.get(partidaEnCurso);
        return p.getReloj();
    }

    public int calculoPuntuacion (String dificultad, int tiempo, int numeroPistas)
    {
        double factorTiempo = 0;
        double factorDificultad = 0;
        switch (dificultad)
        {
            case ("FACIL"): { factorTiempo = 1; factorDificultad = 0.25; break; }
            case ("MEDIO"): { factorTiempo = 0.75; factorDificultad = 0.75; break; }
            case ("DIFICIL"): { factorTiempo = 0.25; factorDificultad = 1; break; }
        }
        factorTiempo = factorTiempo * tiempo;
        double factorPistas = Math.pow(2, numeroPistas);

        return intValue((100000 * factorDificultad) - factorPistas - factorTiempo);
    }
}
