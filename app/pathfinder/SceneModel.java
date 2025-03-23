package pathfinder;

import interfaces.Moveable;
import interfaces.Renderable;
import interfaces.Updateable;
import objects.SceneObject;

import java.awt.Graphics2D;
import java.util.ArrayList;

import data.Vec;

public class SceneModel {
    private boolean isShowComponents = false;

    private ArrayList<SceneObject> objects;
    private ArrayList<Moveable> movers;
    private ArrayList<Renderable> renderers;
    private ArrayList<Updateable> updaters;

    private Vec origin = new Vec(400.0, 300.0);

    private Vec mousePos = origin;

    public SceneModel() {
        objects = new ArrayList<>();
        movers = new ArrayList<>();
        renderers = new ArrayList<>();
        updaters = new ArrayList<>();
    }

    public void addObject(SceneObject obj) {
        objects.add(obj);
        if (obj instanceof Moveable) {
            movers.add((Moveable) obj);
        }

        if (obj instanceof Updateable) {
            updaters.add((Updateable) obj);
        }

        if (obj instanceof Renderable) {
            renderers.add((Renderable) obj);
        }
    }

    public void update() {
        for (Moveable mover : movers) {
            Vec force = new Vec(0.0, 0.02);
            mover.applyForce(force);
        }

        for (Updateable updater : updaters) {
            updater.update();
        }

        for (SceneObject object : objects) {
            // for updates to all objects
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
