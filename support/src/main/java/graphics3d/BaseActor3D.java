package graphics3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class BaseActor3D {
    private ModelInstance instance;
    private Vector3 position;
    private Quaternion rotation;
    private Vector3 scale;
    private AnimationController animationController;
    public void init(){
        instance=null;
        position = new Vector3(0, 0, 0);
        rotation = new Quaternion();
        scale = new Vector3(1, 1, 1);
    }
    public BaseActor3D() {
        init();
    }
    public BaseActor3D(ModelInstance instance){
        init();
        setModelInstance(instance);
    }
    public void setModelInstance(ModelInstance modelInstance){
        instance=modelInstance;
        animationController=new AnimationController(instance);
    }
    public Matrix4 calculateTransform(){
        return new Matrix4(position,rotation,scale);
    }
    public void act(float dT){
        instance.transform.set(calculateTransform());
        if(animationController!=null)
            animationController.update(dT);
    }
    public void draw(ModelBatch batch, Environment environment){
        batch.render(instance,environment);
    }
    public Vector3 getPosition(){
        return position;
    }
    public void setPosition(Vector3 pos){
        position.set(pos);
    }
    public void setPosition(float x, float y, float z){
        position.set(x,y,z);
    }
    public void addPosition(Vector3 v){
        position.add(v);
    }
    public void addPosition(float x, float y, float z){
        position.add(x,y,z);
    }
    public float getTurnAngle(){
        return rotation.getAngleAround(0,-1,0);
    }
    public void setTurnAngle(float degrees){
        rotation.set(new Quaternion(Vector3.Y,degrees));
    }
    public void turn(float degrees){
        rotation.mul(new Quaternion(Vector3.Y,-degrees));
    }
    public void moveForward(float dist){
        addPosition(rotation.transform(new Vector3(0,0,-1).scl(dist)));
    }
    public void moveUp(float dist){
        addPosition(rotation.transform(new Vector3(0,1,0)));
    }
    public void moveRight(float dist){
        addPosition(rotation.transform(new Vector3(1,0,0).scl(dist)));
    }
    public void setColor(Color c){
        for(Material m:instance.materials){
            m.set(ColorAttribute.createDiffuse(c));
        }
    }
    public void setAnimation(String animationName,int loopCount, float speed , AnimationController.AnimationListener animationListener,float transitionTime){
        animationController.animate(animationName,loopCount,speed,animationListener,transitionTime);

    }
    public BaseActor3D clone(){
        BaseActor3D newbie=new BaseActor3D();
        newbie.copy(this);
        return newbie;
    }
    public void copy(BaseActor3D orig){
        this.instance=new ModelInstance(orig.instance);
        this.position.set(orig.position);
        this.rotation.set(orig.rotation);
        this.scale.set(orig.scale);
    }
}
