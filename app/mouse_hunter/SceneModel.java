package mouse_hunter;

import interfaces.Mover;
import interfaces.SceneObject;

import java.awt.Graphics2D;
import java.util.ArrayList;

import data.Vec;

public class SceneModel {
    private boolean isShowComponents = false;
    private ArrayList<SceneObject> objects;
    private ArrayList<Mover> movers;

    private Vec origin = new Vec(
            (MouseHunter.WINDOW_WIDTH - 40) / 2,
            (MouseHunter.WINDOW_HEIGHT - 40) / 2);

    private Vec mousePos = origin;

    public SceneModel() {
        objects = new ArrayList<>();
        movers = new ArrayList<>();
    }

    public void addObject(SceneObject obj) {
        objects.add(obj);
        System.out.println("Snapshot objects++: " + objects.toString());
        if (obj instanceof Mover) {
            movers.add((Mover) obj);
            System.out.println("Snapshot movers++: " + movers.toString());
        }
    }

    public void update(SceneModel model) {
        for (Mover mover : movers) {
            Vec direction = model.getMousePos().minus(mover.getLocation());
            if (direction.mag() > 0) {
                Vec dragToCursor = direction.norm().scale(0.1);
                System.out.println("Drag to cursor: " + dragToCursor.toString());
                mover.applyForce(dragToCursor);
            }
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
        System.out.println("Setting mouse position");
        this.mousePos = pos;
    }

    public void toggleShowComponents() {
        this.isShowComponents = !this.isShowComponents;
    }
}
