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

    /**
     * Instancia un GridEditor amb els paràmetres enviats.
     * @param nomMapa
     * @param matrix
     * @param files
     * @param columnes
     * @param topologia
     */
    public GridEditor(String nomMapa, String[][] matrix, int files, int columnes, String topologia) {
        this.nomMapa = nomMapa;
        this.files = files;
        this.columnes = columnes;
        this.topologia = topologia;
        setMatrix(matrix);
    }

    /**
     * Retorna el nombre de files del mapa que volem editar
     * @return un int amb el nombre de files
     */
    public int getFiles() { return this.files; }

    /**
     * Retorna el nombre de columnes del mapa que volem editar
     * @return un int amb el nombre de columnes
     */
    public int getColumnes() { return this.columnes; }

    /**
     * Retorna la tipologia del mapa que volem editar
     * @return un String amb la tipologia
     */
    public String getTipo() { return this.topologia; }

    /**
     * Prepara la matriu de botons pel mapa a editar
     * @param matrix
     */
    protected abstract void setMatrix(String[][] matrix);
}
