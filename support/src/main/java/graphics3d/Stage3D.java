package graphics3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Stage3D {
    private Environment environment;
    private PerspectiveCamera camera;
    protected final ModelBatch batch;
    protected ArrayList<BaseActor3D> actorList;
    public Stage3D() {
        setEnvironment(new Environment());
        getEnvironment().set(new ColorAttribute(ColorAttribute.AmbientLight, 0.7f, 0.7f, 0.7f, 1));
        DirectionalLight directionalLight = new DirectionalLight();
        Color lightColor = new Color(0.9f, 0.9f, 0.9f, 1);
        Vector3 lightVector = new Vector3(-1.0f, -0.75f, -0.25f);
        directionalLight.set(lightColor, lightVector);
        getEnvironment().add(directionalLight);

        setCamera(new PerspectiveCamera(50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        getCamera().position.set(10f, 10f, 10f);
        getCamera().lookAt(0, 0, 0);
        getCamera().near = 0.01f;
        getCamera().far = 1000f;
        getCamera().update();
        batch = new ModelBatch();
        actorList = new ArrayList<BaseActor3D>();
    }
    public void act(float dT){
        getCamera().update();
        for(BaseActor3D ba: actorList){
            ba.act(dT);
        }
    }
    public void draw(){
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin(getCamera());
        for(BaseActor3D ba: actorList)
            ba.draw(batch, getEnvironment());
        batch.end();
    }
    public void addActor(BaseActor3D actor){
        actorList.add(actor);
    }
    public void removeActor(BaseActor3D actor){
        actorList.remove(actor);
    }
    public void setCameraPosition(float x, float y, float z){
        getCamera().position.set(x,y,z);
    }
    public void setCameraPosition(Vector3 v){
        getCamera().position.set(v);
    }
    public void moveCamera(float x, float y, float z){
        getCamera().position.add(x,y,z);
    }
    public void moveCamera(Vector3 v){
        getCamera().position.add(v);
    }
    public void moveCameraForward(float dist) {
        Vector3 forward = new Vector3(getCamera().direction.x, 0, getCamera().direction.z).nor();
        moveCamera(forward.scl(dist));
    }
    public void moveCameraRight(float dist){
        Vector3 right=new Vector3(getCamera().direction.z,0,-getCamera().direction.x).nor();
        moveCamera(right.scl(dist));
    }
    public void moveCameraUp(float dist){
        moveCamera(0,dist,0);
    }
    public void turnCamera(float angle){
        getCamera().rotate(Vector3.Y,-angle);
    }
    public void tiltCamera(float angle){
        Vector3 right=new Vector3(getCamera().direction.z,0,-getCamera().direction.x);
        getCamera().direction.rotate(right,angle);
    }
    public void setCameraDirection(Vector3 v){
        getCamera().lookAt(v);
        getCamera().up.set(0,1,0);
    }
    public void setCameraDirection(float x, float y, float z){
        setCameraDirection(new Vector3(x,y,z));
    }

    public PerspectiveCamera getCamera() {
        return camera;
    }

    public void setCamera(PerspectiveCamera camera) {
        this.camera = camera;
    }
    public void resize(int width, int height){
        camera.viewportWidth=width;
        camera.viewportHeight=height;
        camera.update();
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
