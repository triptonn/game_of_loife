package interfaces;

import java.awt.Color;
import java.awt.Graphics2D;

import data.Vec;

public interface SceneObject {
    void update();

    void render(Graphics2D g2d);

    Vec getLocation();

    String getName();

    void setName(String name);

    boolean isVisible();

    void setVisible(boolean visible);

    void setColor(Color color);

    Color getColor();
}
