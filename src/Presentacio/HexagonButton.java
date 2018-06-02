package Presentacio;

import javax.swing.*;
import java.awt.*;

public class HexagonButton extends JButton {
    private Shape hexagon = createHexagon();


    public void paintBorder(Graphics g) {
        ((Graphics2D) g).draw(hexagon);
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
        return hexagon.contains(x, y);
    }

    private Shape createHexagon() {
        Polygon p = new Polygon();
        p.addPoint(30, 0);
        p.addPoint(60, 15);
        p.addPoint(60, 45);
        p.addPoint(30,60);
        p.addPoint(0, 45);
        p.addPoint(0,15);
        return p;
    }
}
