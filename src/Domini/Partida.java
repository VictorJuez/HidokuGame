package Domini;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
import java.util.Vector;

public class Partida
{
    private Vector<Integer> numerosInsertados; //contiene los números que había al principio y los que hemos ido poniendo
    private Vector<Integer> numerosInicio; //sólo contiene los números del inicio
    private int cantidadNumeros;
    protected Mapa mapaPartida;
    private Mapa mapaEnunciado;
    private String ID;
    private Date data;
    private boolean paused;
    private double tiempoTranscurrido; //expresado en SEGUNDOS, tiempo entre pausas.
    private int tiempoTotal; //tiempo total acumulado de la partida.
    private long horaInicio;
    private long horaPausa;
    private boolean salirPartida;

    //hacer otro constructor para cuando cargue la partida.
    public Partida (Mapa mapaEnunciado)
    {
        //asignación del ID de la partida (para gestionar load/save, rankings..)
        this.ID = UUID.randomUUID().toString();
        this.salirPartida = false;
        this.paused = false;

        //gestión del tiempo transcurrido en la partida
        this.data = new Date();
        this.horaInicio = data.getTime();
        this.tiempoTranscurrido = 0;
        this.tiempoTotal = 0;

        //copia del mapa a usar
        this.mapaEnunciado = mapaEnunciado;
        MapaFactory mapaFactory = new MapaFactory();
        this.mapaPartida = mapaFactory.getMapa(mapaEnunciado.getTipo(), mapaEnunciado.getAngulos(), mapaEnunciado.getMatrix());
        this.numerosInsertados = mapaPartida.getNumerosExistents();
        this.numerosInicio = mapaPartida.getNumerosExistents();
    }

    //GETTERS DE LA CLASE
    public String getID() { return this.ID; }
    public int getReloj() { return this.tiempoTotal; }
    public String getTipoMapa() { return this.mapaPartida.getTipo(); }
    public String getAngulosMapa() { return this.mapaPartida.getAngulos(); }
    public int getFilasMapa() { return this.mapaPartida.getFilas(); }
    public int getColumnasMapa() { return this.mapaPartida.getColumnas(); }
    public String[][] getMatrixMapa() { return this.mapaPartida.getMatrix(); }
    //lo devuelve como string para pasarselo a PartidaDAO
    public String getNumerosInicio() {
        int size = this.numerosInicio.size();
        String numerosInicio = "";

        numerosInicio += String.valueOf(this.numerosInicio.get(1));
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

        numerosInsertados += String.valueOf(this.numerosInsertados.get(1));
        for (int i = 0; i < size; ++i)
        {
            numerosInsertados += ",";
            numerosInsertados += String.valueOf(this.numerosInsertados.get(i));
        }
        return numerosInsertados;
    }

    public void jugar () throws IOException {
        Scanner myScanner = new Scanner(System.in);
        String op;
        System.out.print("Topologia: ");
        System.out.println(this.mapaPartida.getTipo());
        System.out.print("Adyacencias: ");
        System.out.println(this.mapaPartida.getAngulos());
        printTablero(mapaPartida.matrix);
        while (!salirPartida)
        {
            System.out.println("1 -> añadir, 2 -> borrar, 7 -> reemplazar,3 -> pausar, 4 -> reanudar, 5 -> salir, 6-> guardar");

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
                    insertarNumero(i-1, j-1, numero);
                    printTablero(mapaPartida.matrix);
                    break;
                }
                case ("2"):
                {
                    System.out.println("Introduce la fila:");
                    int i = myScanner.nextInt();
                    System.out.println("Introduce la columna:");
                    int j = myScanner.nextInt();
                    borrarNumero(i-1, j-1);
                    printTablero(mapaPartida.matrix);
                    break;
                }
                case ("3"):
                {
                    System.out.println("Partida pausada");
                    pausarPartida();
                    break;
                }
                case ("4"):
                {
                    System.out.println("Partida reanudada");
                    reanudarPartida();
                    break;
                }
                case ("5"):
                {
                    salirPartida = true;
                    break;
                }
                case ("6"):
                {
                    guardarPartida();
                    break;
                }
                case ("7"):
                    System.out.println("Introduce la fila:");
                    int i = myScanner.nextInt();
                    System.out.println("Introduce la columna:");
                    int j = myScanner.nextInt();
                    System.out.println("Introduce el número:");
                    Integer numero = myScanner.nextInt();
                    reemplazarNumero(i-1, j-1, numero);
                    printTablero(mapaPartida.matrix);
                    break;
            }
        }
    }

    //están todos los números puestos?
    public boolean tableroLleno()
    {
        this.cantidadNumeros = (this.numerosInsertados.size() - this.numerosInicio.size());
        return (this.mapaPartida.interrogants == this.cantidadNumeros);
    }

    //todos los números están puestos
    public void acabarPartida()
    {
        //aquí entra si el Hidato está bien.
        if (this.mapaPartida.matriuCorrecte())
        {
            System.out.println("El hidato es correcto");
            salirPartida = true;
        }
        //aquí entra si el Hidato está mal, podemos escoger si queremos acabar la partida o
        else
        {
            System.out.println("El hidato no es correcto");
        }
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
            System.out.println(this.tiempoTotal);
        }
    }

    public void reanudarPartida()
    {
        data = new Date();
        this.horaInicio = data.getTime();
        this.paused = false;
    }

    //--------------------------------------ÉSTO NO SÉ SI ES CORRECTO EN CUANTO A DISEÑO
    public void guardarPartida() throws IOException
    {
        //para actualizar el reloj
        pausarPartida();
        reanudarPartida();

        ControladorPartida cP = new ControladorPartida();
        cP.guardarPartida(this);
    }

    //indica si en la casilla que apuntamos con i y j es para números o es una casilla no válida
    private boolean casillaNumero (int i, int j)
    {
        String casilla = this.mapaPartida.matrix[i][j];
        if (casilla != "#" && casilla != "*") return true;
        return false;
    }

    //inserta un número en el tablero si no ha sido insertado antes y si la casilla es valida.
    private void insertarNumero (int i, int j, int numero)
    {
        System.out.print("La fila es: ");
        System.out.println(i);
        System.out.print("La columna es: ");
        System.out.println(j);
        //para que no se pueda meter un número más grande que las casillas totales del hidato
        if (numero > mapaPartida.getNumeros() + mapaPartida.getInterrogants(mapaPartida.getMatrix()))
        {
            System.out.print("El numero más grande es: ");
            System.out.println(mapaPartida.getNumeros());
        }
        else
        {
            if (casillaNumero(i, j)) {
                if (this.numerosInicio.contains(numero))
                    System.out.println("No puedes borrar éste número (ya estaba al inicio de la partida).");
                if (!this.numerosInsertados.contains(numero)) {
                    this.numerosInsertados.add(numero);
                    this.mapaPartida.insertarNumero(numero, i, j);

                    //por si ése número que hemos puesto era el último que quedaba por poner
                    if (tableroLleno()) acabarPartida();
                } else System.out.println("El número que está intentando poner ya existe en el tablero.");
            } else System.out.println("La casilla no es válida para introducir un número.");
        }
    }

    private void borrarNumero (int i, int j)
    {
        String casilla = this.mapaPartida.matrix[i][j];
        if (casillaNumero(i, j) && casilla != "?") //si casilla apta para número y no hay ninguno puesto ya
        {
            if (!this.numerosInicio.contains(Integer.parseInt(casilla))) //si no era de los iniciales
            {
                //si no estaba al inicio sabemos sí o sí que lo hemos insertado después
                //entonces sabemos sí o sí que está en números insertados.
                int pos = this.numerosInsertados.indexOf(Integer.parseInt(casilla));
                this.numerosInsertados.remove(pos); //lo quitamos de los números insertados.
                this.mapaPartida.borrarNumero(i, j); //borramos el número si no era de los iniciales
            }
            else System.out.println("No puedes borrar éste número (ya estaba al inicio de la partida).");
        }
        else System.out.println("En la casilla no hay un número.");
    }

    private void reemplazarNumero (int i, int j, int numero)
    {
        //borra el que había y inserta el siguiente.
        //las funciones ya comprueban que se pueda realizar el reemplazo del número así que sólo las llamo.
        borrarNumero(i, j);
        insertarNumero(i, j, numero);
    }

    public static void printTablero(String[][] matrix){
        int filas = matrix.length;
        int columnas = matrix[0].length;
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

