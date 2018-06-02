package Presentacio;

import sun.rmi.runtime.Log;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Main {
    private SignUp signUp;
    private Login Login;
    private MapaView mapaView;
    private CreadorMapa creadorMapa;
    private Menu menu;

    JFrame frame = new JFrame("Main demo");
    static JPanel panelCont = new JPanel();
    JPanel MainPanel;
    JPanel singUpPanel;
    JPanel loginPanel;
    JPanel mapaViewPanel;
    JPanel creadorMapaPanel;
    JPanel menuPanel;
    JButton loginButton;
    JButton signUpButton;
    private JButton sortirButton;
    private JButton button1;
    static CardLayout cl = new CardLayout();


    public Main() {
        //Trying cardLayout
        panelCont.setLayout(cl);
        signUp = new SignUp();
        Login = new Login();
        mapaView = new MapaView();
        creadorMapa = new CreadorMapa();
        menu = new Menu();

        singUpPanel = signUp.getSignUpPanel();
        loginPanel = Login.getLoginPanel();
        mapaViewPanel = mapaView.getMapaPanel();
        creadorMapaPanel = creadorMapa.getCreadorMapasPanel();
        menuPanel = menu.getMenuPanel();

        panelCont.add(MainPanel,"main");
        panelCont.add(singUpPanel, "singUp");
        panelCont.add(loginPanel, "login");
        panelCont.add(mapaViewPanel, "mapaView");
        panelCont.add(creadorMapaPanel, "creadorMapa");
        panelCont.add(menuPanel, "menu");

        cl.show(panelCont, "main");

        //Trying cardLayout
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "singUp");
            }
        });
        signUpButton.addComponentListener(new ComponentAdapter() {
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "login");
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "mapaView");
            }
        });

        frame.add(panelCont);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500,375));
        //frame.pack();
        frame.setVisible(true);
        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Close();
            }
        });

    }

    public static void showMain(){
        cl.show(panelCont, "main");
    }

    public static void showCreadorMapa() { cl.show(panelCont, "creadorMapa"); }

    public static void showMenu() { cl.show(panelCont, "menu"); }

    public static void showMapaView(){
        cl.show(panelCont, "mapaView");
    }

    //he hecho ésto por si queremos meter un botón salir
    public static void Close() { System.exit(0); }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
