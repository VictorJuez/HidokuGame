package Presentacio;

import Domini.ControladorMapa;
import Domini.ControladorUsuari;
import Domini.Mapa.Mapa;
import Domini.Partida;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class MapaView {
    private JButton generarMapaRandomButton;
    private JButton sortirButton;
    private JPanel MapaPanel;
    private JLabel activeUsertxt;
    private JButton menuPrincipal;
    private JPanel panel1;

    private ControladorUsuari controladorUsuari;
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
                mapa = ControladorMapa.generarHidato();
                //TriangleUpButton t = new TriangleUpButton();
                //t.setText("1");
                //t.setVisible(true);
                //t.setPreferredSize(new Dimension(80,80));
                //panel1.add(t);
                BorderLayout grid = new BorderLayout(mapa.getFilas(),mapa.getColumnas());
                panel1.setLayout(grid);
                mapaButton m = new mapaButton(mapa.getFilas(), mapa.getColumnas(), mapa.getMatrix());
                for(int i = 0; i < m.getFiles(); i++){
                    for (int j = 0; j < m.getColumnes(); j++){
                        m.matrix[i][j].setBounds(30*j, 50*i, 80,70);
                        m.matrix[i][j].setVisible(true);
                        panel1.add(m.matrix[i][j]);
                    }
                }
                panel1.setVisible(true);
                panel1.revalidate();
                panel1.repaint();
                MapaPanel.revalidate();
                MapaPanel.repaint();
                System.out.println(panel1.getComponentCount());
            }
        });
        /*tablaHidato.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablaHidato.rowAtPoint(e.getPoint());
                int col = tablaHidato.columnAtPoint(e.getPoint());

                partida = new Partida(mapa, controladorUsuari.getUsuariActiu());
                partida.insertarNumero(row, col, restants.get(i++));

                String[] header = new String[mapa.getColumnas()];
                for(int i=0; i<header.length; ++i) header[i] = String.valueOf(i);
                JTable table = new JTable(partida.getMatrixMapa(), header);
                TableModel tm = table.getModel();
                tablaHidato.setModel(tm);
            }
        });*/
        menuPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showMain();
            }
        });

        MapaPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                activeUsertxt.setText(ControladorUsuari.getUsuariActiu());
            }
        });
        MapaPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
            }
        });
    }

    public JPanel getMapaPanel() {
        return MapaPanel;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here


        panel1 = new JPanel();

        panel1.setVisible(false);
        panel1.revalidate();
        panel1.repaint();
        //MapaPanel.add(panel1);
    }
}
