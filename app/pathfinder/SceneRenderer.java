package pathfinder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import data.Vec;

public class SceneRenderer {
    private static final int ARROW_SIZE = 10;

    public void render(Graphics2D g2d, SceneModel model) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());
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

    /*
     * private void drawComponents(Graphics2D g2d, Vec origin, Vec vector, Color
     * color) {
     * g2d.setColor(color);
     * g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,
     * BasicStroke.JOIN_BEVEL, 0, new float[] { 5 }, 0));
     * 
     * Vec xComponent = new Vec(vector.x(), 0);
     * g2d.drawLine((int) origin.x(), (int) origin.y(), (int) (origin.x() +
     * xComponent.x()), (int) origin.y());
     * 
     * Vec yComponent = new Vec(0, vector.y());
     * g2d.drawLine((int) origin.x(), (int) origin.y(), (int) origin.x(), (int)
     * (origin.y() + yComponent.y()));
     * 
     * g2d.setFont(new Font("Arial", Font.PLAIN, 12));
     * g2d.drawString(String.format("x: %.1f", vector.x()),
     * (int) (origin.x() + (xComponent.x() / 2)),
     * (int) origin.y() - 5);
     * 
     * g2d.drawString(String.format("y: %.1f", vector.y()),
     * (int) origin.x() - 25,
     * (int) (origin.y() + (yComponent.y() / 2)));
     * }
     */

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

    /*
     * private void drawMouseInteraction(Graphics2D g2d, SceneModel model) {
     * g2d.setColor(new Color(255, 255, 255, 100));
     * g2d.setStroke(new BasicStroke());
     * g2d.drawLine((int) model.getOrigin().x(), (int) model.getOrigin().y(),
     * (int) model.getMousePos().x(), (int) model.getMousePos().y());
     * }
     */

    private int getHeight() {
        return Pathfinder.WINDOW_HEIGHT;
    }

    private int getWidth() {
        return Pathfinder.WINDOW_WIDTH;
    }
}
