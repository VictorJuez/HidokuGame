package Presentacio;

import Domini.ControladorPartida;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;

public class CargarPartida {
    private ControladorPartida ControladorPartida;

    private JPanel CargarPartidaPanel;
    private JLabel cargarPartidaLabel;
    private JComboBox PartidesGuardadesBox;
    private JButton EnrereButton;

    public CargarPartida() {
        EnrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showJugar();
            }
        });
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
    }

    public JPanel getCargarPartidaPanel() { return CargarPartidaPanel; }
    //VV LLAMAR EN EL SHOW
    private void setUpPartidesGuardadesBox() throws IOException {
        ArrayList<String> usersID = new ArrayList<>(ControladorPartida.getAllPartidas().keySet());
        String[] prova = usersID.toArray(new String[0]);
        PartidesGuardadesBox.setModel(new javax.swing.DefaultComboBoxModel(prova));
        if(prova.length>0)PartidesGuardadesBox.setSelectedIndex(0);
    }
}
