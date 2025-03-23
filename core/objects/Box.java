package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import data.Vec;
import interfaces.Inert;
import interfaces.Renderable;

public class Box extends SceneObject implements Inert, Renderable {
    private int __width;
    private int __height;

    private boolean __isVisible = false;
    private Color __color;

    public Box(
            String name,
            Vec loc,
            int width,
            int height,
            Dimension sceneDim,
            Color color) {

        super(name, loc, new Dimension(width, height), sceneDim);
        this.__width = width;
        this.__height = height;
        this.__color = color;
    }

    @Override
    public void render(Graphics2D g2d) {
        if (this.__isVisible) {
            g2d.setColor(this.__color);
            g2d.fillRect(
                    (int) this.loc.x(),
                    (int) this.loc.y(),
                    this.__width,
                    this.__height);
        }
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

    @Override
    public boolean isVisible() {
        return this.__isVisible;
    }

    @Override
    public void setVisible(boolean isVisible) {
        this.__isVisible = isVisible;
    }

}
