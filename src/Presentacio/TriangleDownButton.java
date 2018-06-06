package Presentacio;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class TriangleDownButton extends JButton {
    private Shape triangle = createTriangle();
    int fila;
    int columna;
    private boolean modificable = true;

    public boolean isModificable(){return modificable;}

    public void setModificable(boolean b){this.modificable = b;}


    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void paintBorder(Graphics g) {

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        setForeground(Color.BLACK);
        int x = 30;
        if(getText().equals("*")){
            setBackground(Color.GRAY);
            g2.setColor(getBackground());
            g2.fill(triangle);
            g2.setPaint(getForeground());
            g2.draw(triangle);

        }
        else if(!getText().equals("?") && !modificable) {
            setBackground(Color.ORANGE);
            g2.setColor(getBackground());
            g2.fill(triangle);
            g2.setPaint(getForeground());
            g2.draw(triangle);
            g.drawString(getText(), x - getText().length() * 3, (50 + 4) / 2);
        }
        else{
            setBackground(Color.WHITE);
            g2.setColor(getBackground());
            g2.fill(triangle);
            g2.setPaint(getForeground());
            g2.draw(triangle);
            if (!getText().equals("?")) g.drawString(getText(), x - getText().length() * 3, (50 + 4) / 2);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(65, 55);
    }

    public boolean contains(int x, int y) {
        return triangle.contains(x, y);
    }

    private Shape createTriangle() {
        Polygon p = new Polygon();
        p.addPoint(0, 0);
        p.addPoint(60, 0);
        p.addPoint(30, 50);
        return p;
    }
}