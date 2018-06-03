package Presentacio;

import Domini.ControladorMapa;
import Domini.ControladorPartida;
import Domini.ControladorUsuari;
import Domini.Mapa.Mapa;
import Domini.Mapa.MapaFactory;
import Domini.Partida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PartidaS {
    private JButton button4;
    private JLabel numberLabel;
    private JButton button5;
    private JLabel partidaLabel;
    private JButton buttonPause;
    private JButton pistaButton;
    private JButton salirGuardarButton;
    private JPanel mapa;
    private JPanel PanelPartida;

    public PartidaS() {
        PanelPartida.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                System.out.println("holaaa");
                mapa = new JPanel();
                mapa.removeAll();
                BorderLayout grid = new BorderLayout();
                mapa.setLayout(grid);
                mapaFactoryButton factory = new mapaFactoryButton();

                String s = ControladorUsuari.getUsuariActiu();
                String pcurso = ControladorPartida.getPartidaEnCurso();
                Partida p = ControladorPartida.getPartida(pcurso);
                //ControladorPartida.
                mapaButton m = factory.getMapaButton(p.getMapaPartida().getColumnas(),  p.getMapaPartida().getFilas(),  p.getMapaPartida().getMatrix(),  p.getMapaPartida().getTipo());
                int n =  p.getMapaPartida().getNumeros();
                int indexn = 0;
                int indexi = n;
                int interrogant =  p.getMapaPartida().getInterrogants();
                System.out.println( p.getMapaPartida().getTipo());
                for (int i = 0; i < m.getFiles(); i++) {
                    for (int j = 0; j < m.getColumnes(); j++) {
                        mapa.add(m.matrix[i][j]);
                    }
                }
                //aixo es per poder pintar per sobre els que tenen numeros per defecte
                for (int i = 0; i < m.getFiles(); i++) {
                    for (int j = 0; j < m.getColumnes(); j++) {
                        if (!m.matrix[i][j].getText().equals("?") && !m.matrix[i][j].getText().equals("#") && !m.matrix[i][j].getText().equals("*")) {
                            mapa.add(m.matrix[i][j], indexn);
                            indexn++;
                        } else {
                            mapa.add(m.matrix[i][j], indexi);
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
                mapa.add(m.matrix[0][0]);
                mapa.setVisible(true);
                mapa.revalidate();
                mapa.repaint();
                PanelPartida.setVisible(true);
                PanelPartida.revalidate();
                PanelPartida.repaint();
                System.out.println(mapa.getComponentCount());
                super.componentShown(e);
            }
        });
    }

    public JPanel getPanelPartida() {
        return PanelPartida;
    }

}


