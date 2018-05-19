package Domini;

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
}
