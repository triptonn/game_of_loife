package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import data.Vec;
import interfaces.Inert;
import interfaces.Moveable;
import interfaces.Renderable;
import interfaces.Updateable;

public class SimpleLiquid extends SceneObject implements Inert, Renderable, Updateable {
    private Vec __loc;
    private double __angle;
    private double viscosity;
    private boolean __isVisible = false;
    private Color __color;

    public SimpleLiquid(String name, int width, int height, double viscosity, Vec loc, Dimension sceneDim,
            Color color) {
        super(name, loc, new Dimension(width, height), sceneDim);

        this.viscosity = viscosity;
        this.__color = color;
        
        this.__loc = loc;
    }

    public boolean contains(Moveable mover) {
        boolean containes = false;
        SceneObject obj = (SceneObject) mover;

        Vec moverLoc = mover.getLocation();
        Dimension moverDim = obj.getObjectDim();

        boolean moverLeftRightOfBodyLeft = moverLoc.x() >= this.__loc.x();
        boolean moverRightLeftOfBodyRight = moverLoc.x() + moverDim.width <= this.__loc.x() + this.objectDim.getWidth();
        boolean moverTopUnderBodyTop = moverLoc.y() >= this.__loc.y();
        boolean moverBottomOverBodyBottom = moverLoc.y() + moverDim.height <= this.__loc.y()
                + this.objectDim.getHeight();

        if (moverLeftRightOfBodyLeft
                && moverRightLeftOfBodyRight
                && moverTopUnderBodyTop
                && moverBottomOverBodyBottom) {
            containes = true;
        }
        System.out.println(obj.name + ": " + containes);
        return containes;
    }

    public void drag(Moveable mover) {
        double speed = mover.getVelocity().mag();
        mover.applyForce(mover.getVelocity().norm().scale(-1 * this.viscosity * speed * speed));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2d) {
        if (!__isVisible)
            return;
        g2d.setColor(this.__color);
        g2d.fillRect((int) this.__loc.x(), (int) this.__loc.y(), (int) this.objectDim.width,
                (int) this.objectDim.height);
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
    public Vec getLocation() {
        return this.__loc;
    }

    @Override
    public void setLocation(Vec loc) {
        this.__loc = loc;
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
    public Color getColor() {
        return this.__color;
    }

    @Override
    public void setColor(Color color) {
        this.__color = color;
    }
}
