package objects;

import java.awt.Dimension;

import data.Vec;

public class SceneObject {
    public String name;
    public Vec loc;
    private Dimension scene;

    public SceneObject(String name, Vec loc, Dimension scene) {
        this.name = name;
        this.loc = loc;
        this.scene = scene;
    }

    public String getName() {
        return this.name;
    };

    public void setName(String name) {
        this.name = name;
    };

    public Dimension getDim() {
        return this.scene;
    };

    public void setDim(Dimension scene) {
        this.scene = scene;
    };

}
