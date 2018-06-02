package Presentacio;

import javax.swing.*;

public abstract class mapaButton {
    public JButton[][] matrix;
    protected int files;
    protected int columnes;
    protected String tipo;

    public mapaButton(String[][] matrix, int files, int columnes, String tipo) {
        this.files = files;
        this.columnes = columnes;
        this.tipo = tipo;
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
