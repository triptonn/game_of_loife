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

    private Vec draw_origin;
    private Vec loc;
    private Vec vel;
    private Vec acc;

    private Color color;
    private boolean isVisible = false;

    private boolean isLanded = false;
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

    private void setLoc(double locX, double locY) {
        this.loc = new Vec(locX, locY);
    }

    private void setVel(Vec vec) {
        this.vel = vec;
    }

    private void setVel(double velX, double velY) {
        this.vel = new Vec(velX, velY);
    }

    private void setVelX(double velX) {
        this.vel = new Vec(velX, this.vel.y());
    }

    private void setVelY(double velY) {
        this.vel = new Vec(this.vel.x(), velY);
    }

    @Override
    public void update() {
        this.vel = this.vel.plus(acc);

        this.loc = this.loc.plus(vel);

        setLoc(Math.max(Math.min(this.loc.x(), 1280 - this.radius), 0 + this.radius),
                Math.max(Math.min(this.loc.y(), 720 - this.radius), 0 + this.radius));

        this.acc = this.acc.scale(0);

        if (this.isBouncy && !this.isLanded) {
            int locX = (int) this.loc.x();
            int locY = (int) this.loc.y();

            int velX = (int) this.vel.x();
            int velY = (int) this.vel.y();

            double velDotX = this.vel.dot(new Vec(0, -1));
            double velDotY = this.vel.dot(new Vec(0, -1));

            int dimX = (int) this.getDim().width;
            int dimY = (int) this.getDim().height;

            if (locX + radius >= dimX && -velDotX < 0 && velX >= 0.5) {
                locX = Math.min(locX + radius, dimX);
                bounce(true);
            } else if (locX - radius <= 0 && velDotX < 0 && velX >= 0.5) {
                locX = Math.max(locX - radius, 0);
                bounce(true);
            } else if ((locX + radius >= dimX || locX - radius <= 0) && velX < 0.5) {
                setVelX(0.0);
                System.out.println(this.name + " sliding!");
            }
            System.out.println(this.name + ", loc: " + locX + ", " + locY + ", vel: " + this.vel);

            if (locY + radius >= dimY && velDotY < 0 && velY >= 0.5) {
                locY = Math.min(locY + radius, dimY);
                bounce(false);
            } else if (locY - radius <= 0 && -velDotY < 0 && velY >= 0.5) {
                locY = Math.max(locY - radius, 0);
                bounce(false);
            } else if ((locY + radius >= dimY || locY - radius < 0) && velY < 0.5) {
                setVelY(0.0);
                this.isLanded = true;
                System.out.println(this.name + " landed!");
            }
            System.out.println(this.name + ", loc: " + locX + ", " + locY);
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
        /*
         * double x = this.loc.x();
         * double y = this.loc.y();
         */

        this.vel = this.vel.reflect2D(isHorizontal ? 1 : 0).scale(this.bounceFactor);

        /*
         * if (x < 0 || x > this.scene.getWidth()) {
         * 
         * }
         * 
         * if (y < 0 || y > this.scene.getHeight()) {
         * 
         * }
         */

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
