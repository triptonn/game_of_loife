package massive_balls;

import objects.SceneObject;

import interfaces.Movable;
import interfaces.Renderable;
import interfaces.Updateable;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;

import data.Vec;

public class SceneModel {
    private boolean isShowComponents = false;

    private ArrayList<SceneObject> objects;
    private ArrayList<Movable> movers;
    private ArrayList<Renderable> renderers;
    private ArrayList<Updateable> updaters;

    private Dimension dim;
    private Vec origin;

    private Vec mousePos = origin;

    public SceneModel(Dimension scene) {
        this.dim = scene;
        this.origin = new Vec(this.dim.width / 2, this.dim.height / 2);

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
            // stuff for all movers
        }

        for (Updateable updater : updaters) {
            updater.update();
        }

        for (SceneObject object : objects) {
            // stuff for all objects in the scene
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

    public Dimension getDimensions() {
        return this.dim;
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
