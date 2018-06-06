package Presentacio;

import javax.swing.*;
import java.awt.*;

public class HexagonButton extends JButton {
    private Shape hexagon = createHexagon();
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
        ((Graphics2D) g).draw(hexagon);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        setForeground(Color.BLACK);
        int x = 30;
        if(getText().equals("*")){
            setBackground(Color.GRAY);
            g2.setColor(getBackground());
            g2.fill(hexagon);
            g2.setPaint(getForeground());
            g2.draw(hexagon);

        }
        else if(!getText().equals("?") && !modificable) {
            setBackground(Color.ORANGE);
            g2.setColor(getBackground());
            g2.fill(hexagon);
            g2.setPaint(getForeground());
            g2.draw(hexagon);
            g.drawString(getText(), x - getText().length() * 3, (50 + 4) / 2);
        }
        else{
            setBackground(Color.WHITE);
            g2.setColor(getBackground());
            g2.fill(hexagon);
            g2.setPaint(getForeground());
            g2.draw(hexagon);
            if (!getText().equals("?")) g.drawString(getText(), x - getText().length() * 3, (50 + 4) / 2);
        }
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
