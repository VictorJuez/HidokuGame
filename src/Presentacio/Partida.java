package Presentacio;

import Domini.ControladorPartida;

import javax.swing.*;

public class Partida {

    private JPanel partidaPanel;
    private JLabel partidaLabel;
    private JButton button4;
    private JButton button5;
    private JButton buttonPause;
    private JButton pistaButton;
    private JButton salirGuardarButton;
    private JLabel numberLabel;
    private JPanel mapa;


    public JPanel getPartidaPanel()
    {
        return partidaPanel;
    }

    private void createUIComponents() {

                mapa = ControladorPartida.

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
}