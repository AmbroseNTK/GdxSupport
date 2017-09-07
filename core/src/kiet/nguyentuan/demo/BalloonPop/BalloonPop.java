package kiet.nguyentuan.demo.BalloonPop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.TreeMap;

/**
 * Created by kiettuannguyen on 26/07/2017.
 */

public class BalloonPop extends Game {
    @Override
    public void create() {
        ShaderProgram.pedantic=false;
        setScreen(new BalloonPopMultilayer(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
