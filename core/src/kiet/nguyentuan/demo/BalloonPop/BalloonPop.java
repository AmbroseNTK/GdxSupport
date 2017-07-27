package kiet.nguyentuan.demo.BalloonPop;

import com.badlogic.gdx.Game;

/**
 * Created by kiettuannguyen on 26/07/2017.
 */

public class BalloonPop extends Game {
    @Override
    public void create() {
        setScreen(new MainMenu(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
