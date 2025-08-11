package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import data.Vec;
import interfaces.Moveable;
import interfaces.Renderable;
import interfaces.Updateable;

public class MoBox extends SceneObject implements Moveable, Renderable, Updateable {
    private int __width;
    private int __height;

    private double __mass;
    private double __inertia;

    private Vec __loc;
    private Vec __vel;
    private Vec __acc;

    private double __angle;

    private double __magAngularVel;
    // private Vec __dirAngularVel;
    private Vec __angularAcc;

    private Color __color;
    private boolean __isVisible = false;

    private boolean __hasFriction = false;
    private double __frictionCoefficient = 0.80;

    // TODO: Remove drag from here and used the SimpleLiquid class to implement drag
    private boolean __hasDrag = false;
    private double __dragCoefficient = 0.0;

    private boolean __isAttractor = false;

    private boolean __isLanded = false;
    private boolean __isSliding = false;
    private boolean __isBouncy = false;
    private double __bounceFactor = 0.30;

    public MoBox(
            String name,
            Vec loc,
            int width,
            int height,
            double mass,
            Dimension sceneDim,
            Color color) {

        super(name, loc, new Dimension(width, height), sceneDim);
        this.__width = width;
        this.__height = height;
        this.__mass = mass;

        this.__loc = loc;
        this.__vel = new Vec(0.0, 0.0);
        this.__acc = new Vec(0.0, 0.0);

        this.__color = color;
    }

    @Override
    public void update() {
        double velCutOff = 0.05;

        int dimX = (int) this.getSceneDim().width;
        int dimY = (int) this.getSceneDim().height;

        // Translation
        this.__vel = this.__vel.plus(__acc);
        this.__loc = this.__loc.plus(__vel);

        __setLoc(Math.max(Math.min(this.__loc.x(), dimX - this.__width),
                0 + this.__width),
                Math.max(Math.min(this.__loc.y(), dimY - this.__height),
                        0 + this.__height));

        this.__acc = this.__acc.scale(0);

        // Rotation
        this.__magAngularVel += this.__angularAcc.mag();
        this.__angle += this.__magAngularVel;
        this.__angularAcc = new Vec(this.__angularAcc.scale(0));

        // Bounce
        if (this.__isBouncy && !this.__isLanded) {

            int locX = (int) this.__loc.x();
            int locY = (int) this.__loc.y();

            int velX = (int) this.__vel.x();
            int velY = (int) this.__vel.y();

            double velDotX = this.__vel.dot(new Vec(0, -1));
            double velDotY = this.__vel.dot(new Vec(0, -1));

            if (locX + this.__width >= dimX && velDotX < 0 && Math.abs(velX) >= velCutOff) {
                locX = Math.min(locX + this.__height, dimX);
                bounce("horizontal");
            } else if (locX - this.__width <= 0 && velDotX < 0 && Math.abs(velX) >= velCutOff) {
                locX = Math.max(locX - this.__width, 0);
                bounce("horizontal");
            } else if ((locX + this.__width >= dimX || locX - this.__width <= 0) && Math.abs(velDotX) < velCutOff) {
                locX = Math.min(locX + this.__width, dimX);
                __setVelX(0.0);
                this.__isSliding = true;
            }

            if (locY + this.__height >= dimY && velDotY < 0 && Math.abs(velY) >= velCutOff) {
                locY = Math.min(locY + this.__height, dimY);
                bounce("vertical");
            } else if (locY - this.__height <= 0 && -velDotY < 0 && Math.abs(velY) >= velCutOff) {
                locY = Math.max(locY - this.__height, 0);
                bounce("vertical");
            } else if ((locY + this.__height >= dimY || locY - this.__height < 0) && Math.abs(velY) < velCutOff) {
                __setVelY(0.0);
                this.__isLanded = true;
            }
        } else if (this.__isBouncy && this.__isLanded) {
            int locX = (int) this.__loc.x();
            int velX = (int) this.__vel.x();
            double velDotX = this.__vel.dot(new Vec(0, -1));

            if (locX + this.__width >= dimX && velDotX < 0 && Math.abs(velX) >= velCutOff) {
                locX = Math.min(locX + this.__width, dimX);
                bounce("horizontal");
            } else if (locX - this.__width <= 0 && velDotX < 0 && Math.abs(velX) >= velCutOff) {
                locX = Math.max(locX - this.__width, 0);
                bounce("horizontal");
            } else if ((locX + this.__width >= dimX || locX - this.__width <= 0) && Math.abs(velDotX) < velCutOff) {
                __setLoc(locX, (int) this.__loc.y());
                __setVelX(0.0);
                this.__isSliding = true;
            }
        }

        if (this.__vel.mag() > velCutOff) {
            this.__isLanded = false;
            this.__isSliding = false;
        }
    }

    public void bounce(String mode) {
        this.__vel = this.__vel.reflect2D(mode == "horizontal" ? 1 : 0).scale(this.__bounceFactor);
    }

    @Override
    public void render(Graphics2D g2d) {
        if (this.__isVisible) {
            g2d.setColor(this.__color);
            g2d.rotate(this.__angle, this.__loc.x() + (this.__width / 2), this.__loc.y() + (this.__height / 2));
            g2d.fillRect(
                    (int) this.__loc.x(),
                    (int) this.__loc.y(),
                    this.__width,
                    this.__height);
        }
    }

    @Override
    public void applyForce(Vec force) {
        Vec f = force.scale(1 / __mass);
        this.__acc = this.__acc.plus(f);
    }

    @Override
    public void applyMomentum(Vec momentum) {
        double effectiveRadius = (this.__width + this.__height) / 2;
        this.__inertia = this.__mass * effectiveRadius * effectiveRadius;
        Vec m = momentum.scale(1 / this.__inertia);
        this.__angularAcc = this.__angularAcc.plus(m);
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
    public double getAngle() {
        return this.__angle;
    }

    @Override
    public void setAngle(double angle) {
        this.__angle = angle;
    }

    @Override
    public Vec getAngularVelocity() {
        return this.__vel;
    }

    @Override
    public void setAngularVelocity(Vec angularVel) {
        this.__vel = angularVel;
    }

    @Override
    public Vec getAngularAcceleration() {
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
    public boolean isAttractor() {
        return this.__isAttractor;
    }

    @Override
    public void setAttractor(boolean isAttractor) {
        this.__isAttractor = isAttractor;
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
    public void setVisible(boolean isVisible) {
        this.__isVisible = isVisible;
    }

    @Override
    public boolean isBouncy() {
        return this.__isBouncy;
    }

    @Override
    public boolean isLanded() {
        return this.__isLanded;
    }

    @Override
    public boolean isSliding() {
        return this.__isSliding;
    }

    @Override
    public void setBouncy(boolean bouncy) {
        this.__isBouncy = bouncy;
    }

    @Override
    public void setBounceFactor(double factor) {
        this.__bounceFactor = factor;
    }

    @Override
    public boolean getHasFriction() {
        return this.__hasFriction;
    }

    @Override
    public void setHasFriction(boolean hasFriction) {
        this.__hasFriction = hasFriction;
    }

    @Override
    public double getFrictionCoefficient() {
        return this.__frictionCoefficient;
    }

    @Override
    public void setFrictionCoefficient(double coefficient) {
        this.__frictionCoefficient = coefficient;
    }

    @Override
    public boolean getHasDrag() {
        return this.__hasDrag;
    }

    @Override
    public void setHasDrag(boolean hasDrag) {
        this.__hasDrag = hasDrag;
    }

    @Override
    public double getDragCoefficient() {
        return this.__dragCoefficient;
    }

    @Override
    public void setDragCoefficient(double coefficient) {
        this.__dragCoefficient = coefficient;
    }

    protected void __setLoc(double locX, double locY) {
        this.__loc = new Vec(locX, locY);
    }

    private void __setVelX(double velX) {
        this.__vel = new Vec(velX, this.__vel.y());
    }

    private void __setVelY(double velY) {
        this.__vel = new Vec(this.__vel.x(), velY);
    }
}
