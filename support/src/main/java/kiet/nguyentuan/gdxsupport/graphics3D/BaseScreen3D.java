package kiet.nguyentuan.gdxsupport.graphics3D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import kiet.nguyentuan.gdxsupport.graphics2D.BaseScreen;

public abstract class BaseScreen3D extends BaseScreen {

    private Stage3D modelStage;
    private AssetManager assetManager;

    private boolean isLoading;
    public BaseScreen3D(Game game){
        super(game);

    }

    @Override
    protected void create(){
        setModelStage(new Stage3D());
        createBaseScreen3D();
    }

    @Override
    protected void update(float dT){
        updateBaseScreen3D(dT);
    }
    protected abstract void createBaseScreen3D();
    protected abstract void updateBaseScreen3D(float dT);
    protected abstract void createUI();
    private void preCreateUI(int width, int height){
        getInputMultiplexer().removeProcessor(uiStage);
        uiStage=new Stage(new FitViewport(width,height));
        createUI();
        getInputMultiplexer().addProcessor(uiStage);

    }
    public abstract void doneLoading(AssetManager asset);
    @Override
    public void render(float delta) {
        if((isLoading&&assetManager.update())){
           doneLoading(assetManager);
           isLoading=false;
        }
        if(!isPaused())
            modelStage.act(delta);
        update(delta);
        modelStage.draw();
        drawUI(delta);
    }

    @Override
    public void resize(int width, int height) {
        //super.resize(width, height);
        getModelStage().resize(width,height);
        preCreateUI(width,height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return super.scrolled(amount);
    }

    public Stage3D getModelStage() {
        return modelStage;
    }

    private void setModelStage(Stage3D modelStage) {
        this.modelStage = modelStage;
    }
    public void loadAsset(String modelName){
        if(assetManager==null)
            assetManager=new AssetManager();
        assetManager.load(modelName, Model.class);
        isLoading=true;
    }

    public void addModel(BaseActor3D actor3D){
        getModelStage().addActor(actor3D);
    }
}
