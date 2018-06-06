package Presentacio;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class TriangleUpButton extends JButton {
    private Shape triangle = createTriangle();

    int fila;
    int columna;

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    public void paintBorder(Graphics g) {
        ((Graphics2D) g).draw(triangle);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        setForeground(Color.BLACK);
        if(getText().equals("*")){
            setBackground(Color.GRAY);
            g2.setColor(getBackground());
            g2.fill(triangle);
            g2.setPaint(getForeground());
            g2.draw(triangle);

        }
        else if(!getText().equals("?")) {
            setBackground(Color.ORANGE);
            int x = 30;
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
        p.addPoint(0, 50);
        p.addPoint(60, 50);
        p.addPoint(30, 0);
        return p;
    }
}