package Domini.Mapa;

import java.util.Collections;
import java.util.UUID;
import java.util.Vector;

import static java.util.Collections.sort;


public class adyacencias{
    private String valor;
    boolean visitat;
    private Integer x;
    private Integer y;
    private Integer z;
    private Vector<Integer> ad;

    public adyacencias(int y, int x, String valor, int columnas){
        this.y = y;
        this.x = x;
        this.z = x + y*columnas;
        this.valor = valor;
        this.visitat = false;
        this.ad = new Vector<>();
    }

    /**
     * Devuelve el valor de Z
     * @return Devuelve un entero que dice en que posicion de la array se encuentra el numero
     */
    public Integer getZ() {
        return z;
    }

    /**
     * Devulve la Columna en que se encuentra la casilla
     * @return  Devuelve un entero que indica en que Columna se encuentra la casilla
     */
    public Integer getX() {
        return x;
    }

    /**
     * Devulve la Fila en que se encuentra la casilla
     * @return  Devuelve un entero que indica en que Fila se encuentra la casilla
     */
    public Integer getY() { return y; }

    /**
     * Devuelve las casillas que son adyacentes a la casilla actual
     * @return  Devuelve un vector que indica que casillas dentro de la tablaAD son adyacentes
     */
    public Vector<Integer> getAd() {
        return ad;
    }

    /**
     * Añade al vector de adyacencias de la casilla actual el indice de la tablaAD donde se encuentra la adyacencia i.
     * @param i
     */
    public void add(Integer i) {
        ad.add(i);
    }

    /**
     * Devuelve el valor de la adyacencia (casilla)
     * @return  Devuelve un String con el valor que tiene la casilla
     */
    public String getValor() {
        return valor;
    }
};