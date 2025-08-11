package gaussian_bars;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
    private int[] bars = new int[10];
    private static int WIDTH;
    private static int HEIGHT;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 12);

        g.setColor(Color.red);
        g.drawLine(0, (HEIGHT / 3) - 40, WIDTH, (HEIGHT / 3) - 40);

        g.setColor(Color.orange);
        g.setFont(font);
        boolean runFinished = false;
        int counter = 0;
        for (int bar : this.bars) {
            int xPos = counter * (WIDTH / 10);
            int yPos = HEIGHT - bar - 40;
            int width = WIDTH / 10;
            int height = bar;
            String barString = "" + bar;

			g.drawRect(xPos, yPos, width, height);
            if (bar > 19) {
                int numYPos = yPos + (bar / 2) + 6;
                int numXPos = xPos + (width / 2) - (barString.length() * 4);
                g.drawString(barString, numXPos, numYPos);
            }

            if (bar > 479) {
                runFinished = true;
            }
            counter++;
        }

        if (runFinished) {
            GaussianBars.stopGameLoop();
        }
    }

    public void setBars(int[] newBars) {
        this.bars = newBars;
    }

    public static void setDim(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
    }
}
