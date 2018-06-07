package Presentacio;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Vector;

public class mapaButtonTriangle extends mapaButton {
    public mapaButtonTriangle(String[][] matrix, int files, int columnes, String tipo, Vector<Integer> existents) {
        super(matrix, files, columnes, tipo,existents);
    }

    @Override
    protected void setMatrix(String[][]m){
        matrix = new JButton[files][columnes];
        for(int i = 0; i < files; i++){
            for(int j = 0; j < columnes; j++){
                if ((i + j)%2 == 0){
                    TriangleUpButton t = new TriangleUpButton();
                    t.setMaximumSize(new Dimension(60,51));
                    t.setText(m[i][j]);
                    t.setBounds(30 * j, 50 * i, 60, 51);
                    if(m[i][j].equals("#"))t.setVisible(false);
                    else{
                        t.setVisible(true);
                        if (!m[i][j].equals("?") && !m[i][j].equals("*")){
                            Integer x = Integer.valueOf(m[i][j]);
                            if (ex.contains(x)) t.setModificable(false);
                        }
                    }
                    t.setName(i + "," + j);
                    matrix[i][j] = t;
                }
                else{
                    TriangleDownButton t = new TriangleDownButton();
                    t.setMaximumSize(new Dimension(60,51));
                    t.setText(m[i][j]);
                    t.setBounds(30 * j, 50 * i, 60, 51);
                    if(m[i][j].equals("#"))t.setVisible(false);
                    else{
                        t.setVisible(true);
                        if (!m[i][j].equals("?") && !m[i][j].equals("*")){
                            Integer x = Integer.valueOf(m[i][j]);
                            if (ex.contains(x)) t.setModificable(false);
                        }
                    }
                    t.setName(i + "," + j);
                    matrix[i][j] = t;
                }

            }
        }
    }
}
