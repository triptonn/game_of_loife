package objects;

import data.Vec;

public class SceneObject {
    public String name;
    public Vec loc;

    public SceneObject(String name, Vec loc) {
        this.name = name;
        this.loc = loc;
    }

    public String getName() {
        return this.name;
    };

    public void setName(String name) {
        this.name = name;
    };
}
