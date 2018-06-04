package Presentacio.EditorMapa;

import Dades.MapaDAO;
import Domini.ControladorMapa;
import Domini.Mapa.Mapa;
import Domini.Mapa.MapaDecorator;
import Domini.Mapa.MapaFactory;
import Domini.Mapa.UtilsMapaDecorator;
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

    private String charSelected = "1";
    private String numSelected = "1";
    private int index = 1;

    private JPanel ButtonGridPanel;
    private JPanel editorMapaPanel;
    private JLabel CrearMapaLabel;
    private JLabel astLabel;
    private JLabel hashLabel;
    private JLabel intLabel;
    private JButton enrereButton;
    private JButton següentButton;
    private JButton intButton;
    private JButton AstButton;
    private JButton HashButton;
    private JLabel nextNumLabel;
    private JButton nextNumberButton;
    private JButton prevNumberButton;

    public EditorMapa() {
        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showCreadorMapa();
            }
        });

        següentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String[][] matrixHidato = getMatrixHidato();
                for (int i = 0; i < files; ++i) for (int j = 0; j < columnes; ++j) System.out.println(matrixHidato[i][j]);
                MapaFactory mF = new MapaFactory();
                switch (topologia) {        //enric fins
                    case "Quadrats":
                        topologia = "Q";
                        break;
                    case "Triangles":
                        topologia = "T";
                        break;
                    case "Hexagons":
                        topologia = "H";
                        break;
                }
                if (adjacencies.equals("Angles")) adjacencies = "CA";
                else adjacencies = "C";     //fi enric

                Mapa m = mF.getMapa(topologia, adjacencies, matrixHidato);
                //he de ver si tiene solución
                System.out.println(topologia+ " " + adjacencies);
                UtilsMapaDecorator uMD = new UtilsMapaDecorator(m);
                if (uMD.hidatoValido())
                {
                    //lo guardo
                    ControladorMapa.saveMapa(m, nomMapa);
                    JOptionPane.showMessageDialog(null, "El mapa ha sigut guardat amb èxit");
                }
                else JOptionPane.showMessageDialog(null, "El mapa proposat no té solució");
            }
        });

        editorMapaPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                nextNumLabel.setText(numSelected);
                processParameters();
                processGridEditor();
            }
        });

        intButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                charSelected = "?";
            }
        });

        AstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                charSelected = "*";
            }
        });

        HashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                charSelected = "#";
            }
        });

        prevNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decIndex();
                charSelected = String.valueOf(index);
            }
        });
        nextNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incIndex();
                charSelected = String.valueOf(index);
            }
        });
    }

    //recoge los datos marcados por la anterior pantalla (CreadorMapa) y instancia el GridEditor
    private void processParameters() {
        nomMapa = cM.getNommapa();
        topologia = cM.getTipus();
        adjacencies = cM.getTipusAdjacencies();
        files = Integer.valueOf(cM.getNombreFiles());
        columnes = Integer.valueOf(cM.getNombreColumnes());

        String[][] matrix = new String[files][columnes];
        for (int i = 0; i < files; ++i) for (int j = 0; j < columnes; ++j) matrix[i][j] = "-";

        GridEditorFactory fact = new GridEditorFactory();
        gE = fact.getGridEditor(nomMapa, matrix, columnes, files, topologia);
    }

    private void processGridEditor() {
        BorderLayout grid = new BorderLayout();
        ButtonGridPanel.removeAll();
        ButtonGridPanel.setLayout(grid);
        GridEditorFactory geF = new GridEditorFactory();
        String[][] matrix = new String[files][columnes];
        for (int i = 0; i < files; ++i)
            for (int j = 0; j < columnes; ++j) {
                matrix[i][j] = "X";
            }
        gE = geF.getGridEditor(nomMapa, matrix, columnes, files, topologia);

        for (int i = 0; i < files; ++i)
            for (int j = 0; j < columnes; ++j) {
                ButtonGridPanel.add(GridEditor.matrix[i][j]);
                gE.matrix[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton myButton = (JButton) e.getSource();
                        myButton.setText(charSelected);
                        incIndex();
                    }
                });
            }
        ButtonGridPanel.add(GridEditor.matrix[0][0]);
        ButtonGridPanel.revalidate();
        ButtonGridPanel.repaint();
    }

    public JPanel getEditorMapa() {
        return editorMapaPanel;
    }

    private void incIndex() {
        if (!charSelected.equals("*") && !charSelected.equals("#") && index < (files * columnes)) index += 1;
        numSelected = String.valueOf(index);
        nextNumLabel.setText(numSelected);
        charSelected = numSelected;
    }

    private void decIndex() {
        if (index > 0) index -= 1;
        numSelected = String.valueOf(index);
        nextNumLabel.setText(numSelected);
        charSelected = numSelected;
    }

    private String[][] getMatrixHidato()
    {
        String[][] matrix = new String[files][columnes];
        for (int i = 0; i < files; ++i) for (int j = 0; j < columnes; ++j) matrix[i][j] = gE.matrix[i][j].getText();
        return matrix;
    }
}