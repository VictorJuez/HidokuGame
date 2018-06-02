package Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorMapa {
    private JPanel editorMapaPanel;
    private JLabel CrearMapaLabel;
    private JLabel astLabel;
    private JLabel hashLabel;
    private JLabel intLabel;
    private JButton enrereButton;
    private JButton següentButton;

    public EditorMapa() {

        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.showCreadorMapa();
            }
        });
        següentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getEditorMapa() {
        return editorMapaPanel;
    }
}