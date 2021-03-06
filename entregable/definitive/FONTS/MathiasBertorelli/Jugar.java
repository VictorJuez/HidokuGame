////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Jugar {
    private JButton novaPartidaButton;
    private JButton carregarPartidaButton;
    private JButton enrereButton;
    private JPanel JugarPanel;
    private JLabel jugarLabel;

    public JPanel getJugarPanel() {
        return JugarPanel;
    }

    public Jugar() {

        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showMenu();
            }
        });


        novaPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showNovaPartida1();
            }
        });
        carregarPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showCargarPartida();
            }
        });
    }

}
