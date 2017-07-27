package kiet.nguyentuan.demo.CheesePlease;

/**
 * Created by kiettuannguyen on 21/07/2017.
 */

import com.badlogic.gdx.Game;

public class CheesePlease extends Game {

    @Override
    public void create() {
        setScreen(new CheeseMenu(this));
    }
}
