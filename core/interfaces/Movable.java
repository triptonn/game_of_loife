package interfaces;

import data.Vec;

public interface Movable {
    void applyForce(Vec force);

    Vec getLocation();

    Vec getVelocity();

    Vec getAcceleration();

    double getMass();

    void setMass(double m);

    boolean isBouncy();

    void setBouncy(boolean bouncy);

    void setBounceFactor(double factor);

    boolean isLanded();

    boolean isSliding();

    double getFrictionCoefficient();

    void setFrictionCoefficient(double coefficient);
}
