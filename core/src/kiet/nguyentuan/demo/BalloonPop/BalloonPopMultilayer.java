package kiet.nguyentuan.demo.BalloonPop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import kiet.nguyentuan.gdxsupport.Utils.ShaderParameters;
import kiet.nguyentuan.gdxsupport.graphics2D.BaseActor;
import kiet.nguyentuan.gdxsupport.graphics2D.Layer2D;
import kiet.nguyentuan.gdxsupport.graphics2D.MultilayerScreen;

/**
 * Created by nguye on 9/7/2017.
 */

public class BalloonPopMultilayer extends MultilayerScreen {
    public BalloonPopMultilayer(Game game){
        super(game);
    }
    Layer2D gameObjects;
    Layer2D background;
    BaseActor balloon;
    float elapsedTime;
    @Override
    protected void create() {
        background=new Layer2D(new FitViewport(viewWidth,viewHeight));
        gameObjects=new Layer2D(new FitViewport(viewWidth,viewHeight));
        addLayer(0,background);
        addLayer(1,gameObjects);
        setInputForLayer(1);
        balloon=new BaseActor(new Texture("BalloonPop/green-balloon.png"));
        gameObjects.addActor(balloon);
        background.addActor(new BaseActor(new Texture("BalloonPop/Game-layout.png")));
        background.setShaderProgram(Gdx.files.internal("BalloonPop/shaders/sky.vsh.glsl"),Gdx.files.internal("BalloonPop/shaders/ball.fsh.glsl"));
        elapsedTime=0;
    }

    @Override
    protected void update(float dT) {
        elapsedTime+=dT;
        background.passShaderUniforms(new ShaderParameters() {
            @Override
            public void passShaderUniforms(ShaderProgram shaderProgram) {
                shaderProgram.setUniformf("time", elapsedTime);
                shaderProgram.setUniformf("resolution", viewWidth, viewHeight);
            }
        });
    }
}
