////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Presentacio.EditorMapa;

import javax.swing.*;

public abstract class GridEditor {
    public static JButton[][] matrix;
    protected String nomMapa;
    protected int files;
    protected int columnes;
    protected String topologia;

    public GridEditor(String nomMapa, String[][] matrix, int files, int columnes, String topologia) {
        this.nomMapa = nomMapa;
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
