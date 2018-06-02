package Presentacio;

import javax.swing.*;
import java.awt.*;

public class mapaButtonQuadrat extends mapaButton {
    public mapaButtonQuadrat(String[][] matrix, int files, int columnes, String tipo) {
        super(matrix, files, columnes, tipo);
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
                t.setVisible(true);
                t.setName(i + "," + j);
                matrix[i][j] = t;
            }
        }
    }
}
