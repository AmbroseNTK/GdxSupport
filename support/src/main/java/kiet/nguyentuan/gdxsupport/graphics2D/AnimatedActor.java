package kiet.nguyentuan.gdxsupport.graphics2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by kiettuannguyen on 21/07/2017.
 */

public class AnimatedActor extends BaseActor {
    public float elapsedTime;
    public Animation anim;

    /**
     * An actor which has animation.
     */
    public AnimatedActor()
    {
        super();
        elapsedTime = 0;
    }
    public void setAnimation(Animation a)
    {
        TextureRegion region=(TextureRegion)(a.getKeyFrame(0));
        Texture t = region.getTexture();
        setTexture(t);
        anim = a;
    }
    public void act(float dt)
    {
        super.act( dt );
        elapsedTime += dt;
        if (velocityX != 0 || velocityY != 0)
            setRotation(MathUtils.atan2( velocityY, velocityX ) * MathUtils.radiansToDegrees);
    }
    public void draw(Batch batch, float parentAlpha)
    {
        textureRegion.setRegion((TextureRegion) (anim.getKeyFrame(elapsedTime)));
        super.draw(batch, parentAlpha);
    }
}
