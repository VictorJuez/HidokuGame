////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
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
import java.util.Vector;

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
    private Vector<Integer> numerosInserits; //para el control de numeros repetidos

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
    private JButton numberButton;

    public EditorMapa() {
        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showCreadorMapa();
            }
        });

        següentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controlCorrectesa()) {
                    String[][] matrixHidato = getMatrixHidato();
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
                    System.out.println(topologia + " " + adjacencies);
                    UtilsMapaDecorator uMD = new UtilsMapaDecorator(m);
                    if(uMD.conteValorsNoAdmesos()) JOptionPane.showMessageDialog(null, "Encara falten caselles per omplir");
                    else if(!m.matriuBenInicialitzada()) JOptionPane.showMessageDialog(null, "El primer i últim número han d'apareixer sempre");
                    else {
                        if (uMD.hidatoValido()) {
                            //lo guardo
                            ControladorMapa.saveMapa(m, nomMapa);
                            JOptionPane.showMessageDialog(null, "El mapa ha sigut guardat amb èxit");
                            Main.showMenu();
                        } else JOptionPane.showMessageDialog(null, "El mapa proposat no té solució");
                    }
                }
            }
        });

        editorMapaPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                nextNumLabel.setText(numSelected);
                numerosInserits = new Vector<>();
                charSelected = String.valueOf(1);
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
                if (Integer.valueOf(charSelected )< files * columnes)
                {
                    incIndex();
                    charSelected = String.valueOf(index);
                }
            }
        });
        numberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                charSelected = String.valueOf(index);
            }
        });
    }

    /**
     * Recull els paràmetres seleccionats pel mapa
     */
    //recoge los datos marcados por la anterior pantalla (CreadorMapa) y instancia el GridEditor
    private void processParameters() {
        nomMapa = cM.getNommapa();
        topologia = cM.getTipus();
        adjacencies = cM.getTipusAdjacencies();
        files = Integer.valueOf(cM.getNombreFiles());
        columnes = Integer.valueOf(cM.getNombreColumnes());
    }

    /**
     * Crea una matriu de botons per poder crear un mapa de forma interactiva
     */
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
        gE = geF.getGridEditor(nomMapa, matrix, files, columnes, topologia);

        for (int i = 0; i < files; ++i)
            for (int j = 0; j < columnes; ++j) {
                ButtonGridPanel.add(GridEditor.matrix[i][j]);
                gE.matrix[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton myButton = (JButton) e.getSource();
                        String charOnButton = myButton.getText();
                        if (!charOnButton.equals("*") && !charOnButton.equals("#") && !charOnButton.equals("?") && !charOnButton.equals("X"))
                        {
                            numerosInserits.remove(numerosInserits.indexOf(Integer.valueOf(charOnButton)));
                        }
                        myButton.setText(charSelected);
                        if (!charSelected.equals("*") && !charSelected.equals("#") && !charSelected.equals("?") && index < (files * columnes)) incIndex();
                    }
                });
            }
        ButtonGridPanel.add(GridEditor.matrix[0][0]);
        ButtonGridPanel.revalidate();
        ButtonGridPanel.repaint();
    }

    /**
     * Retorna l'editor de mapes
     * @return un JPanel amb l'editor de mapes
     */
    public JPanel getEditorMapa() {
        return editorMapaPanel;
    }

    /**
     * Incrementa l'index (que serveix per saber que nombre estem insertant) i actualitza el nombre seleccionat
     */
    private void incIndex() {
        numerosInserits.add(index);
        index += 1;
        numSelected = String.valueOf(index);
        nextNumLabel.setText(numSelected);
        charSelected = numSelected;
    }

    /**
     * Decrementa l'index (que serveix per saber que nombre estem insertant) i actualitza el nombre seleccionat
     */
    private void decIndex() {
        if (index > 1) index -= 1;
        numSelected = String.valueOf(index);
        nextNumLabel.setText(numSelected);
        charSelected = numSelected;
    }

    /**
     * Retorna la matriu de l'Hidato segons el valor que hem assignat als botons
     * @return un array d'Strings
     */
    private String[][] getMatrixHidato()
    {
        String[][] matrix = new String[files][columnes];
        for (int i = 0; i < files; ++i) for (int j = 0; j < columnes; ++j) matrix[i][j] = gE.matrix[i][j].getText();
        return matrix;
    }

    /**
     * Vigila que es compleixin tots els paràmetres d'un mapa correcte (que no hagi numeros repetits )
     * @return un boolean indicant si el mapa es correcte (que no significa que tingui solucio)
     */
    private boolean controlCorrectesa()
    {
        for (int a = 0; a < numerosInserits.size(); a++)
        {
            if (numerosInserits.indexOf(numerosInserits.get(a)) != a)
            {
                JOptionPane.showMessageDialog(null, "Hi ha nombres repetits");
                return false;
            }
        }
        for (int i = 0; i < files; ++i) for (int j = 0; j < columnes; ++j)
        {
            if (gE.matrix[i][j].equals("X"))
            {
                JOptionPane.showMessageDialog(null, "Hi ha caselles sense valor (X)");
                return false;
            }
        }
        return true;
    }
}