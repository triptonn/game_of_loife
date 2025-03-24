package gravitational;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import data.Vec;

public class SceneRenderer {
    private Dimension scene;
    private static final int ARROW_SIZE = 10;

    public SceneRenderer(Dimension scene) {
        this.scene = scene;
    }

    public void render(Graphics2D g2d, SceneModel model) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, (int) this.scene.getWidth(), (int) this.scene.getHeight());
        model.render(g2d);
    }

    public void drawVec(Graphics2D g2d, Vec origin, Vec vector, Color color) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2));

        Vec end = origin.plus(vector);

        g2d.drawLine((int) origin.x(), (int) origin.y(),
                (int) end.x(), (int) end.y());

        drawArrowHead(g2d, origin, end);
    }

    private void drawArrowHead(Graphics2D g2d, Vec start, Vec end) {
        Vec direction = end.minus(start);
        Vec normalized = direction.norm();

        Vec perp = new Vec(-normalized.y(), normalized.x());

        Vec arrow1 = end.minus(normalized.scale(ARROW_SIZE))
                .plus(perp.scale(ARROW_SIZE / 2));
        Vec arrow2 = end.minus(normalized.scale(ARROW_SIZE))
                .minus(perp.scale(ARROW_SIZE / 2));

        int[] xPoints = { (int) end.x(), (int) arrow1.x(), (int) arrow2.x() };
        int[] yPoints = { (int) end.y(), (int) arrow1.y(), (int) arrow2.y() };
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
}
