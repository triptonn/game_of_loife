package massive_balls;

import objects.Ball;
import objects.SceneObject;

import interfaces.Movable;
import interfaces.Renderable;
import interfaces.Updateable;

import java.awt.Graphics2D;
import java.util.ArrayList;

import data.Vec;

public class SceneModel {
    private boolean isShowComponents = false;

    private ArrayList<SceneObject> objects;
    private ArrayList<Movable> movers;
    private ArrayList<Renderable> renderers;
    private ArrayList<Updateable> updaters;

    private Vec origin = new Vec(MassiveBalls.WINDOW_WIDTH / 2, MassiveBalls.WINDOW_HEIGHT / 2);

    private Vec mousePos = origin;

    public SceneModel() {
        objects = new ArrayList<>();
        movers = new ArrayList<>();
        renderers = new ArrayList<>();
        updaters = new ArrayList<>();
    }

    public void addObject(SceneObject obj) {
        objects.add(obj);
        if (obj instanceof Movable) {
            movers.add((Movable) obj);
        }

        if (obj instanceof Updateable) {
            updaters.add((Updateable) obj);
        }

        if (obj instanceof Renderable) {
            renderers.add((Renderable) obj);
        }
    }

    public void update() {
        for (Movable mover : movers) {
            if (mover instanceof Ball) {
                Ball ball = (Ball) mover;
                if (ball.isBouncy()) {
                    int locX = (int) ball.getLocation().x();
                    int locY = (int) ball.getLocation().y();

                    if (locX > MassiveBalls.WINDOW_WIDTH
                            || locX < 0) {
                        ball.bounce(true);
                    } else if (locY > MassiveBalls.WINDOW_HEIGHT
                            || locY < 0) {
                        ball.bounce(false);
                    }
                }
            }
        }

        for (Updateable updater : updaters) {
            updater.update();
        }

        for (SceneObject object : objects) {
            // stuff for all children of SceneObject
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

    public ArrayList<Movable> getMovers() {
        return movers;
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
