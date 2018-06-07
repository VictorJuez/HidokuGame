////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Presentacio;

import Dades.MapaDAO;
import Domini.ControladorMapa;
import Domini.ControladorPartida;
import Domini.ControladorUsuari;
import Domini.Mapa.Mapa;
import Domini.Partida;
import sun.plugin2.os.windows.SECURITY_ATTRIBUTES;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;

public class MapesExistents {
    private String mapaSeleccionatID;

    private JLabel MapesExistentsLabel;
    private JComboBox MapesExistentsBox;
    private JPanel MapesExistentsPanel;
    private JButton EnrereButton;
    private JButton CrearPartidaButton;

    public MapesExistents() {
        EnrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showNovaPartida1();
            }
        });

        MapesExistentsBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMapaSeleccionatID();
            }
        });

        CrearPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMapaSeleccionatID();
                Mapa m = ControladorMapa.getMapa(mapaSeleccionatID);
                System.out.println(mapaSeleccionatID);
                String IDPartida = ControladorPartida.crearPartida(m, ControladorUsuari.getUsuariActiu()).getID();
                ControladorPartida.seleccionarPartida(IDPartida);
                Main.showPartida();
            }
        });
        MapesExistentsPanel.addComponentListener(new ComponentAdapter() {   //fet per enric
            @Override
            public void componentShown(ComponentEvent e) {
                setUpMapesExistentsBox();
                super.componentShown(e);
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
        if(prova.length>0) MapesExistentsBox.setSelectedIndex(0);
    }

    private void setMapaSeleccionatID() { mapaSeleccionatID = ControladorMapa.getID(String.valueOf(MapesExistentsBox.getSelectedItem())) ; }
}
