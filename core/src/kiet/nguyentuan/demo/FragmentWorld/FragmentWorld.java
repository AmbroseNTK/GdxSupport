package kiet.nguyentuan.demo.FragmentWorld;

import com.badlogic.gdx.Game;

/**
 * Created by nguye on 8/29/2017.
 */

public class FragmentWorld extends Game {

    @Override
    public void create() {
        setScreen(new MainGame(this));
    }
}
