package Presentacio;

import Domini.ControladorMapa;
import Domini.ControladorUsuari;
import Domini.Mapa.Mapa;
import Domini.Partida;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.util.Vector;

public class MapaView {
    private JButton generarMapaRandomButton;
    private JTable tablaHidato;
    private JButton sortirButton;
    private JPanel MapaPanel;
    private JLabel activeUsertxt;
    private JButton menuPrincipal;

    private ControladorUsuari controladorUsuari;
    private ControladorMapa controladorMapa;
    private Mapa mapa;
    private Partida partida;
    private int i = 0;
    private Vector<Integer> restants;

    public MapaView() {
        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        generarMapaRandomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarMapaRandomButton.setVisible(false);
                mapa = controladorMapa.generarHidato();
                restants = mapa.getNumerosRestants();
                String[] header = new String[mapa.getColumnas()];
                for(int i=0; i<header.length; ++i) header[i] = String.valueOf(i);
                JTable table = new JTable(mapa.getMatrix(), header);
                TableModel tm = table.getModel();
                tablaHidato.setModel(tm);
            }
        });
        tablaHidato.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablaHidato.rowAtPoint(e.getPoint());
                int col = tablaHidato.columnAtPoint(e.getPoint());

                partida = new Partida(mapa);
                partida.insertarNumero(row, col, restants.get(i++));

                String[] header = new String[mapa.getColumnas()];
                for(int i=0; i<header.length; ++i) header[i] = String.valueOf(i);
                JTable table = new JTable(partida.getMatrixMapa(), header);
                TableModel tm = table.getModel();
                tablaHidato.setModel(tm);
            }
        });
        menuPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showMain();
            }
        });

        MapaPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                controladorUsuari = new ControladorUsuari();
                controladorMapa = new ControladorMapa();
                activeUsertxt.setText(controladorUsuari.getUsuariActiu());
            }
        });
    }

    public JPanel getMapaPanel() {
        return MapaPanel;
    }
}
