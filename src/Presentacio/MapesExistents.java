package Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapesExistents {
    private JLabel MapesExistentsLabel;
    private JComboBox MapesExistentsBox;
    private JPanel MapesExistentsPanel;
    private JButton EnrereButton;
    private JButton CrearPartidaButton;

    public MapesExistents()
    {

        EnrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showNovaPartida1();
            }
        });
    }

    public JPanel getMapesExistents() { return MapesExistentsPanel; }
}
