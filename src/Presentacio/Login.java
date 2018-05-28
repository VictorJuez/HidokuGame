package Presentacio;

import Domini.ControladorUsuari;
import Domini.Usuari;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JTextField textUsername;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel LoginPanel;
    private JButton enrereButton;
    private static JFrame LoginFrame;

    private MapaView mapaView;
    private ControladorUsuari controladorUsuari;

    public Login() {
        controladorUsuari = new ControladorUsuari();
        mapaView = new MapaView();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textUsername.getText();
                String password = String.valueOf(passwordField.getPassword());

                Usuari usuari = controladorUsuari.getUsuari(username);
                if(usuari == null) JOptionPane.showMessageDialog(null, "El username no correspon a cap usuari!");
                else {
                    if(!controladorUsuari.login(usuari, password)) JOptionPane.showMessageDialog(null, "Contrasenya incorrecte!");
                    else {
                        JOptionPane.showMessageDialog(null, "Login correctament!");
                        Main.showMapaView();
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
    }

    public JPanel getLoginPanel() {
        return LoginPanel;
    }
}
