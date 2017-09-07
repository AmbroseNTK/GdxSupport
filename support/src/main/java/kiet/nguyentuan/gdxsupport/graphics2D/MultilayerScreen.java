package kiet.nguyentuan.gdxsupport.graphics2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by kiettuannguyen on 28/07/2017.
 */

public abstract class MultilayerScreen extends BaseScreen {

    protected Map<Integer, Layer2D> layerMap;
    protected InputMultiplexer inputMultiplexer;

    /**
     * It is like BaseScreen, but it can contains more layer.
     *
     * @param game main game
     */
    public MultilayerScreen(Game game) {
        super(game);
        layerMap = new TreeMap<Integer, Layer2D>();
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        create();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        for (Layer2D layer2D : layerMap.values()) {
            if (!isPaused() && !layer2D.isPause()) {
                layer2D.act(delta);
            }
            if (!layer2D.isHide())
                layer2D.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        for (Layer2D layer2D : layerMap.values()) {
            layer2D.getViewport().update(width, height);
        }
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

    /**
     * Get layer by its name.
     *
     * @param layerName
     * @return
     */
    public Layer2D getLayerByName(String layerName) {
        for (Layer2D layer2D : layerMap.values())
            if (layer2D.getLayerName().equals(layerName))
                return layer2D;
        return null;
    }

    /**
     * Add layer to the input processor
     *
     * @param layerZ
     */
    public void setInputForLayer(int layerZ) {
        if(inputMultiplexer==null){
            inputMultiplexer=new InputMultiplexer();
            Gdx.input.setInputProcessor(inputMultiplexer);
        }
        inputMultiplexer.addProcessor(layerMap.get(layerZ));
    }

    public void removeInputForLayer(int layerZ) {
        inputMultiplexer.removeProcessor(layerMap.get(layerZ));
    }
    public void swapTwoLayer(int lay1,int lay2){
        Layer2D temp=layerMap.get(lay1);
        layerMap.put(lay1,layerMap.get(lay2));
        layerMap.put(lay2,temp);
        layerMap.get(lay1).setLayerZ(lay1);
        layerMap.get(lay2).setLayerZ(lay2);
    }
    public void changeZ(int fromZ,int toZ){
        if(!layerMap.containsKey(toZ)) {
            layerMap.put(toZ, layerMap.get(fromZ));
            layerMap.remove(fromZ);
            layerMap.get(toZ).setLayerZ(toZ);
        }
        else
        {
            swapTwoLayer(fromZ,toZ);
        }
    }
    public void bringToFront(int Z){
        changeZ(Z,Z+1);
    }
    public void sendToBack(int Z){
        changeZ(Z,Z-1);
    }
    public void addLayer(int z, Layer2D layer2D){
        if(layerMap==null)
            layerMap=new TreeMap<Integer, Layer2D>();
        layerMap.put(z,layer2D);
    }
}
