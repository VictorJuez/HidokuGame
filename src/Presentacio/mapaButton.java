package Presentacio;

import javax.swing.*;
import java.util.Vector;

public class mapaButton extends AbstractButton {
    public JButton[][] matrix;
    private int files;
    private int columnes;

    public mapaButton(int files, int columnes, String[][] m) {
        this.files = files;
        this.columnes = columnes;
        this.matrix = new JButton[files][columnes];
        for(int i = 0; i < files; i++){
            for(int j = 0; j < columnes; j++){
                if ((i + j)%2 == 0){
                    TriangleUpButton t = new TriangleUpButton();
                    t.setText(m[i][j]);
                    matrix[i][j] = t;
                }
                else{
                    TriangleDownButton t = new TriangleDownButton();
                    t.setText(m[i][j]);
                    matrix[i][j] = t;
                }
            }
        }
    }

        public JButton[][] getMatrix() {
            return matrix;
        }

        public int getColumnes() {
        return columnes;
    }

    public int getFiles() {

        return files;
    }
}
