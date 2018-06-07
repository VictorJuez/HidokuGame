package Presentacio;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class mapaButtonQuadrat extends mapaButton {
    public mapaButtonQuadrat(String[][] matrix, int files, int columnes, String tipo, Vector<Integer> existents) {
        super(matrix, files, columnes, tipo, existents);
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
