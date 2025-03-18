package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import data.Vec;
import interfaces.Mover;
import interfaces.SceneObject;

public class Ball implements Mover, SceneObject {
    private int radius;

    private Vec loc;
    private Vec vel;
    private Vec acc;

    private Color color;
    private boolean isVisible = false;

    private boolean isBouncy = false;
    private boolean isCollidable = false;

    public Ball(int radius, Vec location, Color color) {
        this.radius = radius;

        this.loc = location;
        this.vel = new Vec(0.0, 0.0);
        this.acc = new Vec(0.0, 0.0);

        this.color = color;
    }

    @Override
    public void update() {
        System.out.println("Updating ball");
        System.out.println("acc: " + this.acc.toString());

        this.vel = this.vel.plus(acc);
        System.out.println("vel: " + this.vel.toString());

        this.loc = this.loc.plus(vel);
        System.out.println("loc: " + this.loc.toString());
    }

    @Override
    public void render(Graphics2D g2d) {
        if (!isVisible)
            return;

        System.out.println("Rendering ball");
        g2d.setColor(color);
        g2d.fillOval(((int) this.loc.x() - this.radius),
                ((int) this.loc.y() - this.radius),
                this.radius * 2,
                this.radius * 2);
    }

    @Override
    public void applyForce(Vec force) {
        this.acc = force;
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
