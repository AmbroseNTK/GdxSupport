package kiet.nguyentuan.demo.CheesePlease;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.Action;

import kiet.nguyentuan.gdxsupport.graphics2D.AnimatedActor;
import kiet.nguyentuan.gdxsupport.graphics2D.BaseActor;
import kiet.nguyentuan.gdxsupport.graphics2D.BaseScreen;

/**
 * Created by kiettuannguyen on 21/07/2017.
 */

public class CheeseLevel extends BaseScreen {
    private AnimatedActor mousey;
    private BaseActor cheese;
    private BaseActor floor;
    private BaseActor winText;
    private boolean win;
    private float timeElapsed;
    private Label timeLabel;
    final int mapWidth = 800;
    final int mapHeight = 800;
    private String dir;

    public CheeseLevel(Game game){
        super(game);
    }
    @Override
    protected void create() {
        dir="CheesePlease";
        timeElapsed = 0;
        floor = new BaseActor();
        floor.setTexture( new Texture(Gdx.files.internal(dir+"/tiles-800-800.jpg")) );
        floor.setPosition( 0, 0 );
        mainStage.addActor( floor );
        cheese = new BaseActor();
        cheese.setTexture( new Texture(Gdx.files.internal(dir+"/cheese.png")) );
        cheese.setPosition( 400, 300 );
        cheese.setOrigin( cheese.getWidth()/2, cheese.getHeight()/2 );
        mainStage.addActor( cheese );
        mousey = new AnimatedActor();
        TextureRegion[] frames = new TextureRegion[4];
        for (int n = 0; n < 4; n++)
        {
            String fileName = dir+"/mouse" + n + ".png";
            Texture tex = new Texture(Gdx.files.internal(fileName));
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frames[n] = new TextureRegion( tex );
        }
        Array<TextureRegion> framesArray = new Array<TextureRegion>(frames);
        Animation anim = new Animation(0.1f, framesArray, Animation.PlayMode.LOOP_PINGPONG);
        mousey.setAnimation( anim );
        mousey.setOrigin( mousey.getWidth()/2, mousey.getHeight()/2 );
        mousey.setPosition( 20, 20 );
        mainStage.addActor(mousey);
        winText = new BaseActor();
        winText.setTexture( new Texture(Gdx.files.internal(dir+"/you-win.png")) );
        winText.setPosition( 170, 60 );
        winText.setVisible( false );
        uiStage.addActor( winText );
        BitmapFont font = new BitmapFont();
        String text = "Time: 0";
        Label.LabelStyle style = new Label.LabelStyle( font, Color.NAVY );
        timeLabel = new Label( text, style );
        timeLabel.setFontScale(2);
        timeLabel.setPosition(500,440);
        uiStage.addActor( timeLabel );
    }

    @Override
    protected void update(float dT) {
        mousey.velocityX = 0;
        mousey.velocityY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            mousey.velocityX -= 100;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            mousey.velocityX += 100;
        ;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            mousey.velocityY += 100;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            mousey.velocityY -= 100;

        mousey.setX(MathUtils.clamp(mousey.getX(), 0, mapWidth - mousey.getWidth()));
        mousey.setY(MathUtils.clamp(mousey.getY(), 0, mapHeight - mousey.getHeight()));

        Rectangle cheeseRectangle = cheese.getBoundingRectangle();
        Rectangle mouseyRectangle = mousey.getBoundingRectangle();
        if (!win && cheeseRectangle.contains(mouseyRectangle)) {
            win = true;
            Action spinShrinkFadeOut = Actions.parallel(
                    Actions.alpha(1),
                    Actions.rotateBy(360, 1),
                    Actions.scaleTo(0, 0, 2),
                    Actions.fadeOut(1)
            );
            cheese.addAction(spinShrinkFadeOut);
            Action fadeInColorCycleForever = Actions.sequence(
                    Actions.alpha(0),
                    Actions.show(),
                    Actions.fadeIn(2),
                    Actions.moveTo(Gdx.graphics.getWidth()*0.5f,Gdx.graphics.getHeight()*0.5f,2f),
                    Actions.forever(
                            Actions.sequence(
                                    Actions.color(new Color(1, 0, 0, 1), 1),
                                    Actions.color(new Color(0, 0, 1, 1), 1)
                            )
                    )
            );
            winText.addAction(fadeInColorCycleForever);
        }
        if (!win) {
            timeElapsed += dT;
            timeLabel.setText("Time: " + (int) timeElapsed);
        }
        Camera cam = mainStage.getCamera();
        cam.position.set(mousey.getX() + mousey.getOriginX(),mousey.getY() + mousey.getOriginY(), 0);
        cam.position.x = MathUtils.clamp(cam.position.x, viewWidth / 2, mapWidth - viewWidth / 2);
        cam.position.y = MathUtils.clamp(cam.position.y, viewHeight / 2, mapHeight - viewHeight / 2);
        cam.update();

    }

    @Override
    public void dispose() {

        super.dispose();
    }

    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.M)
            game.setScreen( new CheeseMenu(game) );
        if (keycode == Input.Keys.P)
            togglePaused();
        return false;
    }
}
