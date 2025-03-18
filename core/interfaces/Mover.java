package interfaces;

import data.Vec;

public interface Mover {
    void applyForce(Vec force);

    Vec getLocation();

    Vec getVelocity();

    Vec getAcceleration();
}
