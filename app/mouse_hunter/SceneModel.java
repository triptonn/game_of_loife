package mouse_hunter;

import interfaces.Inert;
import interfaces.Movable;
import interfaces.Renderable;
import interfaces.Updateable;
import objects.Ball;
import objects.SceneObject;
import objects.VectorArrow;

import java.awt.Graphics2D;
import java.util.ArrayList;

import data.Vec;

public class SceneModel {
    private boolean isShowComponents = false;
    private ArrayList<SceneObject> objects;
    private ArrayList<Inert> inerts;
    private ArrayList<Movable> movers;
    private ArrayList<Renderable> renderers;
    private ArrayList<Updateable> updaters;

    private Vec origin = new Vec(
            (MouseHunter.WINDOW_WIDTH - 40) / 2,
            (MouseHunter.WINDOW_HEIGHT - 40) / 2);

    private Vec mousePos = origin;

    // Scene specifics
    private Vec ballPos;
    private Vec ballVel;
    private Vec ballAcc;

    public SceneModel() {
        inerts = new ArrayList<>();
        objects = new ArrayList<>();
        movers = new ArrayList<>();
        renderers = new ArrayList<>();
        updaters = new ArrayList<>();
    }

    public void addObject(SceneObject obj) {
        objects.add(obj);

        if (obj instanceof Inert) {
            inerts.add((Inert) obj);
        }

        if (obj instanceof Movable) {
            movers.add((Movable) obj);
        }

        if (obj instanceof Renderable) {
            renderers.add((Renderable) obj);
        }

        if (obj instanceof Updateable) {
            updaters.add((Updateable) obj);
        }
    }

    public void update(SceneModel model) {
        for (SceneObject obj : objects) {
            // game logic for all objects
        }

        for (Movable mover : movers) {
            if (mover instanceof SceneObject) {
                Vec direction = model.getMousePos().minus(mover.getLocation());

                if (mover instanceof Ball) {
                    Ball ball = (Ball) mover;

                    if (ball.isBouncy()) {
                        int locX = (int) ball.getLocation().x();
                        int locY = (int) ball.getLocation().y();

                        if (locX > MouseHunter.WINDOW_WIDTH
                                || locX < 0) {
                            ball.bounce(true);
                        } else if (locY > MouseHunter.WINDOW_HEIGHT
                                || locY < 0) {
                            ball.bounce(false);
                        }
                    }
                }

                if (direction.mag() > 0) {
                    Vec dragToCursor = direction.norm().scale(0.05);
                    mover.applyForce(dragToCursor);
                }

                this.ballAcc = mover.getAcceleration();
                this.ballVel = mover.getVelocity();
                this.ballPos = mover.getLocation();
            }
        }

        for (Inert i : inerts) {
            i.setLocation(this.ballPos);
        }

        for (Updateable updater : updaters) {
            if (updater instanceof VectorArrow) {
                VectorArrow arrow = (VectorArrow) updater;
                if (arrow.getName() == "Acc") {
                    arrow.update(this.ballAcc.scale(1000));
                } else if (arrow.getName() == "Vel") {
                    arrow.update(this.ballVel.scale(10));
                }
            } else {
                updater.update();
            }
        }
    }

    public void render(Graphics2D g2d) {
        for (Renderable r : renderers) {
            if (r.isVisible()) {
                r.render(g2d);
            }
        }
    }

    public void setShowComponents(boolean state) {
        this.isShowComponents = state;
    }

    public ArrayList<SceneObject> getObjects() {
        return objects;
    }

    public boolean isShowComponents() {
        return isShowComponents;
    }

    public Vec getOrigin() {
        return this.origin;
    }

    public Vec getMousePos() {
        return this.mousePos;
    }

    public void setMousePos(Vec pos) {
        this.mousePos = pos;
    }

    public void toggleShowComponents() {
        this.isShowComponents = !this.isShowComponents;
    }
}
