package Domini;

import Domini.Mapa.Mapa;

public class Resultat {
    //Singleton pattern
    private final Mapa mapa;
    private final Usuari usuari;
    private int resultat;

    public Resultat(Usuari usuari, Mapa mapa, int resultat){
        this.mapa = mapa;
        this.usuari = usuari;
        this.resultat = resultat;
    }

    public void afegirPuntuacio(int resultat){
        this.resultat+=resultat;
    }

    public void restarPuntuacio(int resultat){
        this.resultat-=resultat;
        if(this.resultat < 0) this.resultat = 0;
    }

    public int getResultat() {
        return resultat;
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
        return this.mapa.getID().equals(r.getMapa().getID()) && this.usuari.getID().equals(r.getUsuari().getID());
    }
}
