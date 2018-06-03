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
    private JButton Jugar;
    private JButton Anterior;
    private JButton Siguiente;
    private ControladorUsuari controladorUsuari;
    private Mapa mapa;
    private Partida partida;
    private int i = 0;
    private Vector<Integer> restants;
    private mapaButton m;
    private JLabel numero;


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
                int interrogant = mapa.getInterrogants();
                int indexn = 0;
                int indexi = n;
                m = factory.getMapaButton(mapa.getColumnas(), mapa.getFilas(),mapa.getMatrix(), mapa.getTipo());
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
                //aixo es per poder pintar per sobre els que tenen numeros per defecte
                for(int i = 0; i < m.getFiles(); i++){
                    for (int j = 0; j < m.getColumnes(); j++){
                        if (!m.matrix[i][j].getText().equals("?") && !m.matrix[i][j].getText().equals("#") && !m.matrix[i][j].getText().equals("*")){
                            panel1.add(m.matrix[i][j], indexn);
                            indexn++;
                        }
                        else{
                            panel1.add(m.matrix[i][j], indexi);
                            indexi++;
                        }
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
        Jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1.setVisible(false);
                for(int i = 0; i < m.getFiles(); i++){
                    for (int j = 0; j < m.getColumnes(); j++){
                        m.matrix[i][j].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JButton myButton = (JButton)e.getSource();
                                String s = myButton.getName();
                                System.out.println(s);
                            }
                        });
                    }
                }
            }
        });
        Anterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if (numero > 1) numero--;
            }
        });

        Siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  if (numero < )
            }
        });
    }

    public void listenerMatrix(int  i, int j){

    }


    public JPanel getMapaPanel() {
        return MapaPanel;
    }


}
