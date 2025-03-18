package vector_shizzle;

import interfaces.Mover;
import interfaces.SceneObject;

import java.awt.Graphics2D;
import java.util.ArrayList;

import data.Vec;

public class SceneModel {
    private boolean isShowComponents = false;

    private ArrayList<SceneObject> objects;
    private ArrayList<Mover> movers;

    private Vec origin = new Vec(400.0, 300.0);

    private Vec mousePos = origin;

    public SceneModel() {
        objects = new ArrayList<>();
        movers = new ArrayList<>();
    }

    public void addObject(SceneObject obj) {
        objects.add(obj);
        if (obj instanceof Mover) {
            movers.add((Mover) obj);
        }
    }

    public void update() {
        for (Mover mover : movers) {
            Vec force = new Vec(0.0, 0.02);
            mover.applyForce(force);
        }

        for (SceneObject obj : objects) {
            obj.update();
        }
    }

    public void render(Graphics2D g2d) {
        for (SceneObject obj : objects) {
            if (obj.isVisible()) {
                obj.render(g2d);
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
