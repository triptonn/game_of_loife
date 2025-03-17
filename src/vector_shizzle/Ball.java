package vector_shizzle;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ball implements Mover, SceneObject {
    private int radius;

    private Vec loc;
    private Vec vel;
    private Vec acc;

    private Color color;
    private boolean isVisible = false;

    public Ball(int radius, Vec location, Color color) {
        this.radius = radius;

        this.loc = location;
        this.vel = new Vec(0.0, 0.0);
        this.acc = new Vec(0.0, 0.0);

        this.color = color;
    }

    @Override
    public void update() {
        vel.plus(acc);
        loc.plus(vel);
    }

    @Override
    public void render(Graphics2D g2d) {
        if (!isVisible)
            return;
        g2d.setColor(color);
        g2d.fillOval((int) loc.x() - radius,
                (int) loc.y() - radius,
                radius * 2,
                radius * 2);
    }

    @Override
    public void applyForce(Vec force) {
        acc = force;
    }

    public int getRadius() {
        return this.radius;
    }

    @Override
    public Vec getLocation() {
        return this.loc;
    }

    @Override
    public Vec getVelocity() {
        return this.vel;
    }

    @Override
    public Vec getAcceleration() {
        return this.acc;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }
}
