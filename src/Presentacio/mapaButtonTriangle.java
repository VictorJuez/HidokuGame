package Presentacio;

import javax.swing.*;
import java.awt.*;

public class mapaButtonTriangle extends mapaButton {
    public mapaButtonTriangle(String[][] matrix, int files, int columnes, String tipo) {
        super(matrix, files, columnes, tipo);
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
                    t.setVisible(true);
                    t.setName(i + "," + j);
                    matrix[i][j] = t;
                }
                else{
                    TriangleDownButton t = new TriangleDownButton();
                    t.setMaximumSize(new Dimension(60,50));
                    t.setText(m[i][j]);
                    t.setBounds(30 * j, 50 * i, 60, 55);
                    t.setVisible(true);
                    t.setName(i + "," + j);
                    matrix[i][j] = t;
                }
            }
        }
    }
}
