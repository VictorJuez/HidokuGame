package Presentacio;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Main {
    private SignUp signUp;
    private Login Login;

    JFrame frame = new JFrame("Main demo");
    JPanel panelCont = new JPanel();
    JPanel MainPanel;
    JPanel singUpPanel;
    JButton signUpButton;
    JButton loginButton;
    CardLayout cl = new CardLayout();


    public Main() {
        //Trying cardLayout
        panelCont.setLayout(cl);
        signUp = new SignUp();
        singUpPanel = signUp.getSignUpPanel();
        panelCont.add(MainPanel,"main");
        panelCont.add(singUpPanel, "singUp");
        cl.show(panelCont, "main");

        //Trying cardLayout

        signUp = new SignUp();
        Login = new Login();
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "singUp");
            }
        });
        signUpButton.addComponentListener(new ComponentAdapter() {
        });

        frame.add(panelCont);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
