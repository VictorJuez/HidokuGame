package Presentacio;

import Domini.ControladorMapa;
import Domini.ControladorUsuari;
import Domini.ControladorPartida;
import Domini.Mapa.Mapa;
import Domini.Partida;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class MapaView {
    private JPanel MapaPanel;
    private JButton button1;
    private JPanel panel1;
    private JLabel random;
    private JButton guardaMapaButton;
    private JButton EnrereButton;
    // private JButton Jugar;
    private Mapa mapa;
    private int i = 0;
    private mapaButton m;


    public MapaView() {

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapa = ControladorMapa.generarHidato();

                panel1.removeAll();
                BorderLayout grid = new BorderLayout();
                panel1.setLayout(grid);
                mapaFactoryButton factory = new mapaFactoryButton();
                int n = mapa.getNumeros();
                int indexi = n;
                m = factory.getMapaButton(mapa.getColumnas(), mapa.getFilas(),mapa.getMatrix(), mapa.getTipo());
                System.out.println(mapa.getTipo());
                for(int i = 0; i < m.getFiles(); i++){
                    for (int j = 0; j < m.getColumnes(); j++){
                            panel1.add(m.matrix[i][j]);
                    }
                }
                panel1.add(m.matrix[0][0]);
                panel1.revalidate();
                panel1.repaint();
                MapaPanel.revalidate();
                MapaPanel.repaint();
            }
        });


        MapaPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
            }
        });
        EnrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showNovaPartida1();
            }
        });
        guardaMapaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Partida p = ControladorPartida.crearPartida(mapa,ControladorUsuari.getUsuariActiu());
                ControladorPartida.seleccionarPartida(p.getID());
                Main.showPartida();
            }
        });

    }



    public JPanel getMapaPanel() {
        return MapaPanel;
    }


}
