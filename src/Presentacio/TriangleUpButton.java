package Presentacio;

import java.awt.*;
import javax.swing.JButton;


public class TriangleUpButton extends JButton {
    private Shape triangle = createTriangle();

    public void paintBorder(Graphics g) {
        ((Graphics2D) g).draw(triangle);
    }

    public void paintComponent(Graphics g) {
        setForeground(Color.BLUE);
        int x = 30;
        g.drawString(getText(), x - getText().length() * 3, (50 - 2) / 2);
    }

    public Dimension getPreferredSize() {
        return new Dimension(60, 50);
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