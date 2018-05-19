package Domini;

import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
import java.util.Vector;

public class Partida
{
    private Vector<Integer> numerosInsertados; //contiene los números que había al principio y los que hemos ido poniendo
    private Vector<Integer> numerosInicio; //sólo contiene los números del inicio
    private Mapa mapaPartida;
    private Mapa mapaEnunciado;
    private String ID;
    private Date data;
    private double tiempoTranscurrido; //expresado en SEGUNDOS
    private long horaInicio;
    private long horaPausa;

    public Partida (Mapa mapaEnunciado, Vector<Integer> numerosExistentes)
    {
        //asignación del ID de la partida (para gestionar load/save, rankings..)
        this.ID = UUID.randomUUID().toString();
        //gestión del tiempo transcurrido en la partida
        this.data = new Date();
        this.horaInicio = data.getTime();
        this.tiempoTranscurrido = 0;
        //copia del mapa a usar
        this.mapaEnunciado = mapaEnunciado;
        MapaFactory mapaFactory = new MapaFactory();
        this.mapaPartida = mapaFactory.getMapa(mapaEnunciado.getTipo(), mapaEnunciado.getAngulos(), mapaEnunciado.getMatrix());
        this.numerosInsertados = numerosExistentes;
        this.numerosInicio = mapaPartida.getNumerosExistents();
    }

    public void pausarPartida() //sobretodo hago ésto por la gestión del cronómetro.
    {
        //cojo la hora actual y la resto para calcular el tiempo que ha transcurrido
        this.horaPausa = data.getTime();
        this.tiempoTranscurrido = Math.floor((this.horaPausa - this.horaInicio) / 1000); //y éste es el tiempo que ha transcurrido
    }

    public void reanudarPartida()
    {
        this.horaInicio = data.getTime();
    }

    //driver para probar la clase (falta revisar)
    public void jugar ()
    {
        boolean exit = false; //para saber si salimos de la partida
        String op; //1 -> añadir, 2 -> borrar, 3 -> salir.
        System.out.println("Presiona a para añadir un número, s para borrar, e para salir");
        while (!exit)
        {
            Scanner myScanner = new Scanner(System.in);
            op = myScanner.next();

            switch (op)
            {
                case ("1"):
                {
                    System.out.println("fila, columna, numero");
                    int i = myScanner.nextInt();
                    int j = myScanner.nextInt();
                    Integer numero = myScanner.nextInt();
                    this.mapaPartida.insertarNumero(i, j, numero);
                    break;
                }
                case ("2"):
                {
                    System.out.println("fila, columna");
                    int i = myScanner.nextInt();
                    int j = myScanner.nextInt();
                    //this.mapaPartida.borrarNumero(i, j);
                    break;
                }
                case ("3"):
                {
                    exit = true;
                    break;
                }
            }
        }
    }

    //indica si en la casilla que apuntamos con i y j es para números o es una casilla no válida
    private boolean casillaNumero (int i, int j)
    {
        String casilla = this.mapaPartida.matrix[i][j];
        if (casilla != "#" && casilla != "*") return true;
        return false;
    }

    //inserta un número en el tablero si no ha sido insertado antes y si la casilla es valida.
    private Mapa insertarNumero (int i, int j, Integer numero)
    {
        if (casillaNumero(i, j))
        {
            if (!this.numerosInsertados.contains(numero))
            {
                this.numerosInsertados.add(numero);
                this.mapaPartida.insertarNumero(i, j, numero);
            }
            else System.out.println("El número que está intentando poner ya existe en el tablero.");
        }
        else System.out.println("La casilla no es válida para introducir un número.");
        return this.mapaPartida;
    }

    private Mapa borrarNumero (int i, int j)
    {
        String casilla = this.mapaPartida.matrix[i][j];
        if (casillaNumero(i, j) && casilla != "?") //si casilla apta para número y no hay ninguno puesto ya
        {
            if (!this.numerosInicio.contains(casilla)) //si no era de los iniciales
            {
                //si no estaba al inicio sabemos sí o sí que lo hemos insertado después
                //entonces sabemos sí o sí que está en números insertados.
                int pos = this.numerosInsertados.indexOf(casilla);
                this.numerosInsertados.remove(pos); //lo quitamos de los números insertados.
                //this.mapaPartida.borrarNumero(i, j); //borramos el número si no era de los iniciales
            }
            else System.out.println("No puedes borrar éste número (ya estaba al inicio de la partida).");
        }
        else System.out.println("En la casilla no hay un número.");
        return this.mapaPartida;
    }
}

