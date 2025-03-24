package templates;

import interfaces.Attractor;
import interfaces.Inert;
import interfaces.Informative;
import interfaces.Moveable;
import interfaces.Renderable;
import interfaces.Updateable;
import objects.SceneObject;
import objects.SimpleLiquid;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;

import data.Vec;

public class SceneModel {
    private boolean isShowComponents = false;

    private ArrayList<Attractor> attractors;
    private ArrayList<Informative> informatives;
    private ArrayList<Moveable> movers;
    private ArrayList<SceneObject> objects;
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
        if (obj instanceof Moveable) {
            movers.add((Moveable) obj);
        }

        if (obj instanceof Updateable) {
            updaters.add((Updateable) obj);
        }

        if (obj instanceof Renderable) {
            renderers.add((Renderable) obj);
        }

        if (obj instanceof Informative) {
            informatives.add((Informative) obj);
        }
    }

    public void update() {
        for (Moveable mover : movers) {
            // Gravity could go here
        }

        for (Updateable updater : updaters) {
            updater.update();
        }

        for (SceneObject object : objects) {
            // for updates to all objects
        }
    }

    public void render(Graphics2D g2d) {
        ArrayList<Renderable> background = new ArrayList<>();
        ArrayList<Renderable> liquidBodies = new ArrayList<>();
        ArrayList<Renderable> actors = new ArrayList<>();

        for (Renderable r : renderers) {
            if (r.isVisible()) {
                if (r instanceof Moveable) {
                    actors.add(r);
                }

                if (r instanceof SimpleLiquid) {
                    liquidBodies.add(r);
                }

                if (r instanceof Inert) {
                    background.add(r);
                }
            }
        }

        for (Renderable b : background) {
            b.render(g2d);
        }

        for (Renderable a : actors) {
            a.render(g2d);
        }

        for (Renderable l : liquidBodies) {
            l.render(g2d);
        }
    }

    public void setShowComponents(boolean state) {
        this.isShowComponents = state;
    }

    public ArrayList<SceneObject> getObjects() {
        return this.objects;
    }

    public ArrayList<Attractor> getAttractors() {
        return this.attractors;
    }

    public ArrayList<Informative> getInformatives() {
        return this.informatives;
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
