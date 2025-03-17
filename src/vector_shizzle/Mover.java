package vector_shizzle;

public class Mover {
    Vec loc;
    Vec vel;
    Vec acc;

    public void update() {
        vel.plus(acc);
        loc.plus(vel);
    }

    void applyForce(Vec force) {
        acc = force;
    }
}
