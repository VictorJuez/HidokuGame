package Presentacio.EditorMapa;

import Presentacio.QuadratButton;

import javax.swing.*;
import java.awt.*;

public class GridEditorQuadrats extends GridEditor {
    public GridEditorQuadrats(String[][] matrix, int files, int columnes, String topologia)
    {
        super(matrix, files, columnes, topologia);
    }

    @Override
    protected void setMatrix(String[][]m){
        matrix = new JButton[files][columnes];
        for(int i = 0; i < files; i++){
            for(int j = 0; j < columnes; j++){
                QuadratButton t = new QuadratButton();
                t.setMaximumSize(new Dimension(60,60));
                t.setBounds(60 * j, 60 * i, 65, 65);
                t.setText(m[i][j]);
                if(m[i][j].equals("#"))t.setVisible(false);
                else t.setVisible(true);
                t.setName(i + "," + j);
                matrix[i][j] = t;
            }
        }
    }
}
