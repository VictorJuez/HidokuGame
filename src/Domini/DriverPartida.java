package Domini;

import Domini.Mapa.Mapa;
import Domini.Mapa.MapaFactory;

import java.io.IOException;
import java.util.Scanner;

public class DriverPartida
{
    public static void main (String[] args) throws IOException {
        //creo un mapa random para pasarle a la partida:

        Scanner myScanner = new Scanner(System.in);
        System.out.println("1 para mapa random, 2 para mapa simple de prueba, 3 para mapa sin solucion (testeo), 4 para cargar partida");
        String op = myScanner.next();

        ControladorPartida cP = new ControladorPartida();
        Partida p;
        if (op.equals("1")) p = cP.crearPartidaRandom();
        else if (op.equals("2"))
        {
            String ID = "1";
            String tipo = "Q";
            String angulos = "C";
            String[][] matrix = new String[2][2];
            matrix[0][0] = "1";
            matrix[0][1] = "2";
            matrix[1][0] = "4";
            matrix[1][1] = "?";
            MapaFactory mF = new MapaFactory();
            Mapa m = mF.getMapa(ID, tipo, angulos, matrix);
            p = cP.crearPartida(m);
        }
        else if (op.equals("3"))
        {
            String ID = "1";
            String tipo = "Q";
            String angulos = "C";
            String[][] matrix = new String[2][2];
            matrix[0][0] = "1";
            matrix[0][1] = "2";
            matrix[1][0] = "3";
            matrix[1][1] = "?";
            MapaFactory mF = new MapaFactory();
            Mapa m = mF.getMapa(ID, tipo, angulos, matrix);
            p = cP.crearPartida(m);
        }
        else
        {
            System.out.println("Inserte la ID de la partida a cargar");
            String ID = myScanner.next();
            p = cP.cargarPartida(ID);
        }
        p.jugar();
    }
}
