package Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreadorMapa {
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

            }
        });

        EnrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showMenu();
            }
        });
    }

    public JPanel getCreadorMapasPanel() { return CreadorMapaPanel; }

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
