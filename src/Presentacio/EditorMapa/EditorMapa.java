package Presentacio.EditorMapa;

import Presentacio.CreadorMapa;
import Presentacio.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;

public class EditorMapa {
    private static GridEditor gE;
    private static CreadorMapa cM;
    private static String nomMapa;
    private static String topologia;
    private static String adjacencies;
    private static int columnes;
    private static int files;

    private JPanel ButtonGridPanel;
    private JPanel editorMapaPanel;
    private JLabel CrearMapaLabel;
    private JLabel astLabel;
    private JLabel hashLabel;
    private JLabel intLabel;
    private JButton enrereButton;
    private JButton següentButton;

    public EditorMapa() {
        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showCreadorMapa();
            }
        });
        següentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { }
        });
        editorMapaPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                processParameters();
                processGridEditor();
            }
        });
    }

    //recoge los datos marcados por la anterior pantalla (CreadorMapa) y instancia el GridEditor
    private void processParameters()
    {
        nomMapa = cM.getNommapa();
        topologia = cM.getTipus();
        adjacencies =  cM.getTipusAdjacencies();
        files = Integer.valueOf(cM.getNombreFiles());
        columnes = Integer.valueOf(cM.getNombreColumnes());

        String[][] matrix = new String[files][columnes];
        for (int i = 0; i < files; ++i) for (int j = 0; j < columnes; ++j) matrix[i][j] = "-";

        GridEditorFactory fact = new GridEditorFactory();
        gE = fact.getGridEditor(nomMapa, matrix, columnes, files, topologia);
    }

    private void processGridEditor()
    {
        BorderLayout grid = new BorderLayout();
        ButtonGridPanel.removeAll();
        ButtonGridPanel.setLayout(grid);
        GridEditorFactory geF = new GridEditorFactory();
        String[][] matrix = new String[files][columnes];
        for (int i = 0; i < files; ++i) for (int j = 0; j < columnes; ++j) matrix[i][j] = "-";
        gE = geF.getGridEditor(nomMapa, matrix, columnes, files, topologia);

        for (int i = 0; i < files; ++i) for (int j = 0; j < columnes; ++j) ButtonGridPanel.add(GridEditor.matrix[i][j]);
        ButtonGridPanel.add(GridEditor.matrix[0][0]);
        ButtonGridPanel.revalidate();
        ButtonGridPanel.repaint();
    }

    public JPanel getEditorMapa() {
        return editorMapaPanel;
    }
}