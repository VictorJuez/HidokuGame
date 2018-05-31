package Presentacio;

import java.awt.*;
import javax.swing.JButton;


public class TriangleDownButton extends JButton {
    private Shape triangle = createTriangle();

    public void paintBorder(Graphics g) {
        ((Graphics2D) g).draw(triangle);
    }

    public void paintComponent(Graphics g) {
        setForeground(Color.BLUE);
        int x = 30;
        System.out.println(x);
        g.drawString(getText(), x - getText().length() * 3, (50 - 1) / 2);
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 100);
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