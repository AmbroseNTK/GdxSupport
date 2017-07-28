package kiet.nguyentuan.gdxsupport.graphics2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import java.util.HashMap;

/**
 * Created by kiettuannguyen on 28/07/2017.
 */

public class MultilayerScreen extends BaseScreen {

    protected HashMap<Integer,Layer2D> layerMap;
    protected InputMultiplexer inputMultiplexer;

    /**
     * It is like BaseScreen, but it can contains more layer.
     * @param game main game
     */
    public MultilayerScreen(Game game) {
        super(game);
        layerMap=new HashMap<Integer, Layer2D>();
        layerMap.put(0,new Layer2D(shaderStage.getViewport()));
        layerMap.put(1,new Layer2D(mainStage.getViewport()));
        layerMap.put(2,new Layer2D(uiStage.getViewport()));
        layerMap.get(0).setLayerName(DefaultLayer.SHADER_LAYER);
        layerMap.get(1).setLayerName(DefaultLayer.MAIN_LAYER);
        layerMap.get(2).setLayerName(DefaultLayer.UI_LAYER);
        layerMap.get(0).setLayerZ(0);
        layerMap.get(1).setLayerZ(1);
        layerMap.get(2).setLayerZ(2);
        inputMultiplexer=new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Get layer by its name.
     * @param layerName
     * @return
     */
    public Layer2D getLayerByName(String layerName){
        for(Layer2D layer2D: layerMap.values())
            if(layer2D.getLayerName().equals(layerName))
                return layer2D;
        return null;
    }

    /**
     * Add layer to the input processor
     * @param layerZ
     */
    public void setInputForLayer(int layerZ){
        inputMultiplexer.addProcessor(layerMap.get(layerZ));
    }

    @Override
    protected void create() {

    }

    @Override
    protected void update(float dT) {

    }
}
