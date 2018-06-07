package Presentacio;

import Domini.ControladorMapa;
import Domini.ControladorPartida;
import Domini.ControladorUsuari;
import Domini.Mapa.Mapa;
import Domini.Mapa.MapaDecorator;
import Domini.Mapa.MapaFactory;
import Domini.Mapa.UtilsMapaDecorator;
import Domini.Partida;
import com.sun.prism.Graphics;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Vector;

public class PartidaS {
    private JLabel partidaLabel;
    private JButton buttonPause;
    private JButton pistaButton;
    private JButton salirGuardarButton;
    private JPanel mapa;
    private JPanel PanelPartida;
    private JButton button4;
    private JLabel numberLabel;
    private JButton button5;
    private JButton comprovar;
    private JLabel topologiaLabel;
    private Partida p;
    int index = 0;

    private Vector<Integer> r = new Vector<>();

    private void calculoFaltan (Vector<Integer> e){
        r.removeAllElements();
        int inicio = 1;
        for (int i = 0; i < e.size(); i++){
            while(inicio != e.get(i)){
                r.add(inicio);
                inicio++;
            }
            inicio++;
        }
        System.out.println(r);
    }

    public PartidaS() {
        PanelPartida.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                mapa.removeAll();
                BorderLayout grid = new BorderLayout();
                mapa.setLayout(grid);
                mapaFactoryButton factory = new mapaFactoryButton();
                String s = ControladorUsuari.getUsuariActiu();
                String pcurso = ControladorPartida.getPartidaEnCurso();
                p = ControladorPartida.getPartida(pcurso);
                calculoFaltan(p.getNumInicio());

                if (r.size() > 0) numberLabel.setText(String.valueOf(r.get(0)));
                else numberLabel.setText("0");
                numberLabel.repaint();
                //ControladorPartida.
                mapaButton m = factory.getMapaButton(p.getMapaPartida().getColumnas(), p.getMapaPartida().getFilas(), p.getMapaPartida().getMatrix(), p.getMapaPartida().getTipo(),p.getNumInicio());
                int n = p.getMapaPartida().getNumeros();
                for (int i = 0; i < m.getFiles(); i++) {
                    for (int j = 0; j < m.getColumnes(); j++) {
                        mapa.add(m.matrix[i][j]);
                    }
                }
                //aixo es per poder pintar per sobre els que tenen numeros per defecte
                for (int i = 0; i < m.getFiles(); i++) {
                    for (int j = 0; j < m.getColumnes(); j++) {
                        mapa.add(m.matrix[i][j]);
                        m.matrix[i][j].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JButton myButton = (JButton) e.getSource();
                                String s = myButton.getName();
                                String parts[] = s.split(",");
                                int fila = Integer.valueOf(parts[0]);
                                int columna = Integer.valueOf(parts[1]);
                                String v = myButton.getText();
                                int v1 = Integer.valueOf(numberLabel.getText());
                                if (v.equals("?")) {
                                    if (ControladorPartida.insertarNumero(fila, columna, v1)) {
                                        myButton.setText(numberLabel.getText());
                                        int x = Integer.valueOf(numberLabel.getText());

                                        if (index < r.size() - 1) {
                                            index++;
                                            numberLabel.setText(String.valueOf(r.get(index)));
                                        }
                                    }
                                } else {
                                    if (ControladorPartida.borrarNumero(fila, columna)) {
                                        myButton.setText("?");
                                        if (index > 0) {
                                            index--;
                                            numberLabel.setText(String.valueOf(r.get(index)));
                                        }

                                    }
                                }

                            }
                        });
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
                //super.componentShown(e);

                //infoLabel
                topologiaLabel.setText("Topologia: " + p.getTipoMapa() + "," + p.getAngulosMapa());
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (index > 0) {
                    index--;
                    numberLabel.setText(String.valueOf(r.get(index)));
                }

            }
        });
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < r.size() - 1) {
                    index++;
                    numberLabel.setText(String.valueOf(r.get(index)));
                }
            }
        });
        comprovar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane finalizar = new JOptionPane();
                if (p.getCantidadInterrogantes() == 0) {
                    UtilsMapaDecorator utilsMapa = new UtilsMapaDecorator(p.getMapaPartida());
                    if (utilsMapa.hidatoValido()) {
                        String difiControladorUsuariltad = "FACIL"; //para el testeo, de mientras lo dejo así

                        int puntuacion = ControladorPartida.calculoPuntuacion(difiControladorUsuariltad, p.getReloj(), p.getPistasConsultadas());
                        //commit de la puntuacion en resultado
                        String userID = ControladorUsuari.getUsuariActiu();
                        boolean b = ControladorUsuari.insertarResultat(ControladorUsuari.getUsuari(userID), puntuacion);
                        if (b) finalizar.showMessageDialog(null, "Nou record personal!\n" +
                                "Puntuacio: " + puntuacion + "\n" +
                                "Temps: " + p.getReloj());
                        else finalizar.showMessageDialog(null,
                                "Puntuacio: " + puntuacion + "\n" +
                                        "Temps: " + p.getReloj());
                        p.setPuntuacion(puntuacion);
                        Main.showRanking();
                    } else {
                        finalizar.showMessageDialog(null, "La solucio es incorrecte! torna-ho a provar");
                    }

                }
                else {
                    finalizar.showMessageDialog(null, "Encara falten caselles per omplir!");
                }

            }
        });


        pistaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer i[];
                i = ControladorPartida.consultarPista();
                if(i[0] != -1){
                    int x = p.getMapaPartida().getColumnas();
                    int y = p.getMapaPartida().getFilas();
                    JButton b = (JButton) mapa.getComponent(i[0]*y+i[1]);
                    b.setForeground(Color.GREEN);
                    b.repaint();
                }
                System.out.println(i[0]+" , "+ i[1]);
            }
        });


        salirGuardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] buttons = { "Si", "No"};
                int returnValue = JOptionPane.showOptionDialog(null, "Vols guardar la partida?", "Guardar i sortir",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
                System.out.println(returnValue);

                if(returnValue == 0){
                    ControladorPartida.savePartida(p);
                }
                Main.showMenu();

            }
        });
    }

    public JPanel getPanelPartida() {
        return PanelPartida;
    }

}


