////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Presentacio.EditorMapa;

import Presentacio.TriangleDownButton;
import Presentacio.TriangleUpButton;

import javax.swing.*;
import java.awt.*;

public class GridEditorTriangles extends GridEditor {
    public GridEditorTriangles(String nomMapa, String[][] matrix, int columnes, int files, String topologia)
    {
        super(nomMapa, matrix, columnes, files, topologia);
    }

    @Override
    protected void setMatrix(String[][]m){
        matrix = new JButton[files][columnes];
        for(int i = 0; i < files; i++){
            for(int j = 0; j < columnes; j++){
                if ((i + j)%2 == 0){
                    TriangleUpButton t = new TriangleUpButton();
                    t.setMaximumSize(new Dimension(60,50));
                    t.setText(m[i][j]);
                    t.setBounds(30 * j, 50 * i, 60, 55);
                    if(m[i][j].equals("#"))t.setVisible(false);
                    else t.setVisible(true);
                    t.setName(i + "," + j);
                    matrix[i][j] = t;
                }
                else{
                    TriangleDownButton t = new TriangleDownButton();
                    t.setMaximumSize(new Dimension(60,50));
                    t.setText(m[i][j]);
                    t.setBounds(30 * j, 50 * i, 60, 55);
                    if(m[i][j].equals("#"))t.setVisible(false);
                    else t.setVisible(true);
                    t.setName(i + "," + j);
                    matrix[i][j] = t;
                }
            }
        }
    }
}
