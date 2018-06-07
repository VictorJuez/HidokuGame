package Presentacio;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class mapaFactoryButton {

    public mapaButton getMapaButton(int columnes, int files, String[][] m, String tipo, Vector<Integer> existents){
        mapaButton mapa = null;
        switch (tipo){
            case "T":
                mapa = new mapaButtonTriangle(m,files,columnes,tipo,existents);
                break;
            case "Q":
                mapa = new mapaButtonQuadrat(m,files,columnes,tipo, existents);
                break;
            case "H":
                mapa = new mapaButtonHexagon(m,files,columnes,tipo, existents);
                break;
        }
        return mapa;
    }

}
