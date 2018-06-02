package Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreadorMapa {
    private String nommapa = new String();
    private String tipus = new String();
    private String tipusAdjacencies = new String();
    private String nombreFiles = new String();
    private String nombreColumnes = new String();

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
                if (nommapa.equals("")) JOptionPane.showMessageDialog(null, "Escriu un nom pel mapa");
                //hay parámetros válidos
                else {
                    if (tipus.equals("Quadrats")) Main.showEditorMapa();
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

    public void setTipus() { this.tipus = (String)tipusMapa.getSelectedItem(); }
    public void setTipusAdjacencies() { this.tipusAdjacencies = (String)adjacencies.getSelectedItem(); }
    public void setNombreFiles() { this.nombreFiles = (String)files.getSelectedItem(); }
    public void setNombreColumnes() { this.nombreColumnes = (String)columnes.getSelectedItem(); }

    public JPanel getCreadorMapasPanel() { return CreadorMapaPanel; }

    public void setNomMapa()
    {
        nommapa = nomMapa.getText();
    }

    private void setUpTipusMapaBox()
    {
        String[] aux = new String[3]; aux[0] = "Quadrats"; aux[1] = "Triangles"; aux[2] = "Hexagons";
        this.tipusMapa.setModel(new javax.swing.DefaultComboBoxModel(aux));
        if(aux.length>0)this.tipusMapa.setSelectedIndex(0);
    }

    private void setUpAdjacenciesBox()
    {
        String[] aux = new String[2]; aux[0] = "Costats"; aux[1] = "Angles";
        this.adjacencies.setModel(new javax.swing.DefaultComboBoxModel(aux));
        if(aux.length>0)this.adjacencies.setSelectedIndex(0);
    }

    private void setUpFilesBox()
    {
        String[] aux = new String[15];
        for (int i = 0; i < 15; ++i) aux[i] = String.valueOf(i+1);
        this.files.setModel(new javax.swing.DefaultComboBoxModel(aux));
        if(aux.length>0)this.files.setSelectedIndex(0);
    }

    private void setUpColumnesBox()
    {
        String[] aux = new String[15];
        for (int i = 0; i < 15; ++i) aux[i] = String.valueOf(i+1);
        this.columnes.setModel(new javax.swing.DefaultComboBoxModel(aux));
        if(aux.length>0)this.columnes.setSelectedIndex(0);
    }
}
