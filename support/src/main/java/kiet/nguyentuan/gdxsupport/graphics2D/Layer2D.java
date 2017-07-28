package kiet.nguyentuan.gdxsupport.graphics2D;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by kiettuannguyen on 28/07/2017.
 */

public class Layer2D extends Stage {

    protected int layerZ;
    private String layerName;
    private boolean customShader;
    protected ShaderProgram defaultShader;
    public void init(){
        defaultShader=getBatch().getShader();
    }
    public Layer2D(){
        super();
        init();
    }
    public Layer2D(Viewport viewport){
        super(viewport);
        init();
    }

    public boolean hasActor(){
        return this.getActors().size==0;
    }

    /**
     * Set your custom shader to this layer.
     * @param vsh File vsh
     * @param fsh File fsh
     */
    public void setShaderProgram(FileHandle vsh,FileHandle fsh){
        ShaderProgram shaderProgram=new ShaderProgram(vsh,fsh);
        setShaderProgram(shaderProgram);
    }

    /**
     * Set your custom shader to this layer.
     * @param shaderProgram Shader program
     */
    public void setShaderProgram(ShaderProgram shaderProgram){
        getBatch().setShader(shaderProgram);
        setCustomShader(true);
    }

    /**
     * Clear your custom shader program and change it to default.
     */
    public void clearShader(){
        setShaderProgram(defaultShader);
        setCustomShader(false);
    }
    public ShaderProgram getShader(){
        return getBatch().getShader();
    }

    public int getLayerZ() {
        return layerZ;
    }

    public void setLayerZ(int layerZ) {
        this.layerZ = layerZ;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public boolean isCustomShader() {
        return customShader;
    }

    public void setCustomShader(boolean customShader) {
        this.customShader = customShader;
    }
}
