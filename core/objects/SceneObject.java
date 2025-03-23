package objects;

import java.awt.Dimension;

import data.Vec;

public class SceneObject {
    public String name;
    protected Vec __loc;
    protected Dimension objectDim;
    protected Dimension sceneDim;

    public SceneObject(String name, Vec loc, Dimension object, Dimension scene) {
        this.name = name;
        this.__loc = loc;
        this.objectDim = object;
        this.sceneDim = scene;
    }

    public String getName() {
        return this.name;
    };

    public void setName(String name) {
        this.name = name;
    };

    public Dimension getObjectDim() {
        return this.objectDim;
    }

    public Dimension getSceneDim() {
        return this.sceneDim;
    };

    public void setSceneDim(Dimension scene) {
        this.sceneDim = scene;
    };
}
