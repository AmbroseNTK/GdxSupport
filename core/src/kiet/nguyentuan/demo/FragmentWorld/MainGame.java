package kiet.nguyentuan.demo.FragmentWorld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import kiet.nguyentuan.gdxsupport.graphics3D.BaseActor3D;
import kiet.nguyentuan.gdxsupport.graphics3D.BaseScreen3D;

/**
 * Created by nguye on 8/29/2017.
 */

public class MainGame extends BaseScreen3D {
    public MainGame(Game game){
        super(game);
    }
    private final int BOARD_WIDTH = 5;
    private final int BOARD_HEIGHT = 5;
    private Vector2 velocity;
    private Block[][] blockList;
    private String[] listBlockName;
    CameraInputController cameraController;
    @Override
    protected void createBaseScreen3D() {
        listBlockName = Gdx.files.internal("FragmentWorld/list").readString().split("\r\n");
        for (String obj : listBlockName) {
            loadAsset("FragmentWorld/" + obj + ".g3db");
        }
        blockList = new Block[BOARD_WIDTH][BOARD_HEIGHT];
        cameraController = new CameraInputController(getModelStage().getCamera());
        getInputMultiplexer().addProcessor(cameraController);
        velocity=new Vector2(-2,0);
    }

    @Override
    protected void updateBaseScreen3D(float dT) {
       for(int i=0;i<BOARD_WIDTH;i++){
           for(int j=0;j<BOARD_HEIGHT;j++){
               if(blockList[i][j] !=null &&!blockList[i][j].getDistance().isZero()){
                   Vector2 dist=new Vector2(velocity.x*dT,velocity.y*dT);
                   blockList[i][j].getDistance().sub(dist);
                   blockList[i][j].getPosition().add(new Vector3(dist.x,0,dist.y));
                   if(blockList[i][j].getDistance().x<=0||blockList[i][j].getDistance().y<=0)
                       blockList[i][j].getDistance().setZero();
               }
           }
       }
    }

    @Override
    protected void createUI() {

    }

    @Override
    public void doneLoading(AssetManager asset) {

        String[] initBlock = {"lava","water"};
        for (String initName : initBlock) {
            Vector2 randPos = getRandom();
            Block block = new Block(initName, new ModelInstance(asset.get("FragmentWorld/" + initName + ".g3db", Model.class)));
            blockList[(int) (randPos.x)][(int) (randPos.y)] = block;
            block.setPosition(randPos.x * 2, 0, randPos.y * 2);
            block.setEmpty(false);
            addModel(block);
            block.setDistance(new Vector2(randPos.x*2,randPos.y*2));
        }
        BaseActor3D face = new BaseActor3D(new ModelInstance(asset.get("FragmentWorld/3DArrow.g3db", Model.class)));
        face.setPosition(0, 0, 0);
        addModel(face);
    }
    @Nullable
    private Vector2 getRandom() {
        if (blockList != null) {
            ArrayList<Vector2> nullList = new ArrayList<Vector2>();
            for (int i = 0; i < BOARD_WIDTH; i++) {
                for (int j = 0; j < BOARD_HEIGHT; j++) {
                    if (blockList[i][j] == null || blockList[i][j].isEmpty())
                        nullList.add(new Vector2(i, j));
                }
            }
            return nullList.get(MathUtils.random(0, nullList.size() - 1));
        }
        return null;
    }
}
