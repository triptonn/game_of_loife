package interfaces;

import data.Vec;

public interface Movable {
    void applyForce(Vec force);

    Vec getLocation();

    Vec getVelocity();

    Vec getAcceleration();
}
