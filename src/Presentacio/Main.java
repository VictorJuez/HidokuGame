package Presentacio;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class Main {
    public static JFrame MainFrame;
    private JButton signUpButton;
    JPanel MainPanel;
    private JButton loginButton;
    final static String INICIO = "inicio";
    final static String SIGNUP = "sign up";
    private SignUp signUp;
    private Login Login;
    public Main() {
        signUp = new SignUp();
        Login = new Login();

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.dispose();
                signUp.createFrame();
            }
        });
        signUpButton.addComponentListener(new ComponentAdapter() {
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.dispose();
                Login.createFrame();
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        MainFrame = frame;
        frame.setContentPane(new Main().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
