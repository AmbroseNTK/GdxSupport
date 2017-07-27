package kiet.nguyentuan.demo.BalloonPop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.compression.lzma.Base;

import java.util.ArrayList;

import kiet.nguyentuan.gdxsupport.graphics2D.BaseActor;
import kiet.nguyentuan.gdxsupport.graphics2D.BaseScreen;

/**
 * Created by kiettuannguyen on 26/07/2017.
 */

public class GameScreen extends BaseScreen {

    private float spawnTimer;
    private float spawnInterval;
    private int point;
    private int clickCount;
    private Label lPoint;
    private Label lTips;
    private Sound balloonExplode;
    private Music backgroundMusic;
    private Music soundGameOver;
    private ShaderProgram shaderProgram;
    private boolean isMsDung;
    private boolean mPart2,mPart3;
    private ArrayList<Sound> fartList;
    private ArrayList<String> shaderList;
    public GameScreen(Game game) {
        super(game);
    }

    @Override
    protected void create() {
        shaderList=new ArrayList<String>();
        shaderList.add("ball.fsh.glsl");
        shaderList.add("sky.fsh.glsl");
        shaderList.add("flylight.fsh.glsl");
        shaderList.add("magiccircle.fsh.glsl");
        if(Gdx.app.getType()== Application.ApplicationType.Desktop){
            shaderList.add("building.fsh.glsl");
        }
        BaseActor background=new BaseActor(new Texture(Gdx.files.internal("BalloonPop/Game-layout.png")));
        setShader(Gdx.files.internal("BalloonPop/shaders/sky.vsh.glsl"),
                Gdx.files.internal("BalloonPop/shaders/"+shaderList.get(MathUtils.random(0,shaderList.size()-1))));
        addShaderActor(background);

        spawnTimer = 0;
        spawnInterval = 0.5f;
        isMsDung=true;
        mPart2=false;
        mPart3=false;
        fartList=new ArrayList<Sound>();
        for(int i=0;i<5;i++){
            fartList.add(Gdx.audio.newSound(Gdx.files.internal("BalloonPop/fart-"+i+".wav")));
        }


// set up user interface
        BitmapFont font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle( font, Color.GOLD );
        point = 0;
        lPoint = new Label( "Point: 0", style );
        lPoint.setFontScale(2);
        lPoint.setPosition(20, 440);
        uiStage.addActor( lPoint );
        lTips = new Label( "Please catch Ms.Dung", style );
        lTips.setFontScale(2);
        lTips.setPosition(220, 440);
        uiStage.addActor( lTips );
        clickCount = 0;

        balloonExplode=Gdx.audio.newSound(Gdx.files.internal("BalloonPop/balloonexplode.mp3"));
        backgroundMusic=Gdx.audio.newMusic(Gdx.files.internal("BalloonPop/Main menu.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        soundGameOver=Gdx.audio.newMusic(Gdx.files.internal("BalloonPop/gameover.mp3"));

        BaseActor redLine=new BaseActor(new Texture(Gdx.files.internal("BalloonPop/redline.png")),630,0);
        addUIActor(redLine);
    }

    @Override
    protected void update(float dT) {
        spawnTimer += dT;
// check time for next balloon spawn
        if (spawnTimer > spawnInterval)
        {
            spawnTimer -= spawnInterval;
            final Balloon b = new Balloon();
            b.addListener(
                    new InputListener()
                    {
                        public boolean touchDown (InputEvent ev, float x, float y, int pointer, int button)
                        {
                            if(isMsDung == b.isMsDung) {
                                point++;
                                b.remove();
                                fartList.get(MathUtils.random(0,4)).play();
                                isMsDung = !isMsDung;
                            }
                            else
                            {
                               doGameOver();
                            }
                            return true;
                        }
                    });
            mainStage.addActor(b);
        }
// remove balloons that are off-screen
        for ( Actor a : mainStage.getActors() )
        {
            if (a.getX() > 640 || a.getY() > 480)
            {
                //Game over too
                doGameOver();
                a.remove();
            }
        }
        lPoint.setText( "Point: "+ point );

        String tip="";
        if(isMsDung)
            tip="Please catch Ms.Dung";
        else
            tip="Please catch a shit";
        lTips.setText( "Tip: " + tip );

        if(point>=300&&point<600 && mPart2==false) {
            backgroundMusic.stop();
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("BalloonPop/playPart2.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
            mPart2 = true;
        }
        if(point>=600&&mPart3==false) {
            backgroundMusic.stop();
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("BalloonPop/playPart3.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
            mPart3 = true;
        }

    }
    private void doGameOver(){
        backgroundMusic.stop();
        //Game over
        setPaused(true);
        if(!soundGameOver.isPlaying()) {
            soundGameOver.play();
        }
        BaseActor gameOver =new BaseActor(new Texture("BalloonPop/gameover.png"));
        gameOver.addAction(Actions.sequence(Actions.alpha(0f),Actions.alpha(1.0f,1.0f)));
        addUIActor(gameOver);

        gameOver.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
}
