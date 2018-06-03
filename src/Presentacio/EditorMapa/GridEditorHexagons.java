package Presentacio.EditorMapa;

import Presentacio.HexagonButton;

import javax.swing.*;
import java.awt.*;

public class GridEditorHexagons extends GridEditor {
    public GridEditorHexagons (String nomMapa, String[][] matrix, int columnes, int files, String topologia)
    {
        super(nomMapa, matrix, columnes, files, topologia);
    }

    @Override
    protected void setMatrix(String[][]m){
        matrix = new JButton[files][columnes];
        for(int i = 0; i < files; i++){
            for(int j = 0; j < columnes; j++){
                HexagonButton t = new HexagonButton();
                t.setMaximumSize(new Dimension(60,60));
                t.setText(m[i][j]);
                if((i%2) == 0) {
                    t.setBounds(60 * j, (60 -15) * i, 65, 65);
                    if(m[i][j].equals("#"))t.setVisible(false);
                    else t.setVisible(true);
                    t.setName(i + "," + j);
                }
                else{
                    t.setBounds(60 * j + 30, (60 -15)* i , 65, 65);
                    if(m[i][j].equals("#"))t.setVisible(false);
                    else t.setVisible(true);
                    t.setName(i + "," + j);
                }
                matrix[i][j] = t;
            }
        }
    }
}
