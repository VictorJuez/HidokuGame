package Domini;

import Domini.Mapa.Mapa;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Usuari {
    private String ID;
    private String password;  //password del usuario
    private HashMap<String, Partida> partidas = new HashMap<>();   //partidas del usuario
    private HashMap<String, Mapa> mapas = new HashMap<>();      //mapas que ha creado el usuario
    //private ControladorPartida controladorPartida = new ControladorPartida();
    private ControladorMapa controladorMapa = new ControladorMapa();

    public Usuari(String ID, String password){
        this.ID = ID;
        this.password = md5(password);

    }

    public Usuari (String ID, String password, ArrayList<String> partidas, ArrayList<String> mapas) throws IOException {
        this.ID = ID;
        this.password = password;
        /*for(String partidaID : partidas){
        }*/
        for(String mapaID : mapas){
            Mapa mapa = controladorMapa.getMapa(mapaID);
            this.mapas.put(mapa.getID(), mapa);
        }
    }

    private String md5(String password)
    {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public String getID(){
        return this.ID;
    }

    public void setPassword(String password) {
        this.password = md5(password);
    }

    public String getPassword() {
        return password;
    }

    public boolean changePassword(String actualPassword, String newPassword){
        if(checkPassword(actualPassword)){
            this.password = md5(newPassword);
            return true;
        }
        return false;
    }

    public void addPartida(Partida partida){
        partidas.put(partida.getID(), partida);
    }

    public void popPartida(Partida partida){
        partidas.remove(partida.getID());
    }

    public void addMapa(Mapa mapa){
        mapas.put(mapa.getID(), mapa);
    }

    public void popMapa (Mapa mapa){
        mapas.remove(mapa.getID());
    }

    public HashMap<String, Mapa> getMapas() {
        return mapas;
    }

    public ArrayList<String> getMapasID(){
        ArrayList<String> mapasID = new ArrayList<>();
        for ( String key : mapas.keySet() ) mapasID.add(key);
        return mapasID;
    }

    public HashMap<String, Partida> getPartidas() {
        return partidas;
    }

    public ArrayList<String> getPartidasID(){
        ArrayList<String> partidasID = new ArrayList<>();
        for ( String key : partidas.keySet() ) partidasID.add(key);
        return partidasID;
    }

    @Override
    public boolean equals(Object obj){
        if (this==obj) return true;
        if (this == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Usuari u = (Usuari) obj;
        return this.ID.equals(u.getID());
    }


    public boolean checkPassword(String password) {
        return this.password.equals(md5(password));
    }
}
