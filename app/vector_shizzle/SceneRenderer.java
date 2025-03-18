package vector_shizzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class SceneRenderer {

    public void render(Graphics2D g2d, SceneModel model) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        model.render(g2d);
    }

    private int getHeight() {
        return VectorVisualizer.WINDOW_HEIGHT;
    }

    private int getWidth() {
        return VectorVisualizer.WINDOW_WIDTH;
    }
}
