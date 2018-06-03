package Presentacio;

import Domini.ControladorUsuari;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Ranking {
    private JPanel rankingPanel;
    private JTable TablaRanking;
    private JLabel actualRecordLabel;

    public Ranking() {
        rankingPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {

            }
        });

        ArrayList<Pair<String, Integer>>al =ControladorUsuari.getGlobalRanking();

        String[] columnNames = {"Usuario","Puntuacion"};
        Object[][] data = new Object[al.size()][2];

        for(int i=0; i<data.length; ++i){
            data[i][0] = al.get(i).getKey();
            data[i][1] = al.get(i).getValue();
        }

        TableModel tableModel = new DefaultTableModel(data, columnNames);
        TablaRanking.setModel(tableModel);

        actualRecordLabel.setText(ControladorUsuari.getActualRecord().getID()+ ": " + String.valueOf(ControladorUsuari.getActualRecord().getRecord()));

        for(Pair<String, Integer> p : al){
            System.out.println(p.getKey()+ ", "+ p.getValue());
        }
    }

    public JPanel getRankingPanel() {
        return rankingPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ranking");
        frame.setContentPane(new Ranking().rankingPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
