package Presentacio;

import Domini.ControladorMapa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MapesExistents {
    private JLabel MapesExistentsLabel;
    private JComboBox MapesExistentsBox;
    private JPanel MapesExistentsPanel;
    private JButton EnrereButton;
    private JButton CrearPartidaButton;

    public MapesExistents() {
        setUpMapesExistentsBox();
        EnrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showNovaPartida1();
            }
        });
    }

    public JPanel getMapesExistents() { return MapesExistentsPanel; }

    private void setUpMapesExistentsBox() {
        ArrayList<String> usersID = null;
        try {
            usersID = new ArrayList<>(ControladorMapa.getAllSavedMaps());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] prova = usersID.toArray(new String[0]);
        MapesExistentsBox.setModel(new javax.swing.DefaultComboBoxModel(prova));
        if(prova.length>0)MapesExistentsBox.setSelectedIndex(0);
    }
}
