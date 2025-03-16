package random_walker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
    private Point pos;
    private static ArrayList<Point> history = new ArrayList<Point>();

    public DrawingPanel(Point pos) {
        setBackground(Color.black);
        this.pos = pos;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);
        for (Point p : history) {
            System.out.println(p.x + ", " + p.y);
            g.fillOval(p.x, p.y, 10, 10);
        }

        g.setColor(Color.red);
        g.fillOval(this.pos.x, this.pos.y, 10, 10);
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public static void setHistory(ArrayList<Point> list) {
        history = list;
    }
}
