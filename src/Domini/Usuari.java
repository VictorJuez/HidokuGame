package Domini;

import Domini.Mapa.Mapa;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Usuari {
    private String ID;
    private String password;  //password del usuario
    private int puntuacio;
    private int record;
    private HashMap<String, Partida> partidas = new HashMap<>();   //partidas del usuario

    public Usuari(String ID, String password){
        this.ID = ID;
        this.password = md5(password);
        this.puntuacio = 0;
        this.record = 0;

    }

    public Usuari (String ID, String password, int puntuacio, int record, ArrayList<String> partidas, ArrayList<String> mapas) throws IOException {
        this.ID = ID;
        this.password = password;
        this.puntuacio = puntuacio;
        this.record = record;
        for(String partidaID : partidas){
            this.partidas.put(partidaID, ControladorPartida.getPartida(partidaID));
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

    public HashMap<String, Partida> getPartidas() {
        return partidas;
    }

    public ArrayList<String> getPartidasID(){
        ArrayList<String> partidasID = new ArrayList<>();
        for ( String key : partidas.keySet() ) partidasID.add(key);
        return partidasID;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
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
