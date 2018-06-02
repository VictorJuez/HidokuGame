package Presentacio;

import java.awt.*;
import javax.swing.JButton;


public class QuadratButton extends JButton {
    private Shape quadrat = createQuadrat();

    public void paintBorder(Graphics g) {
        ((Graphics2D) g).draw(quadrat);
    }

    public void paintComponent(Graphics g) {
        setForeground(Color.BLUE);
        int x = 30;
        g.drawString(getText(), x - getText().length() * 3, (60 + 4) / 2);
    }

    public Dimension getPreferredSize() {
        return new Dimension(65, 65);
    }

    public boolean contains(int x, int y) {
        return quadrat.contains(x, y);
    }

    private Shape createQuadrat() {
        Polygon p = new Polygon();
        p.addPoint(0, 0);
        p.addPoint(60, 0);
        p.addPoint(60, 60);
        p.addPoint(0,60);
        return p;
    }
}
