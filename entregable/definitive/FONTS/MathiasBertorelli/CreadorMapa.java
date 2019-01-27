////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Presentacio;

import Domini.ControladorMapa;
import Domini.Mapa.Mapa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class CreadorMapa {
    ControladorMapa cM;

    private static String nommapa = new String();
    private static String tipus = new String();
    private static String tipusAdjacencies = new String();
    private static String nombreFiles = new String();
    private static String nombreColumnes = new String();

    private JTextField nomMapa;
    private JComboBox tipusMapa;
    private JComboBox adjacencies;
    private JComboBox files;
    private JComboBox columnes;
    private JButton SegüentButton;
    private JButton EnrereButton;
    private JPanel CreadorMapaPanel;

    public CreadorMapa()
    {
        setUpTipusMapaBox();
        setUpAdjacenciesBox();
        setUpFilesBox();
        setUpColumnesBox();

        SegüentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setNomMapa();
                setTipus();
                setTipusAdjacencies();
                setNombreFiles();
                setNombreColumnes();
                if (nommapa.equals("")) JOptionPane.showMessageDialog(null, "Escriu un nom pel mapa");
                //los parámetros están escogidos
                else {
                    cM.getMapa(nommapa);
                    if (cM.getMapa(nommapa) == null) {
                        Main.showEditorMapa();
                    }
                    else JOptionPane.showMessageDialog(null, "Aquest nom ja existeix");
                }
            }
        });

        EnrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showMenu();
            }
        });
        //TIPUS DE MAPA
        tipusMapa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { setTipus(); }
        });
        //TIPUS D'ADJACENCIES
        adjacencies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { setTipusAdjacencies(); }
        });
        //NOMBRE DE FILES
        files.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { setNombreFiles(); }
        });
        //NOMBRE DE COLUMNES
        columnes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { setNombreColumnes(); }
        });
    }

    /**
     * Retorna el nom seleccionat pel mapa
     * @return String amb el nom del mapa
     */
    public static String getNommapa() {
        return nommapa;
    }

    /**
     * Retorna la tipologia seleccionada pel mapa
     * @return String amb la tipologia del mapa
     */
    public static String getTipus() {
        return tipus;
    }

    /**
     * Retorna el tipus d'adjacencies del mapa
     * @return String amb el tipus d'adjacències del mapa
     */
    public static String getTipusAdjacencies() {
        return tipusAdjacencies;
    }

    /**
     * Retorna el nombre de files del mapa
     * @return un String amb el nombre de files del mapa
     */
    public static String getNombreFiles() { return nombreFiles; }

    /**
     * Retorna el nombre de columnes del mapa
     * @return un String amb el nombre de columnes del mapa
     */
    public static String getNombreColumnes() { return nombreColumnes; }

    /**
     * Fixa la variable nommapa amb el nom indicat
     */
    public void setNomMapa()
    {
        nommapa = nomMapa.getText();
    }

    /**
     * Fixa la variable tipus (topologia) amb el tipus indicat
     */
    public void setTipus() { this.tipus = (String)tipusMapa.getSelectedItem(); }

    /**
     * Fixa la variable adjacencies amb el tipus d'adjacencies indicat
     */
    public void setTipusAdjacencies() { this.tipusAdjacencies = (String)adjacencies.getSelectedItem(); }

    /**
     * Fixa la variable nombreFiles amb el nombre de files indicat
     */
    public void setNombreFiles() { this.nombreFiles = (String)files.getSelectedItem(); }

    /**
     * Fixa la variable nombreColumnes amb el nombre de columnes indicat
     */
    public void setNombreColumnes() { this.nombreColumnes = (String)columnes.getSelectedItem(); }

    /**
     * Prepara el Box de tipus mapa amb les topologies disponibles
     */
    private void setUpTipusMapaBox() {
        String[] aux = new String[3]; aux[0] = "Quadrats"; aux[1] = "Triangles"; aux[2] = "Hexagons";
        this.tipusMapa.setModel(new javax.swing.DefaultComboBoxModel(aux));
        if(aux.length>0)this.tipusMapa.setSelectedIndex(0);
    }

    /**
     * Prepara el Box de les adjacencies amb les adjacencies disponibles
     */
    private void setUpAdjacenciesBox() {
        String[] aux = new String[2]; aux[0] = "Costats"; aux[1] = "Angles";
        this.adjacencies.setModel(new javax.swing.DefaultComboBoxModel(aux));
        if(aux.length>0)this.adjacencies.setSelectedIndex(0);
    }

    /**
     * Prepara el Box de les files els valors disponibles
     */
    private void setUpFilesBox() {
        String[] aux = new String[14];
        for (int i = 1; i < 15; ++i) aux[i-1] = String.valueOf(i+1);
        this.files.setModel(new javax.swing.DefaultComboBoxModel(aux));
        if(aux.length>0)this.files.setSelectedIndex(0);
    }

    /**
     * Prepara el Box de les columnes els valors disponibles
     */
    private void setUpColumnesBox() {
        String[] aux = new String[14];
        for (int i = 1; i < 15; ++i) aux[i-1] = String.valueOf(i+1);
        this.columnes.setModel(new javax.swing.DefaultComboBoxModel(aux));
        if(aux.length>0)this.columnes.setSelectedIndex(0);
    }

    /**
     * Retorna aquest mateix menu
     * @return un JPanel amb aquest mateix menu
     */
    public JPanel getCreadorMapasPanel() { return CreadorMapaPanel; }

}
