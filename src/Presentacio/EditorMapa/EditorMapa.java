package Presentacio.EditorMapa;

import Presentacio.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorMapa {
    private JPanel ButtonGridPanel;
    private JPanel editorMapaPanel;
    private JLabel CrearMapaLabel;
    private JLabel astLabel;
    private JLabel hashLabel;
    private JLabel intLabel;
    private JButton enrereButton;
    private JButton següentButton;

    public EditorMapa() {
        BorderLayout grid = new BorderLayout();
        ButtonGridPanel.removeAll();

        ButtonGridPanel.setLayout(grid);
        GridEditorFactory fact = new GridEditorFactory();
        //GridEditor gE = fact.getGridEditor();

        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showCreadorMapa();
            }
        });
        següentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { }
        });
    }

    public JPanel getEditorMapa() {
        return editorMapaPanel;
    }
}