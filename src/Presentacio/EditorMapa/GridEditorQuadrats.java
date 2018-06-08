////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Presentacio.EditorMapa;

import Presentacio.QuadratButton;

import javax.swing.*;
import java.awt.*;

public class GridEditorQuadrats extends GridEditor {
    /**
     * Crida a la constructora de la clase abstracta
     * @param nomMapa
     * @param matrix
     * @param files
     * @param columnes
     * @param topologia
     */
    public GridEditorQuadrats(String nomMapa, String[][] matrix, int files, int columnes, String topologia)
    {
        super(nomMapa, matrix, files, columnes, topologia);
    }

    /**
     * Retorna la matriu de botons de la topologia escollida
     * @param m
     */
    @Override
    protected void setMatrix(String[][] m){
        matrix = new JButton[files][columnes];
        for(int i = 0; i < files; i++){
            for(int j = 0; j < columnes; j++){
                QuadratButton t = new QuadratButton();
                t.setMaximumSize(new Dimension(60,60));
                t.setBounds(60 * j, 60 * i, 65, 65);
                t.setText(m[i][j]);
                t.setVisible(true);
                t.setName(i + "," + j);
                matrix[i][j] = t;
            }
        }
    }
}
