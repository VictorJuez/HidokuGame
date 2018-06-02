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
    private JPanel MapaPanel;
    private JButton button1;
    private JPanel panel1;
    private ControladorUsuari controladorUsuari;
    private Mapa mapa;
    private Partida partida;
    private int i = 0;
    private Vector<Integer> restants;


    public MapaView() {

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //button1.setVisible(false);
                mapa = ControladorMapa.generarHidato();
                //TriangleUpButton t = new TriangleUpButton();
                //t.setText("1");
                //t.setVisible(true);
                //t.setPreferredSize(new Dimension(80,80));
                //panel1.add(t);
                panel1.removeAll();
                BorderLayout grid = new BorderLayout(/*mapa.getFilas()*50,mapa.getColumnas()*60*/);
                panel1.setLayout(grid);
                mapaFactoryButton factory = new mapaFactoryButton();
                mapaButton m = factory.getMapaButton(mapa.getColumnas(), mapa.getFilas(),mapa.getMatrix(), mapa.getTipo());
                System.out.println(mapa.getTipo());
                for(int i = 0; i < m.getFiles(); i++){
                    for (int j = 0; j < m.getColumnes(); j++){
                        panel1.add(m.matrix[i][j]);
                        /*m.matrix[i][j].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JButton myButton = (JButton)e.getSource();
                                String s = myButton.getName();
                                System.out.println(s);
                                //listenerMatrix(i,j);
                            }
                        });*/
                    }
                }
                panel1.add(m.matrix[0][0]);

                panel1.setVisible(true);
                panel1.revalidate();
                panel1.repaint();
                MapaPanel.revalidate();
                MapaPanel.repaint();
                System.out.println(panel1.getComponentCount());
            }
        });


        MapaPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
            }
        });
    }

    public void listenerMatrix(int  i, int j){

    }


    public JPanel getMapaPanel() {
        return MapaPanel;
    }


}
