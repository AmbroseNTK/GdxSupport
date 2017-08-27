package kiet.nguyentuan.demo.Simple3DGame;

import com.badlogic.gdx.Game;

public class MainGame extends Game {

    @Override
    public void create() {
        setScreen(new MainStage(this));
    }
}
