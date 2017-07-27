package kiet.nguyentuan.demo.BalloonPop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import kiet.nguyentuan.gdxsupport.graphics2D.BaseActor;

/**
 * Created by kiettuannguyen on 26/07/2017.
 */

public class Balloon extends BaseActor {

    public float speed;
    public float amplitude;
    public float oscillation;
    public float initialY;
    public float time;
    public int offsetX;
    public boolean isMsDung;
    public static boolean createMsDung;
    public Balloon() {
        speed = 80 * MathUtils.random(0.5f, 2.0f);
        amplitude = 50 * MathUtils.random(0.5f, 2.0f);
        oscillation = 0.01f * MathUtils.random(0.5f, 2.0f);
        initialY = 120 * MathUtils.random(0.5f, 2.0f);
        time = 0;
        offsetX = -100;
        String balloonType = "";
        if (createMsDung) {
            balloonType = "BalloonPop/dung-head1.png";
            isMsDung=true;
        } else {
            balloonType = "BalloonPop/shit.png";
            isMsDung=false;
        }
        createMsDung = !createMsDung;
        setTexture(new Texture(Gdx.files.internal(balloonType)));
        setX(offsetX);
    }
    @Override
    public void act(float dt) {
        super.act(dt);
        time += dt;
        float xPos = speed * time + offsetX;
        float yPos = amplitude * MathUtils.sin(oscillation * xPos) + initialY;
        setPosition( xPos, yPos );
    }
}
