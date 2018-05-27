package Presentacio;

import javax.swing.*;

public class MapaView {
    private JButton generarMapaRandomButton;
    private JTable tablaHidato;
    private JButton sortirButton;
    private JPanel MapaPanel;
    private static JFrame MapaFrame;


    public void createFrame(){
        MapaFrame = new JFrame("Login");
        MapaFrame.setContentPane(new MapaView().MapaPanel);
        MapaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MapaFrame.pack();
        MapaFrame.setVisible(true);
    }
}
