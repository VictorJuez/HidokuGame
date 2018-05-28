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
    public PartidaDAO pDAO = new PartidaDAO();

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

    public static void printPartida(String[][] matrix, double tiempoTranscurrido){
        System.out.print ("Tiempo transcurrido: ");
        System.out.println(tiempoTranscurrido);
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
