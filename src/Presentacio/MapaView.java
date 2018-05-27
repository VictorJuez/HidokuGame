package Presentacio;

import Domini.ControladorMapa;
import Domini.ControladorUsuari;
import Domini.Mapa.Mapa;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapaView {
    private JButton generarMapaRandomButton;
    private JTable tablaHidato;
    private JButton sortirButton;
    private JPanel MapaPanel;
    private JLabel activeUsertxt;
    private static JFrame MapaFrame;

    private ControladorUsuari controladorUsuari;
    private ControladorMapa controladorMapa;

    public MapaView() {
        controladorUsuari = new ControladorUsuari();
        controladorMapa = new ControladorMapa();
        activeUsertxt.setText(controladorUsuari.getUsuariActiu());


        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        generarMapaRandomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mapa mapa = controladorMapa.generarHidato();
                Object[] header = new String[mapa.getColumnas()];
                for(int i=0; i<header.length; ++i) header[i] = i;
                JTable table = new JTable(mapa.getMatrix(), header);
                JScrollPane jScrollPane = new JScrollPane(table);
                MapaFrame.add(jScrollPane, BorderLayout.CENTER);
                SwingUtilities.updateComponentTreeUI(MapaFrame);
            }
        });
    }

    public void createFrame(){
        MapaFrame = new JFrame("Login");
        MapaFrame.setContentPane(new MapaView().MapaPanel);
        MapaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MapaFrame.pack();
        MapaFrame.setVisible(true);
    }
}
