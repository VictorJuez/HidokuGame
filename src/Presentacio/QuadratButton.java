package Presentacio;

import java.awt.*;
import javax.swing.JButton;


public class QuadratButton extends JButton {
    private Shape quadrat = createQuadrat();

    int fila;
    int columna;

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    public void paintBorder(Graphics g) {
        ((Graphics2D) g).draw(quadrat);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        setForeground(Color.BLACK);
        if(getText().equals("*")){
            setBackground(Color.GRAY);
            g2.setColor(getBackground());
            g2.fill(quadrat);
            g2.setPaint(getForeground());
            g2.draw(quadrat);

        }
        else if(!getText().equals("?")) {
            setBackground(Color.ORANGE);
            int x = 30;
            g2.setColor(getBackground());
            g2.fill(quadrat);
            g2.setPaint(getForeground());
            g2.draw(quadrat);
            g.drawString(getText(), x - getText().length() * 3, (50 + 4) / 2);
        }
        else{
            setBackground(Color.WHITE);
            g2.setColor(getBackground());
            g2.fill(quadrat);
            g2.setPaint(getForeground());
            g2.draw(quadrat);
        }
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
