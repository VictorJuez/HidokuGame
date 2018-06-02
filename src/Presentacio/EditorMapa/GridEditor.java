package Presentacio.EditorMapa;

import javax.swing.*;

public abstract class GridEditor {
    public JButton[][] matrix;
    protected int files;
    protected int columnes;
    protected String topologia;

    public GridEditor(String[][] matrix, int files, int columnes, String topologia) {
        this.files = files;
        this.columnes = columnes;
        this.topologia = topologia;
        setMatrix(matrix);
    }

    public int getFiles() { return this.files; }
    public int getColumnes() { return this.columnes; }

    public String getTipo() { return this.topologia; }

    protected abstract void setMatrix(String[][] matrix);
}
