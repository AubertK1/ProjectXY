package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class DualAnimation extends Animation<TextureRegion> {
    private Rectangle[] hitboxes;

    public DualAnimation(float frameDuration, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
        hitboxes = new Rectangle[keyFrames.length];
        for (int i = 0; i < hitboxes.length; i++) {
            if(hitboxes[i] == null) hitboxes[i] = new Rectangle(0,0,0,0);
            else hitboxes[i].set(0,0,0,0);
        }
    }

    public void setHitboxes(Rectangle... hitboxOffsets){
        for (int i = 0; i < hitboxes.length; i++) {
            if(i < hitboxOffsets.length) hitboxes[i].set(hitboxOffsets[i]);
            else hitboxes[i].set(hitboxOffsets[hitboxOffsets.length-1]);
        }
    }

    public Rectangle getKeyHitBox(float stateTime){
        setPlayMode(PlayMode.LOOP);
        int frameNumber = this.getKeyFrameIndex(stateTime);
        return this.hitboxes[frameNumber];
    }
}
