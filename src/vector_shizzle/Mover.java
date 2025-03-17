package vector_shizzle;

public interface Mover {
    void update();

    void applyForce(Vec force);

    Vec getLocation();

    Vec getVelocity();

    Vec getAcceleration();
}
