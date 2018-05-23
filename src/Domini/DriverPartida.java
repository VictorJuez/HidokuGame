package Domini;

public class DriverPartida
{
    public static void main (String[] args)
    {
        //creo un mapa random para pasarle a la partida:
        ControladorMapa cM = new ControladorMapa();
        Mapa mapaAJugar = cM.generarHidato();

        Partida p = new Partida(mapaAJugar);

        p.jugar();
    }
}
