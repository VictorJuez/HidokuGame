////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Presentacio;

import Domini.ControladorPartida;
import Domini.ControladorUsuari;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;

public class CargarPartida {
    private ControladorPartida ControladorPartida;
    private String IDPartida;

    private JPanel CargarPartidaPanel;
    private JLabel cargarPartidaLabel;
    private JComboBox PartidesGuardadesBox;
    private JButton EnrereButton;
    private JButton CarregarButton;

    public CargarPartida() {
        CargarPartidaPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                try {
                    setUpPartidesGuardadesBox();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        EnrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showJugar();
            }
        });

        CarregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorPartida.seleccionarPartida(IDPartida);
                Main.showPartida();
            }
        });
        PartidesGuardadesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setIDPartida();
            }
        });
    }

    /**
     * Retorna aquest mateix menu
     * @return un JPanel amb aquest mateix menu
     */
    public JPanel getCargarPartidaPanel() { return CargarPartidaPanel; }

    /**
     * Prepara el Box de partides guardades amb les partides disponibles al disc
     * @throws IOException
     */
    private void setUpPartidesGuardadesBox() throws IOException {
        ArrayList<String> partidasUsuari = ControladorUsuari.getUsuari(ControladorUsuari.getUsuariActiu()).getPartidasID();
        String[] prova = partidasUsuari.toArray(new String[0]);
        PartidesGuardadesBox.setModel(new javax.swing.DefaultComboBoxModel(prova));
        if(prova.length>0)PartidesGuardadesBox.setSelectedIndex(0);
    }

    /**
     * Fixa el paràmetre ID de la partida
     */
    private void setIDPartida() {
        IDPartida = (String) PartidesGuardadesBox.getSelectedItem();
    }
}
