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

    /**
     * Crea un usuari
     * @param ID
     * @param password
     */
    public Usuari(String ID, String password){
        this.ID = ID;
        this.password = md5(password);
        this.puntuacio = 0;
        this.record = 0;

    }

    /**
     * Crea un usuari
     * @param ID
     * @param password
     * @param puntuacio
     * @param record
     * @param partidas
     * @param mapas
     * @throws IOException
     */
    public Usuari (String ID, String password, int puntuacio, int record, ArrayList<String> partidas, ArrayList<String> mapas) throws IOException {
        this.ID = ID;
        this.password = password;
        this.puntuacio = puntuacio;
        this.record = record;
        for(String partidaID : partidas){
            this.partidas.put(partidaID, ControladorPartida.getPartida(partidaID));
        }
    }

    /**
     * Genera un codi md5 del password que es passa per parametre
     * @param password
     * @return el codi md5 generat
     */
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

    /**
     * Obtenir el ID de l'usuari
     * @return String amb l'ID
     */
    public String getID(){
        return this.ID;
    }

    /**
     * Setter del password de l'usuari
     * @param password
     */
    public void setPassword(String password) {
        this.password = md5(password);
    }

    /**
     * Obtenir el codi md5 del password de l'usuari
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Per canviar el password de l'usuari
     * @param actualPassword
     * @param newPassword
     * @return true si l'actualPassword correspon amb la del usuari, llavors newPassword passa a ser la nova, false si no, i no es canvia.
     */
    public boolean changePassword(String actualPassword, String newPassword){
        if(checkPassword(actualPassword)){
            this.password = md5(newPassword);
            return true;
        }
        return false;
    }

    /**
     * Assignar una partida a l'usuari
     * @param partida
     */
    public void addPartida(Partida partida){
        partidas.put(partida.getID(), partida);
    }

    /**
     * Desassignar una partida a l'usuari
     * @param partida
     */
    public void popPartida(Partida partida){
        partidas.remove(partida.getID());
    }

    /**
     * Obtenir totes les Partides de l'usuari
     * @return
     */
    public HashMap<String, Partida> getPartidas() {
        return partidas;
    }

    /**
     * Obtenir l'ID de totes les partides de l'usuari
     * @return
     */
    public ArrayList<String> getPartidasID(){
        ArrayList<String> partidasID = new ArrayList<>();
        for ( String key : partidas.keySet() ) partidasID.add(key);
        return partidasID;
    }

    /**
     * Obtenir el record de punutuacio de l'usuari
     * @return
     */
    public int getRecord() {
        return record;
    }

    /**
     * Establir el record de puntuacio a l'usuari
     * @param record
     */
    public void setRecord(int record) {
        this.record = record;
    }

    /**
     * Obtenir la puntuacio de l'usuari
     * @return
     */
    public int getPuntuacio() {
        return puntuacio;
    }

    /**
     * Establir la puntuacio a l'usuari
     * @param puntuacio
     */
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


    /**
     * Comprova si el password correspon amb el de l'usuari
     * @param password
     * @return true si correspon, false si no.
     */
    public boolean checkPassword(String password) {
        return this.password.equals(md5(password));
    }
}
