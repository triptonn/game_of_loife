package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import data.Vec;
import interfaces.Inert;
import interfaces.Moveable;
import interfaces.Renderable;

public class SimpleLiquid extends SceneObject implements Inert, Renderable {
    private Vec __loc;

    private double viscosity;

    private boolean __isVisible = false;
    private Color __color;

    public SimpleLiquid(String name, int width, int height, double viscosity, Vec loc, Dimension sceneDim,
            Color color) {
        super(name, loc, new Dimension(width, height), sceneDim);

        this.viscosity = viscosity;
        this.__color = color;
    }

    public boolean contains(Moveable mover) {
        boolean containes = false;
        SceneObject obj = (SceneObject) mover;

        Vec moverLoc = mover.getLocation();
        Dimension moverDim = obj.getObjectDim();

        boolean insideLeft = moverLoc.x() >= this.loc.x();
        boolean insideRight = moverLoc.x() + moverDim.width <= this.loc.x() + this.objectDim.getWidth();
        boolean insideTop = moverLoc.y() >= this.loc.y();
        boolean insideBottom = moverLoc.y() + moverDim.height <= this.loc.y() + this.objectDim.getHeight();

        System.out.println(obj.name + ", insideLeft " + insideLeft);
        System.out.println(obj.name + ", insideRight " + insideRight);
        System.out.println(obj.name + ", insideTop " + insideTop);
        System.out.println(obj.name + ", insideBottom " + insideBottom);

        if (insideLeft && insideRight && insideTop && insideBottom) {
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
    public void render(Graphics2D g2d) {
        if (!__isVisible)
            return;
        g2d.setColor(this.__color);
        g2d.fillRect((int) this.loc.x(), (int) this.loc.y(), (int) this.objectDim.width, (int) this.objectDim.height);
        System.out.println("Render SimpleLiquid");
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
    public Color getColor() {
        return this.__color;
    }

    @Override
    public void setColor(Color color) {
        this.__color = color;
    }
}
