package mouse_hunter;

import interfaces.Mover;
import interfaces.SceneObject;
import objects.Ball;
import objects.VectorArrow;

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

    // Scene specifics
    private Vec ballPos;
    private Vec ballVel;
    private Vec ballAcc;

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

    public void update(SceneModel model) {
        for (Mover mover : movers) {
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

        for (SceneObject obj : objects) {
            if (obj instanceof VectorArrow) {
                VectorArrow arrow = (VectorArrow) obj;
                if (arrow.getName() == "Acc") {
                    arrow.update(this.ballAcc.scale(1000), this.ballPos);
                } else if (arrow.getName() == "Vel") {
                    arrow.update(this.ballVel.scale(10), this.ballPos);
                }
            }
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
