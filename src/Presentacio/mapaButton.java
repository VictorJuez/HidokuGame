package Presentacio;

import javax.swing.*;
import java.util.Vector;

public abstract class mapaButton {
    public JButton[][] matrix;
    protected int files;
    protected int columnes;
    protected String tipo;
    protected Vector<Integer> ex = new Vector<>();

    public mapaButton(String[][] matrix, int files, int columnes, String tipo, Vector<Integer> existents) {
        this.files = files;
        this.columnes = columnes;
        this.tipo = tipo;
        this.ex = existents;
        setMatrix(matrix);
    }

    public int getFiles() {
        return files;
    }

    public int getColumnes() {
        return columnes;
    }

    public String getTipo() {
        return tipo;
    }

    protected abstract void setMatrix(String[][] matrix);
}
