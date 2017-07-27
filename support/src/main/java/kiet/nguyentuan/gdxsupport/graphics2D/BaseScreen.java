package kiet.nguyentuan.gdxsupport.graphics2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by kiettuannguyen on 21/07/2017.
 */

public abstract class BaseScreen implements Screen, InputProcessor {
    protected Game game;
    protected Stage uiStage;
    protected Stage mainStage;
    protected Stage shaderStage;

    private boolean paused;

    public int viewWidth;
    public int viewHeight;

    private float totalTime;

    private boolean customeShader;
    public BaseScreen(Game game){
        this.game=game;

        viewWidth=640;
        viewHeight=480;
        totalTime=0;
        customeShader=false;
        paused=false;

        mainStage=new Stage(new FitViewport(viewWidth,viewHeight));
        uiStage=new Stage(new FitViewport(viewWidth,viewHeight));
        shaderStage=new Stage(new FitViewport(viewWidth,viewHeight));
        InputMultiplexer inputMultiplexer=new InputMultiplexer(this,mainStage,uiStage,shaderStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        create();
    }

    protected abstract void create();
    protected abstract void update(float dT);

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    public void togglePaused(){
        if(isPaused())
            setPaused(false);
        else
            setPaused(true);
    }
    public void addUIActor(Actor actor){
        uiStage.addActor(actor);
    }
    public void addMainActor(Actor actor){
        mainStage.addActor(actor);
    }
    public void addShaderActor(Actor actor){shaderStage.addActor(actor);}
    public void setShader(FileHandle vsh,FileHandle fsh){
        ShaderProgram.pedantic=false;
        customeShader=true;
        shaderStage.getBatch().setShader(new ShaderProgram(vsh,fsh));
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shaderStage.act(delta);


        if(customeShader){
            totalTime+=delta;
            if(shaderStage.getBatch().getShader().isCompiled()){
                shaderStage.getBatch().getShader().begin();
                shaderStage.getBatch().getShader().setUniformf("time", totalTime);
                shaderStage.getBatch().getShader().setUniformf("resolution", viewWidth,viewHeight);
                shaderStage.getBatch().getShader().end();
            }
            else
            {
                System.out.print(shaderStage.getBatch().getShader().getLog());
            }
        }
        shaderStage.draw();

        uiStage.act(delta);
        if(!isPaused()) {
            mainStage.act(delta);
            update(delta);
        }

        mainStage.draw();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        mainStage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);
        shaderStage.getViewport().update(width,height,true);
        viewWidth=width;
        viewHeight=height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
