package kiet.nguyentuan.demo.FragmentWorld;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;

import kiet.nguyentuan.gdxsupport.graphics3D.BaseActor3D;

/**
 * Created by nguye on 8/29/2017.
 */

public class Block extends BaseActor3D {
    public Block(String name, ModelInstance instance){
        super();
        setEmpty(true);
        setBlockName(name);
        setModelInstance(instance);

    }
    private boolean empty;
    private String blockName;
    private Vector2 distance;
    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
        if(isEmpty())
            clearModelInstance();
    }
    public void clearModelInstance(){
        setModelInstance(null);
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public Vector2 getDistance() {
        return distance;
    }

    public void setDistance(Vector2 distance) {
        this.distance = distance;
    }
}
