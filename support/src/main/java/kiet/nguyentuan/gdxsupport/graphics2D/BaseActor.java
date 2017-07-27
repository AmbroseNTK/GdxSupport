package kiet.nguyentuan.gdxsupport.graphics2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.compression.lzma.Base;

/**
 * Created by kiettuannguyen on 21/07/2017.
 */

public class BaseActor extends Actor{
    public TextureRegion textureRegion;
    public Rectangle boundary;
    public float velocityX;
    public float velocityY;
    public void init(){
        textureRegion=new TextureRegion();
        boundary=new Rectangle();
        velocityX=0;
        velocityY=0;
    }
    public BaseActor(){
        super();
        init();
    }
    public BaseActor(Texture texture){
        super();
        init();
        setTexture(texture);
    }
    public BaseActor(Texture texture,float posX,float posY){
        super();
        init();
        setTexture(texture);
        setPosition(posX,posY);
    }
    public void setTexture(Texture t)
    {
        int w = t.getWidth();
        int h = t.getHeight();
        setWidth(w);
        setHeight(h);
        textureRegion.setRegion(t);
    }
    public Rectangle getBoundingRectangle()
    {
        boundary.set( getX(), getY(), getWidth(), getHeight() );
        return boundary;
    }
    public void act(float dt)
    {
        super.act( dt );
        moveBy( velocityX * dt, velocityY * dt );
    }
    public void draw(Batch batch, float parentAlpha) {
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    }
}
