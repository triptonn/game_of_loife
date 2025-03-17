package vector_shizzle;

import java.awt.Color;
import java.awt.Graphics2D;

public interface SceneObject {
    void update();

    void render(Graphics2D g2d);

    Vec getLocation();

    boolean isVisible();

    void setVisible(boolean visible);

    void setColor(Color color);

    public Color getColor();
}
