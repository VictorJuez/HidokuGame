package Domini;

import Domini.Mapa.Mapa;
import Domini.Mapa.MapaFactory;
import Domini.Mapa.UtilsMapaDecorator;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
import java.util.Vector;

public class Partida
{
    private String ID;
    private String usuari;
    private int puntuacion;
    private int pistasConsultadas;
    private Vector<Integer> numerosInsertados; //contiene los números que había al principio y los que hemos ido poniendo
    private Vector<Integer> numerosInicio; //sólo contiene los números del inicio
    private int cantidadInterogantes; //los números que quedan por
    protected Mapa mapaPartida;
    private Date data;
    private boolean paused;
    private double tiempoTranscurrido; //expresado en SEGUNDOS, tiempo entre pausas.
    private int tiempoTotal; //tiempo total acumulado de la partida.
    private long horaInicio;
    private long horaPausa;

    //GETTERS DE LA CLASE
    public String getID() { return this.ID; }
    public String getUsuari() {
        return usuari;
    }
    public int getReloj() { return this.tiempoTotal; }
    public String getTipoMapa() { return this.mapaPartida.getTipo(); }
    public String getAngulosMapa() { return this.mapaPartida.getAngulos(); }
    public int getFilasMapa() { return this.mapaPartida.getFilas(); }
    public int getColumnasMapa() { return this.mapaPartida.getColumnas(); }
    public Mapa getMapaPartida() {
        return mapaPartida;
    }
    public String[][] getMatrixMapa() { return this.mapaPartida.getMatrix(); }
    public int getCantidadInterrogantes() { return this.cantidadInterogantes; }
    public int getPistasConsultadas() {
        return this.pistasConsultadas;
    }
    //los devuelve como string para pasarselo a PartidaDAO
    public String getNumerosInicio() {
        int size = this.numerosInicio.size();
        String numerosInicio = "";

        numerosInicio += String.valueOf(this.numerosInicio.get(0));
        for (int i = 1; i < size; ++i)
        {
            numerosInicio += ",";
            numerosInicio += String.valueOf(this.numerosInicio.get(i));
        }
        return numerosInicio;
    }
    public String getNumerosInsertados() {
        int size = this.numerosInsertados.size();
        String numerosInsertados = "";

        numerosInsertados += String.valueOf(this.numerosInsertados.get(0));
        for (int i = 1; i < size; ++i)
        {
            numerosInsertados += ",";
            numerosInsertados += String.valueOf(this.numerosInsertados.get(i));
        }
        return numerosInsertados;
    }

    //SETTERS DE LA CLASE
    public void setID(String ID) { this.ID = ID; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }
    public void setReloj(double reloj) { this.tiempoTranscurrido = reloj; }
    public void setCantidadInterrogantes(int cantidadInterogantes) { this.cantidadInterogantes = cantidadInterogantes; }
    public void setNumerosInsertados (Vector<Integer> numerosInsertados)
    {
        this.numerosInsertados = numerosInsertados;
    }
    public void setNumerosInicio (Vector<Integer> numerosInicio) { this.numerosInicio = numerosInicio; }

    //hacer otro constructor para cuando cargue la partida.
    public Partida (Mapa mapaEnunciado, String usuari)
    {
        //asignación del ID de la partida (para gestionar load/save, rankings..)
        this.ID = UUID.randomUUID().toString();
        this.usuari = usuari;
        this.paused = false;
        this.cantidadInterogantes = mapaEnunciado.getInterrogants();
        this.pistasConsultadas = 0;
        this.puntuacion = 0;

        //gestión del tiempo transcurrido en la partida
        this.tiempoTranscurrido = 0;
        this.tiempoTotal = 0;

        //copia del mapa a usar
        MapaFactory mapaFactory = new MapaFactory();
        this.mapaPartida = mapaFactory.getMapa(mapaEnunciado.getTipo(), mapaEnunciado.getAngulos(), mapaEnunciado.getMatrix());
        this.numerosInsertados = mapaPartida.getNumerosExistents();
        this.numerosInicio = mapaPartida.getNumerosExistents();
    }

    public Partida (String ID, String usuari, Vector<Integer> numerosInicio, Vector<Integer> numerosInsertados, int cantidadInterogantes,
                   Mapa mapaPartida, int tiempoTotal)
    {
        this.ID = ID;
        this.usuari = usuari;
        this.numerosInicio = numerosInicio;
        this.numerosInsertados = numerosInsertados;
        this.cantidadInterogantes = cantidadInterogantes;
        this.mapaPartida = mapaPartida;
        this.paused = false;
        this.tiempoTotal = tiempoTotal;
    }

    public void activarContador()
    {
        this.data = new Date();
        this.horaInicio = data.getTime();
    }

    public void actualizarContador()
    {
        pausarPartida();
        reanudarPartida();
    }

    public void pausarPartida() //sobretodo hago ésto por la gestión del cronómetro.
    {
        if (!paused)
        {
            //cojo la hora actual y la resto para calcular el tiempo que ha transcurrido
            this.paused = true;
            data = new Date();
            this.horaPausa = data.getTime();
            this.tiempoTranscurrido = Math.floor((this.horaPausa - this.horaInicio) / 1000); //y éste es el tiempo que ha transcurrido
            this.tiempoTotal += this.tiempoTranscurrido;
        }
    }

    public void reanudarPartida()
    {
        data = new Date();
        this.horaInicio = data.getTime();
        this.paused = false;
    }

    //indica si en la casilla que apuntamos con i y j es para números o es una casilla no válida
    private boolean casillaNumero (int i, int j)
    {
        String casilla = this.mapaPartida.getMatrix()[i][j];
        if (!casilla.equals("#") && !casilla.equals("*")) return true;
        return false;
    }

    //para que no de un out of bounds
    private boolean casillaValida(int i, int j)
    {
        return (i < this.mapaPartida.getFilas() && j < this.mapaPartida.getColumnas());
    }

    //inserta un número en el tablero si no ha sido insertado antes y si la casilla es valida.
    public void insertarNumero (int i, int j, int numero)
    {
        if (paused) System.out.println ("La partida está en pausa");
        else if (casillaValida(i, j)) {
            //para que no se pueda meter un número más grande que las casillas totales del hidato
            if (numero > mapaPartida.getNumeros() + mapaPartida.getInterrogants()) {
                System.out.print("El numero más grande es: ");
                System.out.println(mapaPartida.getNumeros());
            }
            else if (this.numerosInicio.contains(numero))
                System.out.println("No puedes modificar éste número (ya estaba al inicio de la partida).");
            else {
                if (casillaNumero(i, j) && this.mapaPartida.getMatrix()[i][j].equals("?")) {
                    if (!this.numerosInsertados.contains(numero)) {
                        this.numerosInsertados.add(numero);
                        this.mapaPartida.insertarNumero(numero, i, j);
                        this.cantidadInterogantes -= 1;
                        this.mapaPartida.actualizaAdyacencias();
                    } else System.out.println("El número que está intentando poner ya existe en el tablero.");
                } else System.out.println("La casilla no es válida para introducir un número");
            }
        }
        else System.out.println("La casilla está fuera de la matriz");
    }

    public void borrarNumero (int i, int j)
    {
        if (paused) System.out.println ("La partida está en pausa");
        else if (casillaValida(i, j)) {
            String casilla = this.mapaPartida.getMatrix()[i][j];
            if (casillaNumero(i, j) && casilla != "?") //si casilla apta para número y no hay ninguno puesto ya
            {
                if (!this.numerosInicio.contains(Integer.parseInt(casilla))) //si no era de los iniciales
                {
                    //si no estaba al inicio sabemos sí o sí que lo hemos insertado después
                    //entonces sabemos sí o sí que está en números insertados.
                    int pos = this.numerosInsertados.indexOf(Integer.parseInt(casilla));
                    this.numerosInsertados.remove(pos); //lo quitamos de los números insertados.
                    this.mapaPartida.borrarNumero(i, j); //borramos el número si no era de los iniciales
                    this.cantidadInterogantes += 1;
                } else System.out.println("No puedes borrar éste número (ya estaba al inicio de la partida).");
            } else System.out.println("En la casilla no hay un número.");
        }
        else System.out.println("La casilla está fuera de la matriz");
    }

    public void reemplazarNumero (int i, int j, int numero)
    {
        //borra el que había y inserta el siguiente.
        //las funciones ya comprueban que se pueda realizar el reemplazo del número así que sólo las llamo.
        borrarNumero(i, j);
        insertarNumero(i, j, numero);
    }

    public void consultarPista ()
    {
        ++pistasConsultadas;
        UtilsMapaDecorator umd = new UtilsMapaDecorator(this.mapaPartida);
        int resultatPista = umd.pista();
        if (resultatPista == -1) System.out.println("Hay algún número mal puesto");
    }
}
