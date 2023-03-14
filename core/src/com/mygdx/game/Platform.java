package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This will be used to create stages and platforms
 */
public class Platform extends Object {
    /**
     * 
     * @param x
     * @param y
     * @param texture
     */
    public Platform(float x, float y, Texture texture) {
        super(x, y, texture.getWidth(), texture.getHeight(), true, true);
        model = texture;
    }

    /**
     * makes a platform based on a sprite sheet
     * 
     * @param spriteSheet
     */
    public Platform(float x, float y, Texture spriteSheet, int ROWS, int COLUMNS) {
        super(x, y, spriteSheet.getWidth(), spriteSheet.getHeight(), true, true);
        swapAnimation(idleAnimation = animate(idleSheet = spriteSheet, ROWS, COLUMNS, .35f));

        TextureRegion model2 = currentAnimation.getKeyFrame(stateTime, true);
        setSize(model2.getRegionWidth(), model2.getRegionHeight());
        setHurtbox(x, y, model2.getRegionWidth(), model2.getRegionHeight());
    }

    public void render(SpriteBatch batch){

        if(currentAnimation == null) { //if no animation...
            //this draws the fighter flipped depending on which way it is facing
            batch.draw(model, getX(), getY(), getWidth(), getHeight());
        }
        else { //if has an animation
            stateTime += Main.getFrameRate();
            modelFrame = currentAnimation.getKeyFrame(stateTime, true);
            applyHitbox(currentAnimation.getKeyHitBox(stateTime), false);
            applyFocalPoint(currentAnimation.getKeyFocalPoint(stateTime), false);
            if (currentAnimation != idleAnimation && currentAnimation.isAnimationFinished(stateTime)) currentAnimation = idleAnimation;

            batch.draw(modelFrame, getX(), getY(), getWidth(), getHeight());
        }

        if(Main.inDebugMode) {
            batch.end();
            renderHurtBox();
            batch.begin();
        }
    }
}
