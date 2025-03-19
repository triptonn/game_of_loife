package interfaces;

import java.awt.Color;
import java.awt.Graphics2D;

public interface Renderable {
    void render(Graphics2D g2d);

    boolean isVisible();

    void setVisible(boolean visible);

    Color getColor();

    void setColor(Color color);
}
