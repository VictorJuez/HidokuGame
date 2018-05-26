package Domini;

import Dades.PartidaDAO;
import Domini.Mapa.Mapa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorPartida
{
    private static HashMap<String, Partida> partidasMap = new HashMap<>();
    private ArrayList<String> partidasDisk = new ArrayList<>();
    private PartidaDAO pDAO = new PartidaDAO();

    public ControladorPartida() {}

    public Partida crearPartida(Mapa m)
    {
        //crea una partida y la añade al hashmap de partidas existentes.
        Partida p;
        p = new Partida(m);
        this.partidasMap.put(p.getID(), p);
        return p;
    }

    public Partida crearPartidaRandom()
    {
        ControladorMapa cM = new ControladorMapa();
        Mapa mapaRandom = cM.generarHidato();
        Partida p = new Partida(mapaRandom);
        this.partidasMap.put(p.getID(), p);
        return p;
    }

    public void guardarPartida(Partida p) throws IOException
    {
        pDAO.savePartida(p);
    }

    public Partida cargarPartida(String ID) throws IOException
    {
        Partida p = pDAO.loadPartida(ID); //la partida nos la dejan 100% preparada.
        return p;
    }

}
