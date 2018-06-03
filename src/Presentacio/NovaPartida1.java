package Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NovaPartida1 {
    private JButton mapesExistentsButton;
    private JButton Enrere;
    private JButton mapaAleatoriButton;
    private JPanel NovaPartida1;
    private JLabel partidanova;


    public JPanel getNovaPartida1() {
        return NovaPartida1;
    }

    public NovaPartida1() {
        Enrere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        Enrere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showJugar();
            }
        });
        mapaAleatoriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showMapaView();
            }
        });
    }
}
