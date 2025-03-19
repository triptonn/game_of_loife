package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import data.Vec;
import interfaces.Movable;
import interfaces.Renderable;
import interfaces.Updateable;

public class Ball extends SceneObject implements Movable, Renderable, Updateable {
    private int radius;
    private double mass;

    private Vec loc;
    private Vec vel;
    private Vec acc;

    private Color color;
    private boolean isVisible = false;

    private boolean isBouncy = false;
    private double bounceFactor = 0.95;

    public Ball(String name, int radius, double mass, Vec loc, Dimension dim, Color color) {
        super(name, loc, dim);
        this.name = name;
        this.radius = radius;
        this.mass = mass;

        this.loc = loc;
        this.vel = new Vec(0.0, 0.0);
        this.acc = new Vec(0.0, 0.0);

        this.color = color;
    }

    @Override
    public void update() {
        this.vel = this.vel.plus(acc);
        this.loc = this.loc.plus(vel);
        this.acc = this.acc.scale(0);

        if (isBouncy) {
            if (loc.x() + radius > this.getDim().width
                    || loc.x() - radius < 0) {
                bounce(true);
            } else if (loc.y() + radius > this.getDim().height
                    || loc.y() - radius < 0) {
                bounce(false);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (!isVisible)
            return;
        g2d.setColor(color);
        g2d.fillOval(((int) this.loc.x() - this.radius),
                ((int) this.loc.y() - this.radius),
                this.radius * 2,
                this.radius * 2);
    }

    @Override
    public void applyForce(Vec force) {
        Vec f = force.scale(1 / mass);
        this.acc = this.acc.plus(f);
    }

    public void bounce(boolean isHorizontal) {
        this.vel = this.vel.reflect2D(isHorizontal ? 1 : 0).scale(this.bounceFactor);
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
    public double getMass() {
        return this.mass;
    }

    @Override
    public void setMass(double m) {
        this.mass = m;
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

    public boolean isBouncy() {
        return this.isBouncy;
    }

    public void setBouncy(boolean bouncy) {
        this.isBouncy = bouncy;
    }

    public void setBounceFactor(double factor) {
        this.bounceFactor = factor;
    }
}
