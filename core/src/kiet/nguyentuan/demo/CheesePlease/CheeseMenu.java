package kiet.nguyentuan.demo.CheesePlease;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import kiet.nguyentuan.gdxsupport.graphics2D.BaseActor;
import kiet.nguyentuan.gdxsupport.graphics2D.BaseScreen;

/**
 * Created by kiettuannguyen on 21/07/2017.
 */

public class CheeseMenu extends BaseScreen {

    public CheeseMenu(Game game){
        super(game);
    }
    private String dir;
    @Override
    protected void create() {
        dir="CheesePlease";
        InputListener clickOnMenu=new InputListener(){
            public boolean touchDown (InputEvent ev, float x, float y, int pointer, int button)
            {
                game.setScreen(new CheeseLevel(game));
                return true;
            }
        };
        BaseActor background=new BaseActor(new Texture(Gdx.files.internal(dir+"/tiles-menu.jpg")));
        background.addListener(clickOnMenu);
        addUIActor(background);

        BaseActor title=new BaseActor(new Texture(Gdx.files.internal(dir+"/cheese-please.png")),20,100);
        title.addListener(clickOnMenu);
        addUIActor(title);

        BitmapFont font = new BitmapFont();
        String text = " Press S to start, M for main menu ";
        Label.LabelStyle style = new Label.LabelStyle( font, Color.YELLOW );
        Label instructions = new Label( text, style );
        instructions.setFontScale(2);
        instructions.setPosition(100, 50);
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
    }

    @Override
    protected void update(float dT) {
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            game.setScreen(new CheeseLevel(game));
    }
}
