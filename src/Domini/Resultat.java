package Domini;

import Domini.Mapa.Mapa;

public class Resultat {
    //Singleton pattern
    private final Mapa mapa;
    private final Usuari usuari;
    private int puntuacio;

    public Resultat(Usuari usuari, Mapa mapa, int puntuacio){
        this.mapa = mapa;
        this.usuari = usuari;
        this.puntuacio = puntuacio;
    }

    public Resultat(Usuari usuari, int puntuacio){
        this.usuari = usuari;
        this.puntuacio = puntuacio;
        this.mapa = null;
    }

    public void afegirPuntuacio(int resultat){
        this.puntuacio +=resultat;
    }

    public void restarPuntuacio(int resultat){
        this.puntuacio -=resultat;
        if(this.puntuacio < 0) this.puntuacio = 0;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public Mapa getMapa() {
        return mapa;
    }

    @Override
    public boolean equals(Object obj){
        if (this==obj) return true;
        if (this == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Resultat r = (Resultat) obj;
        if(r.getMapa()!= null && this.mapa != null) return this.mapa.getID().equals(r.getMapa().getID()) && this.usuari.getID().equals(r.getUsuari().getID());
        if(r.getMapa() == null && this.mapa == null) return this.usuari.getID().equals(r.getUsuari().getID());
        else return false;
    }
}
