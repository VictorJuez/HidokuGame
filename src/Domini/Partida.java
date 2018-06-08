////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
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
    private int cantidadInterogantes; //los números que quedan por poner
    protected Mapa mapaEnunciado;
    protected Mapa mapaPartida;
    private Date data;
    private boolean paused;
    private double tiempoTranscurrido; //expresado en SEGUNDOS, tiempo entre pausas.
    private int tiempoTotal; //tiempo total acumulado de la partida.
    private long horaInicio;
    private long horaPausa;

    //GETTERS DE LA CLASE

    /**
     * Retorna la ID de la partida
     * @return String amb la ID de la partida
     */
    public String getID() { return this.ID; }

    /**
     * Retorna la ID de l'usuari
     * @return String amb la ID de l'usuari
     */
    public String getUsuari() {
        return usuari;
    }

    /**
     * Retorna el temps de joc comprés des de l'inici de la partida
     * @return Un Integer amb el temps de joc.
     */
    public int getReloj() { return this.tiempoTotal; }

    /**
     * Retorna un String amb la tipologia del mapa
     * @return String amb la tipologia del mapa
     */
    public String getTipoMapa() { return this.mapaPartida.getTipo(); }

    /**
     * Retorna un String amb el tipus d'adjacència del mapa
     * @return Un String amb el tipus d'adjacència del mapa
     */
    public String getAngulosMapa() { return this.mapaPartida.getAngulos(); }

    /**
     * Retorna un Integer amb el nombre de files del mapa
     * @return un Integer amb el nombre de files del mapa
     */
    public int getFilasMapa() { return this.mapaPartida.getFilas(); }

    /**
     * Retorna un Integer amb el nombre de columnes del mapa
     * @return un Integer amb el nombre de columnes del mapa
     */
    public int getColumnasMapa() { return this.mapaPartida.getColumnas(); }

    /**
     * Retorna el mapa de la partida
     * @return el mapa de la partida
     */
    public Mapa getMapaPartida() {
        return mapaPartida;
    }

    /**
     * Retorna un Array d'Strings amb el tauler del mapa
     * @return un Array d'Strings amb el tauler del mapa
     */
    public String[][] getMatrixMapa() { return this.mapaPartida.getMatrix(); }

    /**
     * Retorna la quantitat d'interrogants que hi han actualment al mapa de la partida
     * @return un Integer amb la quantitat d'interrogants que hi han actualment al mapa de la partida
     */
    public int getCantidadInterrogantes() { return this.cantidadInterogantes; }

    /**
     * Retorna la quantitat de pistes que s'han consultat a la partida
     * @return un Integer amb la quantitat de pistes consultadas
     */
    public int getPistasConsultadas() {
        return this.pistasConsultadas;
    }
    //los devuelve como string para pasarselo a PartidaDAO

    /**
     * Retorna un Vector de Integers amb els nombres que estaven al mapa al començament de la partida
     * @return un Vector de Integers amb els nombres que estaven al mapa al començament de la partida
     */
    public Vector<Integer> getNumInicio(){
        return numerosInicio;
    }

    /**
     * Retorna un String amb els nombres que estaven al mapa al començament de la partida
     * @return un String amb els nombres que estaven al mapa al començament de la partida
     */
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

    /**
     * Retorna un String amb els nombres que l'usuari ha inserit durant la partida (mes els de l'inici)
     * @return Un String amb els nombres que l'usuari ha inserit durant la partida (mes els de l'inici)
     */
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

    /**
     * Fixa una ID per la partida
     * @param ID
     */
    public void setID(String ID) { this.ID = ID; }

    /**
     * Fixa una puntuació per a la partida
     * @param puntuacion
     */
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }

    /**
     * Fixa un cert temps emprat per la partida
     * @param reloj
     */
    public void setReloj(double reloj) { this.tiempoTranscurrido = reloj; }

    /**
     * Fixa una quantitat d'interrogants a la partida
     * @param cantidadInterogantes
     */
    public void setCantidadInterrogantes(int cantidadInterogantes) { this.cantidadInterogantes = cantidadInterogantes; }

    /**
     * Fixa un vector de nombres inserits per a la partida
     * @param numerosInsertados
     */
    public void setNumerosInsertados (Vector<Integer> numerosInsertados)
    {
        this.numerosInsertados = numerosInsertados;
    }

    /**
     * Fixa un vector de nombres inicials per a la partida
     * @param numerosInicio
     */
    public void setNumerosInicio (Vector<Integer> numerosInicio) { this.numerosInicio = numerosInicio; }

    /**
     * Crea una partida amb el mapa passat per paràmetre i fixa l'usuari passat per paràmetre com propietari
     * @param mapaEnunciado
     * @param usuari
     */
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

        this.mapaEnunciado = mapaEnunciado;

        //copia del mapa a usar
        MapaFactory mapaFactory = new MapaFactory();
        this.mapaPartida = mapaFactory.getMapa(mapaEnunciado.getTipo(), mapaEnunciado.getAngulos(), mapaEnunciado.getMatrix());
        this.numerosInsertados = mapaPartida.getNumerosExistents();
        this.numerosInicio = mapaPartida.getNumerosExistents();
    }

    /**
     * Crea una partida amb una certa ID, un usuari propietari, i demés paràmetres concrets.
     * @param ID
     * @param usuari
     * @param numerosInicio
     * @param numerosInsertados
     * @param cantidadInterogantes
     * @param mapaPartida
     * @param tiempoTotal
     */
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

    /**
     * Activa el recompte del temps per a la partida activa
     */
    public void activarContador()
    {
        this.data = new Date();
        this.horaInicio = data.getTime();
    }

    /**
     * Refresh del contador de temps
     */
    public void actualizarContador()
    {
        pausarPartida();
        reanudarPartida();
    }

    /**
     * Pausa la partida (es deixa de comptabilitzar el temps)
     */
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

    /**
     * Despausa la partida (es torna a comptar el temps)
     */
    public void reanudarPartida()
    {
        data = new Date();
        this.horaInicio = data.getTime();
        this.paused = false;
    }

    /**
     * Verifica si a la casella indicada hi ha un nombre o hi ha un # o un *
     * @param i
     * @param j
     * @return
     */
    //indica si en la casilla que apuntamos con i y j es para números o es una casilla no válida
    private boolean casillaNumero (int i, int j)
    {
        String casilla = this.mapaPartida.getMatrix()[i][j];
        if (!casilla.equals("#") && !casilla.equals("*")) return true;
        return false;
    }

    /**
     * Indica si la casella no es surt de la matriu del mapa
     * @param i
     * @param j
     * @return Un boolean que indica si la casella està dins del rang de la matriu
     */
    //para que no de un out of bounds
    private boolean casillaValida(int i, int j)
    {
        return (i < this.mapaPartida.getFilas() && j < this.mapaPartida.getColumnas());
    }

    /**
     * Inserta un nombre a la casella indicada
     * @param i
     * @param j
     * @param numero
     * @return un boolean que indica si s'ha pogut insertar el nombre a la casella.
     */
    //inserta un número en el tablero si no ha sido insertado antes y si la casilla es valida.
    public boolean insertarNumero (int i, int j, int numero)
    {
        if (!paused && casillaValida(i, j))
        {
            //para que no se pueda meter un número más grande que las casillas totales del hidato
            //System.out.println(mapaPartida.getNumeros() +" , "+ mapaPartida.getInterrogants());
            /*if (numero > (mapaPartida.getNumeros() + mapaPartida.getInterrogants())) {
                System.out.print("El numero más grande es: ");
                System.out.println(mapaPartida.getNumeros());
            }
            else if (this.numerosInicio.contains(numero))
                System.out.println("No puedes modificar éste número (ya estaba al inicio de la partida).");
            else {*/
            if (casillaNumero(i, j) && this.mapaPartida.getMatrix()[i][j].equals("?") && !this.numerosInicio.contains(numero)
                    && numero < (mapaPartida.getNumeros() + mapaPartida.getInterrogants())) {
                if (!this.numerosInsertados.contains(numero)) {
                    this.numerosInsertados.add(numero);
                    this.mapaPartida.insertarNumero(numero, i, j);
                    this.cantidadInterogantes -= 1;
                    return true;
                    } //else System.out.println("El número que está intentando poner ya existe en el tablero.");
                } //else System.out.println("La casilla no es válida para introducir un número");
            }
        //else System.out.println("La casilla está fuera de la matriz");
        return false;
    }

    /**
     * Esborra un nombre a la casella indicada
     * @param i
     * @param j
     * @return Un boolean que indica si s'ha pogut esborrar
     */
    public boolean borrarNumero (int i, int j)
    {
        if (casillaValida(i, j) && !paused )
        {
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
                    return true;
                } //else System.out.println("No puedes borrar éste número (ya estaba al inicio de la partida).");
            } //else System.out.println("En la casilla no hay un número.");
        }
        //else System.out.println("La casilla está fuera de la matriz");
        return false;
    }

    /**
     * Borra el nombre a la casella indicada i inserta un altre
     * @param i
     * @param j
     * @param numero
     * @return Un boolean que indica si s'ha pogut realitzar la operació
     */
    public boolean reemplazarNumero (int i, int j, int numero)
    {
        boolean b = false;
        //borra el que había y inserta el siguiente.
        //las funciones ya comprueban que se pueda realizar el reemplazo del número así que sólo las llamo.
        borrarNumero(i, j);
        b = insertarNumero(i, j, numero);
        return b;
    }

    /**
     * Consulta una pista sobre el mapa que s'està jugant
     * @return Un Array de Integers on figuren el següent nombre a insertar i la fila i columna de la casella on ha d'anar
     */

    public Integer[] consultarPista () {
        ++pistasConsultadas;
        UtilsMapaDecorator umd = new UtilsMapaDecorator(this.mapaPartida);
        Integer[] resultatPista = umd.pista();
        return resultatPista;
    }
}
