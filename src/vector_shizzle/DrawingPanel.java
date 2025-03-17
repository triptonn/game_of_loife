package vector_shizzle;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
    private Vec loc;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillOval((int) this.loc.data[0] + 15, (int) this.loc.data[1] + 15, 30, 30);
    }

    public void updateLoc(Vec loc) {
        this.loc = loc;
    }
}
