package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Dimension;

import data.Vec;
import interfaces.Inert;
import interfaces.Renderable;
import interfaces.Updateable;

public class VectorArrow extends SceneObject implements Inert, Renderable, Updateable {
    private Vec self;

    private Color color;
    private boolean isVisible;
    private int ARROW_SIZE;

    public VectorArrow(String name, Vec self, Vec loc, int arrowSize, Dimension dim, Color color) {
        super(name, loc, dim);
        this.self = self;
        this.ARROW_SIZE = arrowSize;
        this.color = color;
    }

    @Override
    public void update() {
    }

    public void update(Vec self) {
        this.self = self;
    };

    @Override
    public void render(Graphics2D g2d) {
        if (this.self.mag() == 0) {
            return;
        }

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2));

        Vec end = loc.plus(this.self);

        g2d.drawLine((int) this.loc.x(), (int) this.loc.y(),
                (int) end.x(), (int) end.y());

        Vec direction = end.minus(this.loc);
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

    public Vec getLocation() {
        return this.loc;
    }

    public void setLocation(Vec loc) {
        this.loc = loc;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
