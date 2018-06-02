package Presentacio;

import javax.swing.*;
import java.awt.*;

public class editorMapaButton {
    private JButton[][] matrix;
    private int files;
    private int columnes;

    public editorMapaButton(int files, int columnes, String[][] matrixS)
    {
        this.files = files;
        this.columnes = columnes;
        this.matrix = new JButton[files][columnes];

    }
}
