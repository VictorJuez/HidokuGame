package Presentacio;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class mapaFactoryButton {

    public mapaButton getMapaButton(int columnes, int files, String[][] m, String tipo){
        mapaButton mapa = null;
        switch (tipo){
            case "T":
                mapa = new mapaButtonTriangle(m,files,columnes,tipo);
                break;
            case "Q":
                mapa = new mapaButtonQuadrat(m,files,columnes,tipo);
                break;
            case "H":
                mapa = new mapaButtonHexagon(m,files,columnes,tipo);
                break;
        }
        return mapa;
    }
    /*
        for(int i = 0; i < files; i++){
            for(int j = 0; j < columnes; j++){
                if ((i + j)%2 == 0){
                    TriangleUpButton t = new TriangleUpButton();
                    t.setMaximumSize(new Dimension(60,50));
                    t.setText(m[i][j]);
                    matrix[i][j] = t;
                }
                else{
                    TriangleDownButton t = new TriangleDownButton();
                    t.setMaximumSize(new Dimension(60,50));
                    t.setText(m[i][j]);
                    matrix[i][j] = t;
                }
            }
        }*/
}
