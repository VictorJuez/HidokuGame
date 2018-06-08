////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Presentacio;

import Domini.ControladorUsuari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Menu {
    ControladorUsuari ControladorUsuari;

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
        CreadorMapasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showCreadorMapa();
            }
        });
        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorUsuari.logout();
                Main.showMain();
            }
        });
        JugarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showJugar();
            }
        });
        MenuPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                setUpUsuariLabel();
            }
        });
        RankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showRanking();
            }
        });
    }

    /**
     * Mostra pe'l label de usuari l'usuari actiu al sistema
     */
    public void setUpUsuariLabel() { this.usuariActiuLabel.setText(ControladorUsuari.getUsuariActiu()); }

    /**
     * Retorna aquest mateix menu
     * @return JPanel amb aquest mateix menu
     */
    public JPanel getMenuPanel() { return MenuPanel; }
}
