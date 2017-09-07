package kiet.nguyentuan.demo.Simple3DGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import kiet.nguyentuan.gdxsupport.graphics3D.BaseActor3D;
import kiet.nguyentuan.gdxsupport.graphics3D.BaseScreen3D;
import kiet.nguyentuan.gdxsupport.graphics2D.BaseActor;


public class MainStage extends BaseScreen3D {

    BaseActor3D men;
    BaseActor button1;
    CameraInputController cameraInputController;
    CellTypes[][] map;
    Environment environment;
    DirectionalLight light;
    Vector2 playerPos;
    PlayerDirect currentDirect;
    float distance;
    BaseActor arrowForward;
    BaseActor arrowLeft;
    BaseActor arrowRight;
    private int[] arrow;
    boolean showArrow;
    boolean isFirstime;
    public MainStage(Game game) {
        super(game);
    }

    @Override
    protected void createBaseScreen3D() {
        loadAsset("Simple3DGame/models/men-highpoly.g3db");
        loadAsset("Simple3DGame/models/ground.g3db");
        loadAsset("Simple3DGame/models/EgyptBlock.g3db");
        map=MapGenerator.loadMap("Simple3DGame/maps/map1.map");
        distance=0;
        arrowForward=new BaseActor(new Texture("Simple3DGame/arrow-top.png"));
        arrowLeft=new BaseActor(new Texture("Simple3DGame/arrow-right.png"));
        arrowRight=new BaseActor(new Texture("Simple3DGame/arrow-left.png"));
        arrowForward.setScale(0.05f);
        arrowRight.setScale(0.05f);
        arrowLeft.setScale(0.05f);
        arrowForward.setPosition(viewWidth*0.5f-arrowForward.getWidth()*0.5f*0.05f,viewHeight-50-arrowForward.getHeight()*0.05f);
        arrowRight.setPosition(50,viewHeight*0.5f-arrowRight.getHeight()*0.5f*0.05f);
        arrowLeft.setPosition(viewWidth-arrowLeft.getWidth()*0.05f-50,viewHeight*0.5f-arrowLeft.getHeight()*0.5f*0.05f);
        arrowForward.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isFirstime=true;
                men.setTurnAngle(0);
                currentDirect=PlayerDirect.FORWARD;
                distance=10+find((int)(playerPos.x+1),(int)(playerPos.y));
                hideArrow();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        arrowRight.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isFirstime=true;
                men.setTurnAngle(90);
                currentDirect=PlayerDirect.RIGHT;
                distance=10+find((int)(playerPos.x),(int)(playerPos.y+1));
                hideArrow();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        arrowLeft.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isFirstime=true;
                men.setTurnAngle(-90);
                currentDirect=PlayerDirect.LEFT;
                distance=10+find((int)(playerPos.x),(int)(playerPos.y-1));
                hideArrow();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        cameraInputController=new CameraInputController(getModelStage().getCamera());
        getInputMultiplexer().addProcessor(cameraInputController);
        isFirstime=true;
    }
    public void hideArrow(){
        arrowForward.setHide(true);
        arrowRight.setHide(true);
        arrowLeft.setHide(true);
    }
    @Override
    protected void updateBaseScreen3D(float dT) {
        if (men != null) {
            float dist = 5f * dT;
            if(distance<=0) {
                distance = find((int) (playerPos.x), (int) (playerPos.y));
                if(!isFirstime&&!showArrow){
                    for (int i = 0; i < 3; i++) {
                        if (arrow[i] == 1) {
                            if (i == 0)
                                arrowForward.setHide(false);
                            if (i == 1)
                                arrowRight.setHide(false);
                            if (i == 2)
                                arrowLeft.setHide(false);
                        }
                    }
                    showArrow = true;
                }
                isFirstime=false;
            }
            else {
                men.moveForward(dist);
            }
            distance-=dist;
            //getModelStage().setCameraPosition(new Vector3(men.getPosition().x - 10, men.getPosition().y + 10, men.getPosition().z));
            //getModelStage().setCameraDirection(men.getPosition());
        }

    }
    @Override
    protected void createUI() {
        button1=new BaseActor(new Texture("badlogic.jpg"));
        button1.setPosition(10,10);
        button1.setScale(0.25f);
        button1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                button1.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.fadeIn(0.5f)));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        uiStage.addActor(arrowForward);
        uiStage.addActor(arrowRight);
        uiStage.addActor(arrowLeft);
        hideArrow();
        addUIActor(button1);
    }

    @Override
    public void doneLoading(AssetManager asset) {

        for(int i=0;i<MapGenerator.width;i++){
            for(int j=0;j<MapGenerator.height;j++){
                BaseActor3D block=null;
                switch (map[i][j]){
                    case WALL:
                        block=new BaseActor3D(new ModelInstance(asset.get("Simple3DGame/models/EgyptBlock.g3db",Model.class)));
                        break;
                    case GROUND:
                        block=new BaseActor3D(new ModelInstance(asset.get("Simple3DGame/models/ground.g3db",Model.class)));
                        break;
                }
                if(block!=null) {
                    block.setPosition(i * -10, 0, j *10);
                    addModel(block);
                }
            }
        }
        men=new BaseActor3D(new ModelInstance(asset.get("Simple3DGame/models/men-highpoly.g3db",Model.class)));
        men.setAnimation("Armature|run", -1, 5f, new AnimationController.AnimationListener() {
            @Override
            public void onEnd(AnimationController.AnimationDesc animation) {

            }

            @Override
            public void onLoop(AnimationController.AnimationDesc animation) {

            }
        },0);
        addModel(men);
        playerPos=new Vector2(1,5);
        men.setPosition(playerPos.x*10,0,playerPos.y*10);
        currentDirect=PlayerDirect.FORWARD;

    }
    public boolean checkValidateCoord(int x, int y) {
        if (x >= 0 && x < MapGenerator.width && y >= 0 && y < MapGenerator.height)
            return true;
        return false;
    }
    public int find(int x, int y){
        int pathCount=0;
        arrow=new int[]{0,0,0};
        if(currentDirect==PlayerDirect.FORWARD){
            if(checkValidateCoord(x+1, y)&&map[x+1][y]==CellTypes.GROUND) {
                pathCount++;
                arrow[0]=1;
            }
            if(checkValidateCoord(x,y+1)&&map[x][y+1]==CellTypes.GROUND) {
                pathCount++;
                arrow[1]=1;
            }
            if(checkValidateCoord(x,y-1)&&map[x][y-1]==CellTypes.GROUND) {
                pathCount++;
                arrow[2]=1;
            }
        }
        else if(currentDirect==PlayerDirect.RIGHT){
            if(checkValidateCoord(x, y+1)&&map[x][y+1]==CellTypes.GROUND) {
                pathCount++;
                arrow[0]=1;
            }

            if(checkValidateCoord(x-1,y)&&map[x-1][y]==CellTypes.GROUND) {
                pathCount++;
                arrow[1]=1;
            }

            if(checkValidateCoord(x+1,y)&&map[x+1][y]==CellTypes.GROUND) {
                pathCount++;
                arrow[2]=1;
            }
        }
        else if(currentDirect==PlayerDirect.LEFT){
            if(checkValidateCoord(x, y-1)&&map[x][y-1]==CellTypes.GROUND) {
                pathCount++;
                arrow[0] = 1;
            }
            if(checkValidateCoord(x+1,y)&&map[x+1][y]==CellTypes.GROUND) {
                pathCount++;
                arrow[1] = 1;
            }
            if(checkValidateCoord(x-1,y)&&map[x-1][y]==CellTypes.GROUND) {
                pathCount++;
                arrow[2] = 1;
            }
        }
        if(pathCount==1){
            switch (currentDirect){
                case FORWARD:
                    return 10+find(x+1,y);
                case LEFT:
                    return 10+find(x,y+1);
                case RIGHT:
                    return 10+find(x,y-1);
            }
        }
        showArrow=false;
        playerPos=new Vector2(x,y);
        return 0;
    }
}
