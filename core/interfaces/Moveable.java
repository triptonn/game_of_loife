package interfaces;

import data.Vec;

public interface Moveable {
    void applyForce(Vec force);

    void applyMomentum(Vec momentum);

    Vec getLocation();

    Vec getVelocity();

    Vec getAcceleration();

    double getAngle();

    Vec getAngularVelocity();

    void setAngularVelocity(Vec angularVel);

    Vec getAngularAcceleration();

    double getMass();

    void setMass(double m);

    boolean isBouncy();

    void setBouncy(boolean bouncy);

    void setBounceFactor(double factor);

    boolean isAttractor();

    void setAttractor(boolean isAttractor);

    boolean isLanded();

    boolean isSliding();

    boolean getHasFriction();

    void setHasFriction(boolean hasFriction);

    double getFrictionCoefficient();

    void setFrictionCoefficient(double coefficient);

    boolean getHasDrag();

    void setHasDrag(boolean hasDrag);

    double getDragCoefficient();

    void setDragCoefficient(double coefficient);
}
