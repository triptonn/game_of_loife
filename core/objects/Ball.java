package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import data.Vec;
import interfaces.Attractor;
import interfaces.Moveable;
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
public class Ball extends SceneObject implements Moveable, Attractor, Renderable, Updateable {

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
        return this.__angularVel;
    }

    @Override
    public void setAngularVelocity(Vec angularVel) {
        this.__angularVel = angularVel;
    }

    @Override
    public Vec getAngularAcceleration() {
        return this.__angularAcc;
    }

    @Override
    public void applyMomentum(Vec momentum) {
        this.__inertia = this.__mass * __radius * __radius;
        Vec m = momentum.scale(1 / this.__inertia);
        this.__angularAcc = this.__acc.plus(m);
    }

    @Override
    public Vec attract(Moveable m) {
        Vec distanceVec = this.__loc.minus(m.getLocation());

        if (distanceVec.mag() == 0)
            return new Vec(0.0, 0.0);

        Vec direction = distanceVec.norm();
        double distance = distanceVec.mag();
        distance = Math.min(Math.max(distance, 5), 25);

        double magnitude = (__mass * m.getMass()) / (distance * distance);
        Vec force = direction.scale(magnitude);
        return force;
    }

    private int __radius;
    private double __mass;
    private double __inertia;

    private Vec __loc;
    private Vec __vel;
    private Vec __acc;

    private double __angle;
    private Vec __angularVel;
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
    private double __bounceFactor = 0.80;

    /**
     * <h1>Constructor of the Ball object</h1>
     *
     * @param name     Type: String --> Name as identifier
     * @param radius   Type: int --> Radius in pixel
     * @param mass     Type: double --> Mass of the ball
     * @param loc      Type: Vec --> Location (drawing origin, not the center)
     * @param sceneDim Type: Dimension --> Scene dimension
     * @param color    Type: Color --> Color of the ball object
     *
     *
     */
    public Ball(String name, int radius, double mass, Vec loc, Dimension sceneDim, Color color) {
        super(name, loc, new Dimension(radius, radius), sceneDim);

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
        double velCutOff = 0.05;

        int dimX = (int) this.getSceneDim().width;
        int dimY = (int) this.getSceneDim().height;

        // Translation
        this.__vel = this.__vel.plus(__acc);
        this.__loc = this.__loc.plus(__vel);
        __setLoc(
                Math.max(Math.min(this.__loc.x(), dimX - this.__radius),
                        0 + this.__radius),
                Math.max(Math.min(this.__loc.y(), dimY - this.__radius),
                        0 + this.__radius));

        this.__acc = this.__acc.scale(0);

        // Rotation
        if (this.__angularVel != null
                && this.__angularAcc != null
                && this.__angularVel.mag() != 0
                && this.__angularAcc.mag() != 0) {
            this.__angularVel = this.__angularVel.plus(__angularAcc);
            this.__angle = this.__angle + this.__angularVel.mag();
            this.__angularAcc = this.__angularAcc.scale(0);
        }

        if (this.__isBouncy && !this.__isLanded) {

            int locX = (int) this.__loc.x();
            int locY = (int) this.__loc.y();

            int velX = (int) this.__vel.x();
            int velY = (int) this.__vel.y();

            double velDotX = this.__vel.dot(new Vec(0, -1));
            double velDotY = this.__vel.dot(new Vec(0, -1));

            if (locX + __radius >= dimX && velDotX < 0 && Math.abs(velX) >= velCutOff) {
                locX = Math.min(locX + __radius, dimX);
                bounce("horizontal");
            } else if (locX - __radius <= 0 && velDotX < 0 && Math.abs(velX) >= velCutOff) {
                locX = Math.max(locX - __radius, 0);
                bounce("horizontal");
            } else if ((locX + __radius >= dimX || locX - __radius <= 0) && Math.abs(velDotX) < velCutOff) {
                locX = Math.min(locX + __radius, dimX);
                __setVelX(0.0);
                this.__isSliding = true;
            }

            if (locY + __radius >= dimY && velDotY < 0 && Math.abs(velY) >= velCutOff) {
                locY = Math.min(locY + __radius, dimY);
                bounce("vertical");
            } else if (locY - __radius <= 0 && -velDotY < 0 && Math.abs(velY) >= velCutOff) {
                locY = Math.max(locY - __radius, 0);
                bounce("vertical");
            } else if ((locY + __radius >= dimY || locY - __radius < 0) && Math.abs(velY) < velCutOff) {
                __setVelY(0.0);
                this.__isLanded = true;
            }
        } else if (this.__isBouncy && this.__isLanded) {
            int locX = (int) this.__loc.x();
            int velX = (int) this.__vel.x();
            double velDotX = this.__vel.dot(new Vec(0, -1));

            if (locX + __radius >= dimX && velDotX < 0 && Math.abs(velX) >= velCutOff) {
                locX = Math.min(locX + __radius, dimX);
                bounce("horizontal");
            } else if (locX - __radius <= 0 && velDotX < 0 && Math.abs(velX) >= velCutOff) {
                locX = Math.max(locX - __radius, 0);
                bounce("horizontal");
            } else if ((locX + __radius >= dimX || locX - __radius <= 0) && Math.abs(velDotX) < velCutOff) {
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
        if (!this.__isVisible)
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

    @Override
    public Vec getLocation() {
        return this.__loc;
    }

    @Override
    public Vec getVelocity() {
        return this.__vel;
    }

    public void setVelocity(Vec velocity) {
        this.__vel = velocity;
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
    public void setVisible(boolean visible) {
        this.__isVisible = visible;
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

    public int getRadius() {
        return this.__radius;
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
