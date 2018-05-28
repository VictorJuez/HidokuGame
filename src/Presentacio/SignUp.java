package Presentacio;

import Domini.ControladorUsuari;
import Domini.Usuari;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp {
    private JButton button_msg;
    JPanel SignUpPanel;
    private JTextField userName;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton enrereButton;
    private static JFrame SignUpFrame;

    private ControladorUsuari controladorUsuari = new ControladorUsuari();

    public SignUp() {
        button_msg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userName.getText();
                String pss = String.valueOf(passwordField1.getPassword());
                String pss2 = String.valueOf(passwordField2.getPassword());
                if(!pss.equals(pss2))JOptionPane.showMessageDialog(null, "contrasenyes no coincideixen");
                else {
                    Usuari usuari  = controladorUsuari.insertarUsuari(username, pss);
                    JOptionPane.showMessageDialog(null, "Usuari creat!");
                }
            }
        });
    }

    public void createFrame(){
        SignUpFrame = new JFrame("SignUp");
        SignUpFrame.setContentPane(new SignUp().SignUpPanel);
        SignUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SignUpFrame.pack();
        SignUpFrame.setVisible(true);
    }

    public JPanel getSignUpPanel() {
        return SignUpPanel;
    }
}
