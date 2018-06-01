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

    public Integer getZ() {
        return z;
    }
    public Integer getX() {
        return x;
    }
    public Integer getY() { return y; }


    public Vector<Integer> getAd() {
        return ad;
    }

    public void add(Integer i) {
        ad.add(i);
    }

    public String getValor() {
        return valor;
    }
    public void setValorAdyacencia(String value){
        this.valor = value;
    }
};