package Presentacio;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Matrix {
    private JPanel panelMatrix;
    TriangleDownButton buttonTriangleDownButton;

    public Matrix() {
       // button1.setForeground(Color.blue);
        //button1.pol
        buttonTriangleDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonTriangleDownButton.getText().equals("2")){
                    buttonTriangleDownButton.setText("100");
                }
                else {
                    buttonTriangleDownButton.setText("2");
                }

                //buttonTriangleDownButton.repaint(0,0,buttonTriangleDownButton.getWidth(),buttonTriangleDownButton.getHeight());
                panelMatrix.repaint();
                System.out.println("ei");
            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Matrix");
        frame.setContentPane(new Matrix().panelMatrix);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        buttonTriangleDownButton = new TriangleDownButton();
    }
}
