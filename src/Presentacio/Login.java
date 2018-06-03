package Presentacio;

import Domini.ControladorUsuari;
import Domini.Usuari;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Login {
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel LoginPanel;
    private JButton enrereButton;
    private JComboBox userBox;
    private String username;

    private MapaView mapaView;
    private ControladorUsuari ControladorUsuari;

    public Login() {
        mapaView = new MapaView();

        setUpUserBox();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = String.valueOf(passwordField.getPassword());

                Usuari usuari = ControladorUsuari.getUsuari(username);
                if(usuari == null) JOptionPane.showMessageDialog(null, "El username no correspon a cap usuari!");
                else {
                    if(!ControladorUsuari.login(usuari, password)) JOptionPane.showMessageDialog(null, "Contrasenya incorrecte!");
                    else {
                        Main.showMenu();
                    }
                }
            }
        });


        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showMain();
            }
        });
        LoginPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                setUpUserBox();
            }
        });
        userBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = (String) userBox.getSelectedItem();
            }
        });
    }

    private void setUpUserBox() {
        ArrayList<String> usersID = new ArrayList<>(ControladorUsuari.getAllUsers().keySet());
        String[] prova = usersID.toArray(new String[0]);
        userBox.setModel(new javax.swing.DefaultComboBoxModel(prova));
        if(prova.length>0)userBox.setSelectedIndex(0);
    }

    public JPanel getLoginPanel() {
        return LoginPanel;
    }
}
