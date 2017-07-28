package kiet.nguyentuan.demo.BalloonPop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import kiet.nguyentuan.gdxsupport.graphics2D.BaseActor;
import kiet.nguyentuan.gdxsupport.graphics2D.BaseScreen;

/**
 * Created by kiettuannguyen on 26/07/2017.
 */

public class MainMenu extends BaseScreen {

    private Music titleMusic;
    public MainMenu(Game game){
        super(game);

    }
    @Override
    protected void create() {
        titleMusic=Gdx.audio.newMusic(Gdx.files.internal("BalloonPop/title.mp3"));
        titleMusic.setLooping(true);
        titleMusic.play();
        BaseActor background=new BaseActor(new Texture(Gdx.files.internal("BalloonPop/background.png")));
        background.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(0.5f,1),Actions.rotateTo(-0.5f,1))));
        addMainActor(background);

        BaseActor layout=new BaseActor(new Texture("BalloonPop/Game-layout.png"));
        addUIActor(layout);



        BitmapFont font = new BitmapFont();
        String text = " Press anywhere to start";
        Label.LabelStyle style = new Label.LabelStyle( font, Color.YELLOW );
        Label instructions = new Label( text, style );
        instructions.setFontScale(2);
        instructions.setPosition(640*0.5f-150, 50);
        instructions.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.color( new Color(1, 1, 0, 1), 0.5f ),
                                Actions.delay( 0.5f ),
                                Actions.color( new Color(0.5f, 0.5f, 0, 1), 0.5f )
                        )
                )
        );
        addUIActor(instructions);

        InputListener clickMenu=new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                titleMusic.stop();
                game.setScreen(new GameScreen(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        };
        layout.addListener(clickMenu);

    }

    @Override
    protected void update(float dT) {

    }
}
