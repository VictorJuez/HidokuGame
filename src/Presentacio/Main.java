package Presentacio;


import Presentacio.EditorMapa.EditorMapa;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Main {
    private SignUp signUp;
    private Login Login;
    private MapaView mapaView;
    private CreadorMapa creadorMapa;
    private Menu menu;
    private EditorMapa editorMapa;
    private Partida partida;
    private Jugar jugar;
    private NovaPartida1 novaPartida1;
    private CargarPartida cargarPartida;

    JFrame frame = new JFrame("Main demo");
    static JPanel panelCont = new JPanel();
    JPanel MainPanel;
    JPanel singUpPanel;
    JPanel loginPanel;
    JPanel mapaViewPanel;
    JPanel creadorMapaPanel;
    JPanel menuPanel;
    JPanel editorMapaPanel;
    JPanel partidaPanel;
    JPanel cargarPartidaPanel;
    JPanel novapartida1;    //es la ventana donde escojes si quieres un mapa existente o un mapa aleatorio
    JPanel jugarPanel;

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
        editorMapa = new EditorMapa();
        partida = new Partida();
        jugar = new Jugar();
        novaPartida1 = new NovaPartida1();
        partida = new Partida();
        cargarPartida = new CargarPartida();

        partidaPanel = partida.getPartidaPanel();
        novapartida1 = novaPartida1.getNovaPartida1();
        jugarPanel = jugar.getJugarPanel();     //ja tens el jpanel
        singUpPanel = signUp.getSignUpPanel();
        loginPanel = Login.getLoginPanel();
        mapaViewPanel = mapaView.getMapaPanel();
        creadorMapaPanel = creadorMapa.getCreadorMapasPanel();
        menuPanel = menu.getMenuPanel();
        editorMapaPanel = editorMapa.getEditorMapa();
        partidaPanel = partida.getPartidaPanel();
        cargarPartidaPanel = cargarPartida.getCargarPartidaPanel();

        panelCont.add(partidaPanel,"Partida");
        panelCont.add(novapartida1,"Nova Partida");
        panelCont.add(jugarPanel, "Jugar");
        panelCont.add(MainPanel,"main");
        panelCont.add(singUpPanel, "singUp");
        panelCont.add(loginPanel, "login");
        panelCont.add(mapaViewPanel, "mapaView");
        panelCont.add(creadorMapaPanel, "creadorMapa");
        panelCont.add(menuPanel, "menu");
        panelCont.add(editorMapaPanel, "editorMapa");
        panelCont.add(partidaPanel,"Partida");

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
                showMapaView();
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

    public static void showNovaPartida1(){cl.show(panelCont, "Nova Partida");}

    public static void showJugar(){cl.show(panelCont, "Jugar");}

    public static void showCreadorMapa() { cl.show(panelCont, "creadorMapa"); }

    public static void showMenu() { cl.show(panelCont, "menu"); }

    public static void showPartida(){ cl.show(panelCont, "Partida");}

    public static void showEditorMapa() { cl.show(panelCont, "editorMapa"); }

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
