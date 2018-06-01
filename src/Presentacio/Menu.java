package Presentacio;

import Domini.ControladorUsuari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    ControladorUsuari cU;

    private CreadorMapa cM;

    private JPanel MenuPanel;
    private JButton RankingButton;
    private JButton CreadorMapasButton;
    private JButton JugarButton;
    private JButton LogOutButton;
    private JLabel usuariLabel;
    private JLabel usuariActiuLabel;

    JFrame frame = new JFrame("Menu principal");
    static JPanel panelCont = new JPanel();

    JButton jugarButton;
    JButton rankingButton;
    JButton creadorMapasButton;
    static CardLayout cl = new CardLayout();


    public Menu ()
    {
        cU = new ControladorUsuari();
        setUpUsuariLabel(); //sale nobody porque hay que hacer el refresh después del login
        CreadorMapasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showCreadorMapa();
            }
        });
        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cU.logout();
                Main.showMain();
            }
        });
    }

    public void setUpUsuariLabel() { this.usuariActiuLabel.setText(cU.getUsuariActiu()); }

    public JPanel getMenuPanel() { return MenuPanel; }
}
