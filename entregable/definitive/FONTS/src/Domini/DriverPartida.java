////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Domini;

import Domini.Mapa.Mapa;
import Domini.Mapa.MapaFactory;

import java.io.IOException;
import java.util.Scanner;

public class DriverPartida
{
    public static void main (String[] args) throws IOException {
        Usuari u = ControladorUsuari.insertarUsuari("Mathias", "123");
        ControladorUsuari.login(u, "123");


        Scanner myScanner = new Scanner(System.in);
        System.out.println("1 para mapa random, 2 para mapa simple de prueba, 3 para mapa sin solucion (testeo), 4 para cargar partida, 0 para salir");
        String op = myScanner.next();

        Partida p;
        if (op.equals("1")) p = ControladorPartida.crearPartidaRandom(ControladorUsuari.getUsuariActiu());
        else if (op.equals("2"))
        {
            String tipo = "Q";
            String angulos = "C";
            String[][] matrix = new String[2][2];
            matrix[0][0] = "1";
            matrix[0][1] = "2";
            matrix[1][0] = "4";
            matrix[1][1] = "?";
            MapaFactory mF = new MapaFactory();
            Mapa m = mF.getMapa(tipo, angulos, matrix);
            p = ControladorPartida.crearPartida(m, ControladorUsuari.getUsuariActiu());
        }
        else if (op.equals("3"))
        {
            String tipo = "Q";
            String angulos = "C";
            String[][] matrix = new String[2][2];
            matrix[0][0] = "1";
            matrix[0][1] = "2";
            matrix[1][0] = "3";
            matrix[1][1] = "?";
            MapaFactory mF = new MapaFactory();
            Mapa m = mF.getMapa(tipo, angulos, matrix);
            p = ControladorPartida.crearPartida(m, ControladorUsuari.getUsuariActiu());
        }
        else
        {
            System.out.println("Inserte la ID de la partida a cargar");
            String ID = myScanner.next();
            p = ControladorPartida.getPartida(ID);
        }
        //aqui es donde empezamos a jugar:
        ControladorPartida.seleccionarPartida(p.getID());

        while (op != "0")
        {
            System.out.println("1 -> añadir, 2 -> borrar, 7 -> reemplazar,3 -> pausar, 4 -> reanudar, 5 -> salir, 6-> guardar, 7-> pista");
            printPartida(p.getMapaPartida().getMatrix(), p.getMapaPartida());
            op = myScanner.next();

            switch (op)
            {
                case ("1"):
                {
                    System.out.println("Introduce la fila:");
                    int i = myScanner.nextInt();
                    System.out.println("Introduce la columna:");
                    int j = myScanner.nextInt();
                    System.out.println("Introduce el número:");
                    Integer numero = myScanner.nextInt();
                    ControladorPartida.insertarNumero(i-1, j-1, numero);
                    printPartida(p.getMapaPartida().getMatrix(), p.getMapaPartida());
                    break;
                }
                case ("2"):
                {
                    System.out.println("Introduce la fila:");
                    int i = myScanner.nextInt();
                    System.out.println("Introduce la columna:");
                    int j = myScanner.nextInt();
                    ControladorPartida.borrarNumero(i-1, j-1);
                    printPartida(p.getMapaPartida().getMatrix(), p.getMapaPartida());
                    break;
                }
                case ("3"):
                {
                    System.out.println("Partida pausada");
                    p.pausarPartida();
                    break;
                }
                case ("4"):
                {
                    System.out.println("Partida reanudada");
                    p.reanudarPartida();
                    break;
                }
                case ("6"):
                {
                    ControladorPartida.savePartida(p);
                    break;
                }
                case ("7"):
                    Integer[] i = p.consultarPista();
                    System.out.println(i[0]+" "+i[1]);
                    break;
            }
        }
    }

    public static void printPartida(String[][] matrix, Mapa mapa){
        int filas = matrix.length;
        int columnas = matrix[0].length;
        System.out.print("Topologia: ");
        System.out.println(mapa.getTipo());
        System.out.print("Adyacencias: ");
        System.out.println(mapa.getAngulos());
        System.out.println(ControladorPartida.consultarTiempo());
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
