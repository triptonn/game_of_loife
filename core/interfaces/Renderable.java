package interfaces;

import java.awt.Color;
import java.awt.Graphics2D;

public interface Renderable {
    void render(Graphics2D g2d);

    double getAngle();

    void setAngle(double angle);

    boolean isVisible();

    void setVisible(boolean visible);

    Color getColor();

    void setColor(Color color);
}
