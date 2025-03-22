package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import data.Vec;
import interfaces.Movable;
import interfaces.Renderable;
import interfaces.Updateable;

// TODO: Finish documentation

/**
 * <h1>Ball</h1>
 * Ball extends the SceneObject class to a ball physics-object which can be
 * drawn using Graphics2D.
 * 
 * @param name     Type: String --> Name as identifier
 * @param __radius Type: int --> Radius in pixel
 * @param __mass   Type: double --> Mass of the ball
 * @param __loc    Type: Vec --> Location (drawing origin, not the center)
 * @param dim      Type: Dimension --> Scene dimension
 * @param __color  Type: Color --> Color of the ball object
 *
 *
 */
public class Ball extends SceneObject implements Movable, Renderable, Updateable {
    private int __radius;
    private double __mass;

    private Vec __loc;
    private Vec __vel;
    private Vec __acc;

    private Color __color;
    private boolean __isVisible = false;

    private boolean __isLanded = false;
    private boolean __isSliding = false;
    private boolean __isBouncy = false;
    private double __bounceFactor = 0.80;

    /**
     * <h1>Constructor of the Ball object</h1>
     *
     * @param name   Type: String --> Name as identifier
     * @param radius Type: int --> Radius in pixel
     * @param mass   Type: double --> Mass of the ball
     * @param loc    Type: Vec --> Location (drawing origin, not the center)
     * @param dim    Type: Dimension --> Scene dimension
     * @param color  Type: Color --> Color of the ball object
     *
     *
     */
    public Ball(String name, int radius, double mass, Vec loc, Dimension dim, Color color) {
        super(name, loc, dim);

        this.name = name;
        this.__radius = radius;
        this.__mass = mass;

        this.__loc = loc;
        this.__vel = new Vec(0.0, 0.0);
        this.__acc = new Vec(0.0, 0.0);

        this.__color = color;
    }

    /**
     * <h1>update() --> void</h1>
     * Updates location of the ball object by applying an accumulated acceleration
     * force to the ball object.
     */

    @Override
    public void update() {
        int dimX = (int) this.getDim().width;
        int dimY = (int) this.getDim().height;

        this.__vel = this.__vel.plus(__acc);

        this.__loc = this.__loc.plus(__vel);

        __setLoc(Math.max(Math.min(this.__loc.x(), dimX - this.__radius), 0 + this.__radius),
                Math.max(Math.min(this.__loc.y(), dimY - this.__radius), 0 + this.__radius));

        this.__acc = this.__acc.scale(0);

        if (this.__isBouncy && !this.__isLanded) {
            double velCutOff = 0.1;

            int locX = (int) this.__loc.x();
            int locY = (int) this.__loc.y();

            int velX = (int) this.__vel.x();
            int velY = (int) this.__vel.y();

            double velDotX = this.__vel.dot(new Vec(0, -1));
            double velDotY = this.__vel.dot(new Vec(0, -1));

            if (locX + __radius >= dimX && velDotX < 0 && velX >= velCutOff) {
                locX = Math.min(locX + __radius, dimX);
                bounce(true);
            } else if (locX - __radius <= 0 && -velDotX < 0 && velX >= velCutOff) {
                locX = Math.max(locX - __radius, 0);
                bounce(true);
            } else if ((locX + __radius >= dimX || locX - __radius <= 0) && velX < velCutOff) {
                __setVelX(0.0);
                this.__isSliding = true;
            }

            if (locY + __radius >= dimY && velDotY < 0 && velY >= velCutOff) {
                locY = Math.min(locY + __radius, dimY);
                bounce(false);
            } else if (locY - __radius <= 0 && -velDotY < 0 && velY >= velCutOff) {
                locY = Math.max(locY - __radius, 0);
                bounce(false);
            } else if ((locY + __radius >= dimY || locY - __radius < 0) && velY < velCutOff) {
                __setVelY(0.0);
                this.__isLanded = true;
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (!__isVisible)
            return;
        g2d.setColor(__color);
        g2d.fillOval(((int) this.__loc.x() - this.__radius),
                ((int) this.__loc.y() - this.__radius),
                this.__radius * 2,
                this.__radius * 2);
    }

    @Override
    public void applyForce(Vec force) {
        Vec f = force.scale(1 / __mass);
        this.__acc = this.__acc.plus(f);
    }

    public void bounce(boolean isHorizontal) {
        this.__vel = this.__vel.reflect2D(isHorizontal ? 1 : 0).scale(this.__bounceFactor);
    }

    public int get__radius() {
        return this.__radius;
    }

    @Override
    public Vec getLocation() {
        return this.__loc;
    }

    @Override
    public Vec getVelocity() {
        return this.__vel;
    }

    @Override
    public Vec getAcceleration() {
        return this.__acc;
    }

    @Override
    public double getMass() {
        return this.__mass;
    }

    @Override
    public void setMass(double m) {
        this.__mass = m;
    }

    @Override
    public Color getColor() {
        return this.__color;
    }

    @Override
    public void setColor(Color color) {
        this.__color = color;
    }

    @Override
    public boolean isVisible() {
        return this.__isVisible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.__isVisible = visible;
    }

    public boolean isBouncy() {
        return this.__isBouncy;
    }

    public void setBouncy(boolean bouncy) {
        this.__isBouncy = bouncy;
    }

    public void setBounceFactor(double factor) {
        this.__bounceFactor = factor;
    }

    private void __setLoc(double locX, double locY) {
        this.__loc = new Vec(locX, locY);
    }

    private void __setVelX(double velX) {
        this.__vel = new Vec(velX, this.__vel.y());
    }

    private void __setVelY(double velY) {
        this.__vel = new Vec(this.__vel.x(), velY);
    }
}
